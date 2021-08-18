package soso.sosoproject.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityController extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index", "/account/**", "/about", "/menu", "/css2/**", "/css/**",
                        "/fonts/**", "/img/**", "/js/**", "/Marco - Doc/**", "/scss/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/account/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
//
//        "/", "/index", "/account/**", "/css2/**", "/css/**",
//                "/fonts/**", "/img/**", "/js/**", "/Marco - Doc/**", "/scss/**"


    }
}
