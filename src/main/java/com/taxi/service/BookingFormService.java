package com.taxi.service;

import java.util.List;

import com.taxi.model.BookingForm;
//import com.taxi.model.ContactForm;

public interface BookingFormService {
	public BookingForm saveBookingFormService(BookingForm bookingForm);
	
	public List<BookingForm> readAllBookingService();
	public void deleteBookingService(int id);


}
