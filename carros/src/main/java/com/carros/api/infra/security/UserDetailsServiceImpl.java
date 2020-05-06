package com.carros.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.carros.api.usuarios.User;
import com.carros.api.usuarios.UserRepository;


//Responsável por fazer a autenticação.

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
   
	@Autowired
	private UserRepository userRep;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
		
        User user = userRep.findUserByLogin(username);
        
        if(user == null) {
        	throw new UsernameNotFoundException("usuário não encontrado");
        }else {
        	return user; //Como User Implementa UserDetais podemos retornar direto
        }
        
        /* Método que deixava fixa cariação dos usuários/perfis
        if(username.equals("user")) {
            return User.withUsername(username).password(encoder.encode("user")).roles("USER").build();
        } else if(username.equals("admin")) {
            return User.withUsername(username).password(encoder.encode("admin")).roles("USER", "ADMIN").build();
        }
        
        throw new UsernameNotFoundException("usuário não encontrado");
        */

        
    }


}
