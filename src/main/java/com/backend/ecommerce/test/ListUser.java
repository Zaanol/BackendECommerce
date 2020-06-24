package com.backend.ecommerce.test;

import com.backend.ecommerce.model.User;
import com.backend.ecommerce.util.JPAUtil;

public class ListUser {
    public static void main(String[] args) {
        System.out.println(JPAUtil.getObjects(User.class));
        User user = (User) JPAUtil.getObject(User.class, 1);
        System.out.println(user.getName());
    }
}
