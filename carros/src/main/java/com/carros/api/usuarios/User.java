package com.carros.api.usuarios;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String login;
    private String senha;

    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",  //tabela associativa
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), //Colunas que fazem o join
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return senha; //virá da tabela
    }

    @Override
    public String getUsername() {
        return login; //virá da tabela
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }    
    
    /* usado somente para gerar uma senha encriptografada e imprimir.
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode("123");
        System.out.print(pwd);
    }
    
    */
}
