package com.jpop.user.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpop.user.model.User;
import com.jpop.user.repo.UserRepository;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
	
	@Autowired
	public UserRepository userRepository;
	
	@GetMapping("")
	public ResponseEntity getAllUsers()
	{
		
		List<User> userList=userRepository.findAll();
		System.out.println("User Details "+userList.toString());
		return ResponseEntity.status(HttpStatus.OK).body(userList);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable int id)
	{
		System.out.println(" id from input "+id);
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.findById(id));
		
	}
	
	@PostMapping("")
	public  ResponseEntity addUsers(@RequestBody User  user)
	{
		System.out.println(" user details "+user.toString());
		userRepository.save(user);
		return ResponseEntity.status(HttpStatus.OK).body("User added Successfully");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteUser(@PathVariable int id)
	{
		Optional<User>user=userRepository.findById(id);
		if(user.isPresent())
		{
			userRepository.delete(user.get());
			return ResponseEntity.status(HttpStatus.OK).body("User Deleted ");

		}
		else
		{
			return ResponseEntity.status(HttpStatus.OK).body("User not found "); 
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity updateUser(@PathVariable int id,@RequestBody User inuser)
	{
		Optional<User>user=userRepository.findById(id);
		if(user.isPresent())
		{
			user.get().setRole(inuser.getRole());
			user.get().setUserName(inuser.getUserName());

			userRepository.save(user.get());
			return ResponseEntity.status(HttpStatus.OK).body("User updated ");

		}
		else
		{
			return ResponseEntity.status(HttpStatus.OK).body("User not found "); 
		}
	}
}
