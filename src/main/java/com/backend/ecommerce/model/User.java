package com.backend.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "User")
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "UF")
    private String UF;

    @Column(name = "city")
    private String city;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "street")
    private String street;

    @Column(name = "streetNumber")
    private String streetNumber;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Status status;

    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "updateDate")
    private Date updateDate;

    public enum Status{
        INACTIVE, ACTIVE
    }

    public User(){}

    public User(String name, String email, String UF, String city, String neighborhood, String street, String streetNumber, String zipCode, String phone, String password, Status status, Date creationDate, Date updateDate) {
        this.name = name;
        this.email = email;
        this.UF = UF;
        this.city = city;
        this.neighborhood = neighborhood;
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.phone = phone;
        this.password = password;
        this.status = status;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}