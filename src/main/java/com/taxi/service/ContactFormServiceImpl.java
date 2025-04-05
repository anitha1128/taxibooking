package com.taxi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taxi.dao.ContactFormCrud;
import com.taxi.model.ContactForm;

@Service
public class ContactFormServiceImpl implements ContactFormService {

	private ContactFormCrud contactFormCrud;
	
	@Override
	public ContactForm saveContactFromService(ContactForm contactForm) {

	 return 	contactFormCrud.save(contactForm);
	}

	public ContactFormCrud getContactFormCrud() {
		return contactFormCrud;
	}

	@Autowired
	public void setContactFormCrud(ContactFormCrud contactFormCrud) {
		this.contactFormCrud = contactFormCrud;
	}

	@Override
	public List<ContactForm> readAllContactsService() {
		// TODO Auto-generated method stub
		return contactFormCrud.findAll();
	}

	@Override
	public void deleteContactService(int id) {
		// TODO Auto-generated method stub
		contactFormCrud.deleteById(id);
		
	}

}


