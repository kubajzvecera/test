package com.example.vis_reservation.domain;

public class Customer {
    private long id;
    private String fname;
    private String lname;
    private String phone;
    private String email;
    private Address address;

    public Customer() {
    }

    public Customer(long id, String fname, String lname, String phone, String email, Address address) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{id=" + id + ", fname='" + fname + "', lname='" + lname
                + "', phone='" + phone + "', email='" + email + "', address=" + address + "}";
    }
}