package com.taxi.service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taxi.dao.ServiceCrud;
import com.taxi.model.ServiceForm;

import jakarta.transaction.Transactional;

@Service
public class ServiceFormServiceImpl implements ServiceFormService {

    private ServiceCrud serviceCrud;

    @Autowired
    public void setServiceCrud(ServiceCrud serviceCrud) {
        this.serviceCrud = serviceCrud;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ServiceForm addService(ServiceForm serviceForm, MultipartFile multipartFile) throws Exception {
        ServiceForm savedService = null;

        try {
            // First, save the serviceForm (without the image)
            savedService = serviceCrud.save(serviceForm);

            if (savedService != null && !multipartFile.isEmpty()) {
                // Define the existing directory path
                String uploadDir = "C:\\Users\\ANITHA\\Documents\\workspace-spring-tool-suite-4-4.28.0.RELEASE\\TaxiBooking\\src\\main\\resources\\static\\myserviceimg\\";

                // Ensure the directory exists
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs(); // Create the folder if it doesn’t exist
                }

                // Create the full file path
                String filePath = uploadDir + multipartFile.getOriginalFilename();
                File file = new File(filePath);

                // Save the file using FileOutputStream and close it properly
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(multipartFile.getBytes());
                }

                // Update the serviceForm with the image filename
                savedService.setImage(multipartFile.getOriginalFilename());

                // Save again to update the image field in the database
                serviceCrud.save(savedService);
            }
        } catch (IOException e) {
            throw new Exception("File upload failed: " + e.getMessage());
        } catch (Exception e) {
            throw e;
        }

        return savedService;
    }

	@Override
	public List<ServiceForm> readAllServices() {
		
		return serviceCrud.findAll();
	}
}
