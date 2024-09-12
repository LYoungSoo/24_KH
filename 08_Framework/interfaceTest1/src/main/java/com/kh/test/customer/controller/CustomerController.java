package com.kh.test.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.test.customer.dto.Customer;
import com.kh.test.customer.service.CustomerService;

@Controller
//@RequestMapping("customer")	// 이거하나
public class CustomerController {

  @Autowired 
  private CustomerService service;

  @PostMapping("customer/insertCustomer") 
  public String insertCustomer(Customer customer, Model model) {
    int result = service.insertCustomer(customer);

    
    if (result > 0)
      model.addAttribute("message", "추가 성공!!!");
    else
      model.addAttribute("message", "추가 실패...");

    return "result"; 
  }
}
