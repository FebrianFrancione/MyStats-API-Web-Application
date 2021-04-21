package com.quickChart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{


    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(new UserDetailsServiceImpl());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }




    @Order(1)
    @Configuration
    public static class JSON extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/chart/json/createUser").permitAll()
                    .antMatchers("/chart/json/**").authenticated()
                    .anyRequest().permitAll()
                    .and()
                    .formLogin()
                    .defaultSuccessUrl("/chart/json/", true)
                    .and()
                    .logout().permitAll()
                    .and()
                    .httpBasic();
        }
    }

    @Order(2)
    @Configuration
    public static class Rest extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers( "/chart/Landing").permitAll()
                    .antMatchers( "/chart/Signup").permitAll()
                    .antMatchers( "/chart/Signup").permitAll()
                    .antMatchers( "/chart/**").authenticated()
                    .anyRequest().permitAll()
                    .and()
                    .formLogin()
                    .defaultSuccessUrl("/chart/", true)
                    .and()
                    .logout().permitAll()
                    .logoutSuccessUrl("/chart/Landing/")
                    .and()
                    .csrf().disable();
            http.authorizeRequests();

        }
    }
}