package soso.sosoproject.controller.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import soso.sosoproject.service.oauth2.Oauth2UserService;

@Order(3)
@Configuration
@EnableWebSecurity
public class Oauth2Security extends WebSecurityConfigurerAdapter {

    @Autowired
    private Oauth2UserService oauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http
//                .antMatcher("/oauth2/authorization/**")
//                .authorizeRequests()
//                .antMatchers("/oauth2/**").permitAll()
//                .anyRequest().authenticated()
//                .and().oauth2Login().defaultSuccessUrl("/user/index");
//                .userInfoEndpoint().userService(oauth2UserService);

    }



}
