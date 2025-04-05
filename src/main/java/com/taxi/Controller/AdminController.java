package com.taxi.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.taxi.dao.AdminDao;
import com.taxi.model.Admin;
import com.taxi.model.BookingForm;
import com.taxi.model.ContactForm;
import com.taxi.model.ServiceForm;
import com.taxi.service.AdminCredentialsService;
import com.taxi.service.BookingFormService;
import com.taxi.service.ContactFormService;
import com.taxi.service.ServiceFormService;

import org.springframework.ui.Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	private AdminCredentialsService adminCredentailsService;
	@Autowired
	private BookingFormService bookingFormService;
	@Autowired
	private ServiceFormService serviceFormService;
	
	

	@Autowired
	public void setServiceFormService(ServiceFormService serviceFormService) {
		this.serviceFormService = serviceFormService;
	}
	
	@Autowired
	public void setBookingFormService(BookingFormService bookingFormService)
	{
		this.bookingFormService= bookingFormService;
	}
	
	@Autowired
	public void setAdminCredentialsService(AdminCredentialsService adminCredentialsService) {
		this.adminCredentailsService=adminCredentialsService;
	}
    
	@Autowired
	private ContactFormService contactFormService;
	
	public void setContactFormService(ContactFormService contactFormService) {
		this.contactFormService=contactFormService;
	}
    @Autowired
    private AdminDao adminDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @PostMapping("login")
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password) {
        Optional<Admin> existingAdmin = adminDao.findByUsername(username);

        if (existingAdmin.isPresent() && passwordEncoder.matches(password, existingAdmin.get().getPassword())) {
            return "Login Successful!";
        } else {
            return "Invalid Username or Password";
        }
    }
    
    @GetMapping("readAllContacts")
    public String readAllContacts(Model model) {
    //	List<ContactForm> allContactsService=
    			model.addAttribute("allcontacts",contactFormService.readAllContactsService());
    	
        return "admin/readallcontacts";
    }

    @GetMapping("deleteContact/{id}")
    public String deleteContacts( @PathVariable int id,RedirectAttributes redirectAttributes) {
    	
    	contactFormService.deleteContactService(id);
    	redirectAttributes.addFlashAttribute("message","CONTACT DELETED SUCCESSFULLY");
    	
        return "redirect:/admin/readAllContacts";
     
    }
    
    @GetMapping("changeCredentials")
    public String changeCredentialsView()
    {
    	return "admin/changecredentials";
    }

    @PostMapping("changeCredentials")
    public String changeCredentials(
        @RequestParam("oldusername") String oldusername,
        @RequestParam("oldpassword") String oldpassword,
        @RequestParam("newusername") String newusername,
        @RequestParam("newpassword") String newpassword,
        RedirectAttributes redirectAttributes
    ) {
        String result = adminCredentailsService.checkAdminCredentials(oldusername, oldpassword);
        
        if ("SUCCESS".equals(result)) {
            result = adminCredentailsService.updateAdminCredentials(newusername, newpassword, oldusername);
        }
        
        redirectAttributes.addFlashAttribute("message", result);
        return "redirect:/admin/dashboard";
    }


    @GetMapping("readAllBookings")
    public String readAllBookings(Model model) {
    List<BookingForm> allBookingsService=bookingFormService.readAllBookingService();
    System.out.print(allBookingsService); 
    model.addAttribute("allBookings",allBookingsService);
     
    	
        return "admin/readallbookings";
    }
    @GetMapping("deleteBooking/{id}")
    public String deleteBooking( @PathVariable int id,RedirectAttributes redirectAttributes) {
    	
    	bookingFormService.deleteBookingService(id);
    	redirectAttributes.addFlashAttribute("message","BOOKING  DELETED SUCCESSFULLY");
    	
        return "redirect:/admin/readAllBookings";
     
    }
    
    @GetMapping("addService")
    public  String addServiceView() {
    
     
    	
        return "admin/addService";
    }
    
    @InitBinder
    public void stopBinding(WebDataBinder webDataBinder) {
    	webDataBinder.setDisallowedFields("image");
    }
    
    @PostMapping("addService")
    public String addService(@ModelAttribute ServiceForm serviceForm,
                             @RequestParam("image") MultipartFile multipartFile,
                             RedirectAttributes redirectAttributes) {
        try {
            if (multipartFile.isEmpty()) {
                redirectAttributes.addFlashAttribute("msg", "Please select an image.");
                return "redirect:/addService";
            }

            // Call the service to handle both file saving & DB update
            ServiceForm savedService = serviceFormService.addService(serviceForm, multipartFile);

            if (savedService != null) {
                redirectAttributes.addFlashAttribute("msg", "Service added successfully");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Something went wrong");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "Error: " + e.getMessage());
        }

        return "redirect:/admin/addService";
    }
 
   
	
}
