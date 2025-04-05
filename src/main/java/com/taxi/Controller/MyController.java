package com.taxi.Controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.taxi.model.BookingForm;
import com.taxi.model.ContactForm;
import com.taxi.model.ServiceForm;
import com.taxi.service.BookingFormService;
import com.taxi.service.ContactFormService;
import com.taxi.service.ServiceFormService;

@Controller
public class MyController {

  
    private ContactFormService contactFormService;
    private BookingFormService bookingFormService;
    private ServiceFormService serviceFormService;

    @Autowired
    public void setServiceFormService(ServiceFormService serviceFormService) {
        this.serviceFormService = serviceFormService;
    }
@Autowired
    public MyController(BookingFormService bookingFormService, ContactFormService contactFormService) {
        this.bookingFormService = bookingFormService;
        this.contactFormService = contactFormService;
    }

    
    @GetMapping(path = {"", "home", "welcome", "index"})
    public String welcomeView(HttpServletRequest req, Model m) {
        m.addAttribute("mycurrentpage", req.getRequestURI());
        m.addAttribute("bookingForm", new BookingForm());
        return "index";
    }

    @GetMapping("/about")
    public String aboutView(HttpServletRequest req, Model m) {
        m.addAttribute("mycurrentpage", req.getRequestURI());
        return "about";
    }

    @GetMapping("/cars")
    public String carsView(HttpServletRequest req, Model m) {
        m.addAttribute("mycurrentpage", req.getRequestURI());
        return "cars";
    }

    @GetMapping("/services")
    public String serviceView(HttpServletRequest req, Model m) {
        m.addAttribute("mycurrentpage", req.getRequestURI());
        
        List<ServiceForm> allServices=serviceFormService.readAllServices();
        m.addAttribute("allservices",allServices);
        return "services";
    }

    @GetMapping("/contacts")
    public String contactView(HttpServletRequest req, Model m) {
        m.addAttribute("mycurrentpage", req.getRequestURI());
        m.addAttribute("contactForm", new ContactForm());
        return "contacts";
    }
    
    @GetMapping("/login")
    public String adminLoginView(HttpServletRequest request,Model model)
    {
		ServletContext servletContext=request.getServletContext();
		 Object attribute=servletContext.getAttribute("logout");
		 
		if(attribute instanceof Boolean)
		{
			model.addAttribute("logout",attribute);
			servletContext.removeAttribute("logout");
		}
    	return "adminlogin";
    }

    @PostMapping("/contactForm")
    public String contactForm(@Valid @ModelAttribute("contactForm") ContactForm contactForm,
                              BindingResult bindingResult, Model m, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            m.addAttribute("bindingResult", bindingResult);
            return "contacts";
        }

       ContactForm saveContactFormService= contactFormService.saveContactFromService(contactForm);
       
       if(saveContactFormService!=null)
       {
    	   redirectAttributes.addFlashAttribute("message","Booking  successfully"); 
       }
       else
       {
    	   redirectAttributes.addFlashAttribute("message","Something went wrong");
       }
        return "redirect:/contacts";
    }
    

    @PostMapping("bookingform")
    public String bookingform(@Valid @ModelAttribute BookingForm bookingForm,
                              BindingResult bindingResult, Model m, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            m.addAttribute("bindingResult", bindingResult);
            return "index"; // Ensure "index.html" exists in templates
        }
       // System.out.println("Saving booking form: " + bookingForm);

        else if(bookingForm.getAdult()+bookingForm.getChildren()>4) {
        	m.addAttribute("message","hello");
        	return "index";
        }
        	
        
        
        
       // BookingForm saveBookingFormService = bookingFormService.saveBookingFormService(bookingForm);
       
		/*
		 * if(saveBookingFormService!=null) {
		 * System.out.println("Booking successfully saved with ID: " +
		 * saveBookingFormService.getId());
		 * 
		 * redirectAttributes.addFlashAttribute("message","Booking successfully"); }
		 * else { System.out.println("not saved");
		 * 
		 * redirectAttributes.addFlashAttribute("message","Something went wrong"); }
		 */
        
        return "redirect:/index";
    }

}
