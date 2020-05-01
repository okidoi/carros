package com.carros.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)  //Habilita a segurança POR METODO
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	
		
	//Removemos a parte do FormLogin Autentication e deixamos somente o BasicAutentication	
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .anyRequest().authenticated() //Aqui está anyRequest, poderiamos fazer somente para GET ou para POST.
                .and().httpBasic()
                .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        //Alternativa para a opção de autenticação em memória. Usamos uma implementação customizada de UserDetaisService
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
        
        //Configuração abaixa é usada quando quero ter 2 tipos de ROLES (user e outra admin) e fazer autenticação em memória        
        /*
        auth
            .inMemoryAuthentication().passwordEncoder(encoder)
            .withUser("user").password(encoder.encode("user")).roles("USER")
            .and()
            .withUser("admin").password(encoder.encode("admin")).roles("USER", "ADMIN");
       */
    }
}

