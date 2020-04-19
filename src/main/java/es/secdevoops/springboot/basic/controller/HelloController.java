package es.secdevoops.springboot.basic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import es.secdevoops.springboot.basic.model.UserAccount;
import es.secdevoops.springboot.basic.repository.UserAccountRepository;

@RestController
public class HelloController {
	
	@Autowired
	private UserAccountRepository userRepository;
	
	@GetMapping("/hello")
	public List<UserAccount> hello() {
		return userRepository.findAll();
	}
}
