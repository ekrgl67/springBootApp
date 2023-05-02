package com.springboot.reflection;

public class Converter {

    public static void main(String[] args) {

        Customer customer = getCustomer();

        CustomerDto customerDto = convertToCustomerDto(customer);

        System.out.println(customerDto);
    }

    private static CustomerDto convertToCustomerDto(Customer source) {
        CustomerDto target = new CustomerDto();
        target.setName(source.getName());
        target.setSurname(source.getSurname());
        target.setId(source.getId());
        return target;
    }

    private static Customer getCustomer() {
        Customer customer = new Customer();
        customer.setName("ali");
        customer.setSurname("veli");
        customer.setId(1L);
        return customer;
    }
}
