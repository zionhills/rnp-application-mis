package com.example.zion23182.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
   @Autowired private UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception
    {return http
        .csrf(csrf->csrf.disable())
        .authorizeHttpRequests(auth->{
           auth.requestMatchers("/","/register_account","/success","/admin/**","/signup","/login","css/**","js/**","/image/**","assets/**","appcharts/**","../appcharts/**").permitAll();
           auth.requestMatchers("/admin/**").hasRole("ADMIN");
           auth.requestMatchers("/user/**").hasRole("USER");
           auth.anyRequest().authenticated();
        })
        .formLogin(login->{
            login.loginPage("/login").permitAll(); 
            login.failureUrl("/login?error");
            login.defaultSuccessUrl("/defaultSuccessLogin",true);
        }).build();        
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;

    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
