package com.springboot.reflection;

import com.springboot.crd.domain.CrdCreditCard;

import java.lang.reflect.Modifier;

public class App {

    public static void main(String[] args) {

        Class<?> clazz;
        try {
//            clazz = Class.forName("com.springboot.reflection.CustomerDto");
            clazz = Class.forName("com.springboot.crd.domain.CrdCreditCardActivity");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Hata");
        }

        int modifiers = clazz.getModifiers();
        System.out.println(Modifier.isPublic(modifiers));

        Class<?> superclass = clazz.getSuperclass();
        System.out.println(superclass);

        Class<?>[] interfaces = superclass.getInterfaces();
        System.out.println(interfaces[0].getModifiers());
     }
}
