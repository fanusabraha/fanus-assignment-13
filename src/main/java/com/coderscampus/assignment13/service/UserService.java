package com.coderscampus.assignment13.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.coderscampus.assignment13.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AccountRepository accountRepo;
	
	public List<User> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	
	public List<User> findByNameAndUsername(String name, String username) {
		return userRepo.findByNameAndUsername(name, username);
	}
	
	public List<User> findByCreatedDateBetween(LocalDate date1, LocalDate date2) {
		return userRepo.findByCreatedDateBetween(date1, date2);
	}
	
	public User findExactlyOneUserByUsername(String username) {
		List<User> users = userRepo.findExactlyOneUserByUsername(username);
		if (users.size() > 0)
			return users.get(0);
		else
			return new User();
	}
	
	public Set<User> findAll () {
		return userRepo.findAllUsersWithAccountsAndAddresses();
	}
	
	public User findById(Long userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		return userOpt.orElse(new User());
	}

	public User saveUser(User user) {
		/*
		if (user.getUserId() != null *//* && user.getAddress() == null *//*) {
			Address newaddress = new Address();
			newaddress.setAddressLine1(user.getAddress().getAddressLine1());
			newaddress.setAddressLine2(user.getAddress().getAddressLine2());
			newaddress.setCity(user.getAddress().getCity());
			newaddress.setRegion(user.getAddress().getRegion());
			newaddress.setCountry(user.getAddress().getCountry());
			newaddress.setZipCode(user.getAddress().getZipCode());
			newaddress.setUserId(user.getUserId());
			newaddress.setUser(user);
			user.setAddress(newaddress);
		}*/
		return userRepo.save(user);
	}
	public User saveUserWithAdress(User user, Address address) {


		if (user.getUserId() != null && address==null) {
			Address newaddress = new Address();
			newaddress.setAddressLine1(user.getAddress().getAddressLine1());
			newaddress.setAddressLine2(user.getAddress().getAddressLine2());
			newaddress.setCity(user.getAddress().getCity());
			newaddress.setRegion(user.getAddress().getRegion());
			newaddress.setCountry(user.getAddress().getCountry());
			newaddress.setZipCode(user.getAddress().getZipCode());
			newaddress.setUserId(user.getUserId());
			newaddress.setUser(user);
			user.setAddress(newaddress);
		}
		else {
			address.setUser(user);
			user.setAddress(address);
		}

		return userRepo.save(user);
	}

	public void delete(Long userId) {
		userRepo.deleteById(userId);
	}
}
