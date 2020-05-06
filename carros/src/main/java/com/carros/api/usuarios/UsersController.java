package com.carros.api.usuarios;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carros.api.infra.security.jwt.JwtUtil;


//Evitar tratamento de exceção nesta classe. Quanto mais limpa for a classe melhor.
//O tratamento de Exceção fica na ExceptionConfig e a exceção do tipo IllegalArgumentException será convertida em 
//BadRequest

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
	
	@Autowired
	private UsersService  service;

	@GetMapping()
	public ResponseEntity get() {
		List<UserDTO> list = service.getUsers();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/info")
	public UserDTO userInfo(@AuthenticationPrincipal User user) {
		
		//UserDetails userDetails = JwtUtil.getUserDetails();  //Caso não quisesse usar o @AuthenticationPrincipal injetado.
		
		return UserDTO.create(user);
	}
	
	
		
}
