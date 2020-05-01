package com.carros.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

	@GetMapping  
	public String get() {
		return "Get Spring Boot";
	}
	
    @GetMapping("/userInfo")
    public UserDetails userInfo(@AuthenticationPrincipal UserDetails user) {
        return user;
    }	
	
	@GetMapping("/login")  
	public String login(@RequestParam("login") String lLogin,
					    @RequestParam("senha") String lSenha) {
		return "Login: " + lLogin + " <br> Senha: " + lSenha ;
	}
	
	//Acessado via http://localhost:8080/login  no Postman onde o tipo é POST, o Body (form-data) deve ter 2 keys-values (login e senha)
	@PostMapping("/login")  
	public String loginPost(@RequestParam("login") String lLogin,
					    	@RequestParam("senha") String lSenha) {
		
		return "Login: " + lLogin + "  Senha: " + lSenha ;
	}	

	@GetMapping("/login/{login}/senha/{senha}")  
	public String login2(@PathVariable("login") String lLogin,
					     @PathVariable("senha") String lSenha) {
		return "Login: " + lLogin + "  Senha: " + lSenha ;
	}
	
	@GetMapping("/carros/{id}")  
	public String getCarroById(@PathVariable("id") String id) {
		return "Carro by " + id;
	}
	
	@GetMapping("/carros/tipo/{tipo}")  
	public String getCarroByTipo(@PathVariable("tipo") String tipo) {
		return "Lista de Carros " + tipo;
	}
	
	@PostMapping()  
	public String post() {
		return "Post Spring Boot";
	}	
	
	@PutMapping()  
	public String put() {
		return "Put Spring Boot";
	}

	@DeleteMapping()  
	public String delete() {
		return "Delete Spring Boot";
	}	

	
}
