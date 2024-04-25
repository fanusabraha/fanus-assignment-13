package com.coderscampus.assignment13.web;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AddressService addressService;
	
	@GetMapping("/register")
	public String getCreateUser (ModelMap model) {
		
		model.put("user", new User());
		
		return "register";
	}
	
	@PostMapping("/register")
	public String postCreateUser (User user) {
		Address address = new Address();
		address.setUser(user);
		userService.saveUser(user);
		return "redirect:/register";
	}
	
	@GetMapping("/users")
	public String getAllUsers (ModelMap model) {
		Set<User> users = userService.findAll();
		
		model.put("users", users);
		if (users.size() == 1) {
			model.put("user", users.iterator().next());
		}
		
		return "users";

	}

	@GetMapping("/users/{userId}")
	public String getOneUser (ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		model.put("users", Arrays.asList(user));
		model.put("user", user);
		// new test day 21/4
		Address oldAddress = user.getAddress();
		// this is if the user is new and does not have address it will not throw an exception
		if (oldAddress==null){
			oldAddress = new Address();
		}
		model.put("oldAddress", oldAddress);
		return "users";
	}
	
	@PostMapping("/users/{userId}")
	public String postOneUser (User user, Address address) {
		User existingUser = userService.findById(user.getUserId());
		Address existingUserAddress = existingUser.getAddress();
		if (existingUserAddress == null) {
			existingUserAddress = new Address();
			existingUser.setAddress(existingUserAddress);
			existingUserAddress.setUser(existingUser);
		}
		existingUserAddress.setAddressDetails(address);
		existingUser.setName(user.getName());
		existingUser.setUsername(user.getUsername());
		if (user.getPassword()!=null){existingUser.setPassword(user.getPassword());}
		addressService.saveAddress(existingUserAddress);
		userService.saveUser(existingUser);

		return "redirect:/users/"+user.getUserId();
	}
	
	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser (@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}


}
