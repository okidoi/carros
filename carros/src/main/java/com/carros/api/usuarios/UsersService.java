package com.carros.api.usuarios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UsersService {
	
	@Autowired
	private UserRepository rep;
	
	public List<UserDTO> getUsers(){
	
		return rep.findAll().stream().map(UserDTO::create).collect(Collectors.toList());
	}

	
}
