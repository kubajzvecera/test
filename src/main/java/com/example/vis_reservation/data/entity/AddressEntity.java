package com.example.vis_reservation.data.entity;

public class AddressEntity {
    private long id;
    private String street;
    private String city;
    private String houseNumber;
    private String zipCode;

    public AddressEntity() {
    }

    public AddressEntity(long id, String street, String city, String houseNumber, String zipCode) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "AddressEntity{id=" + id + ", street='" + street + "', city='" + city
                + "', houseNumber='" + houseNumber + "', zipCode='" + zipCode + "'}";
    }
}
