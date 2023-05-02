package com.springboot.reflection;

import java.lang.reflect.Method;

public class CodeGenerator {

    public static void main(String[] args) {

        Class clazzSource = Customer.class;
        Class clazzTarget = CustomerDto.class;

        String simpleName = clazzTarget.getSimpleName();
        Method[] declaredMethods = clazzTarget.getDeclaredMethods();

        System.out.println(simpleName + " target = new " + simpleName + "(); ");

        for (Method eachMethod : declaredMethods) {
            String name = eachMethod.getName();

            if (name.startsWith("set")) {

                String replace = name.replace("set", "get");
                System.out.println("target." + name + "(source." + replace + "());");
            }
        }
        System.out.println("return target;");

    }

}
