package com.taxi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "contactform")
public class ContactForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private int id;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 30, message = "Invalid name size")
    @Column(length = 30)
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 5, max = 50, message = "Invalid email size")
    @Column(length = 50)
    private String email;

    @NotNull(message = "Phone number cannot be null")
    @Min(value = 100000000, message = "Phone number must have at least 10 digits")
    @Max(value = 9999999999999L, message = "Phone number cannot exceed 10 digits")
    @Column(length = 20)
    private long phone;

    @NotEmpty(message = "Message cannot be empty")
    @Size(min = 2, max = 30, message = "Invalid message size")
    @Column(length = 500)
    private String message;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
