package com.taxi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taxi.model.ServiceForm;

@Repository
public interface ServiceCrud extends JpaRepository<ServiceForm , Integer> {
	

}
