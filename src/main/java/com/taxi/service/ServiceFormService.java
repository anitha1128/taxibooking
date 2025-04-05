package com.taxi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.taxi.model.ServiceForm;

public interface ServiceFormService {
	public ServiceForm addService(ServiceForm serviceForm,MultipartFile multipartFile) throws Exception;

	public List<ServiceForm> readAllServices();
}
