package com.backend.ecommerce.test;

import com.backend.ecommerce.model.User;
import com.backend.ecommerce.util.JPAUtil;

import java.util.Date;

public class InsertUser {
    public static void main(String[] args) {
        User user = new User();
        user.setName("Willian Zanol");
        user.setEmail("willianzanoll@hotmail.com");
        user.setUF("SC");
        user.setCity("Fraiburgo");
        user.setNeighborhood("Centro");
        user.setStreet("Nereu Ramos");
        user.setStreetNumber("483");
        user.setZipCode("89580000");
        user.setPhone("3246-6490");
        user.setPassword("123456789");
        user.setStatus(User.Status.ACTIVE);
        user.setCreationDate(new Date());
        user.setUpdateDate(new Date());

        JPAUtil.persist(user);
    }
}