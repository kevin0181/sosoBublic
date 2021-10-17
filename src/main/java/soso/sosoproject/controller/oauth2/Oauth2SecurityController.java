package soso.sosoproject.controller.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import soso.sosoproject.controller.oauth2.handler.Oauth2LoginSuccessHandler;
import soso.sosoproject.service.oauth2.Oauth2UserService;

@Order(3)
@Configuration
@EnableWebSecurity
public class Oauth2SecurityController extends WebSecurityConfigurerAdapter {

    @Autowired
    private Oauth2UserService oauth2UserService;

    @Autowired
    private Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().ignoringAntMatchers("/menu/change-menu");

        http
                .oauth2Login().userInfoEndpoint().userService(oauth2UserService)
                .and().successHandler(oauth2LoginSuccessHandler);
    }
}
