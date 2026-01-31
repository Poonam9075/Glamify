package com.glamify.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import com.glamify.dto.CustomerDto;
import com.glamify.entity.Customer;

public class CustomerMapper {

	public static CustomerDto toDto(Customer customer)
	{
		CustomerDto customerDto = new CustomerDto();
		
		customerDto.setActive(customer.isActive());
		customerDto.setEmail(customer.getEmail());
		customerDto.setFullName(customer.getFullName());
		customerDto.setGender(customer.getGender());
		customerDto.setPassword(customer.getPassword());
		customerDto.setPhone(customer.getPhone());
		customerDto.setUserRole(customer.getRole());
		customerDto.setUserId(customer.getUserId());
		
		return customerDto;
	}
	
	public static List<CustomerDto> toDtolist(List<Customer> customerList)
	{
		List<CustomerDto> customerDtoList = new ArrayList<>();
		
		for(Customer customer : customerList)
		{
			customerDtoList.add(CustomerMapper.toDto(customer));
		}
		
		return customerDtoList;
	}
	
	public static Customer toEntity(CustomerDto customerDto)
	{
		Customer customer = new Customer();
		
		customer.setActive(customerDto.isActive());
		customer.setEmail(customerDto.getEmail());
		customer.setFullName(customerDto.getFullName());
		customer.setGender(customerDto.getGender());
		customer.setPassword(customerDto.getPassword());
		customer.setPhone(customerDto.getPhone());
		customer.setRole(customerDto.getUserRole());
		customer.setUserId(customerDto.getUserId());
		
		return customer;
	}
}
