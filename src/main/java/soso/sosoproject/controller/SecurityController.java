package soso.sosoproject.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityController extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index", "/account/**", "/about", "/menu", "/css2/**", "/css/**",
                        "/fonts/**", "/img/**", "/js/**", "/Marco - Doc/**", "/scss/**").permitAll()
                .antMatchers("/").hasRole("USER")
                .antMatchers("/**", "/admin/**", "/assets/**", "/admin_fragment/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/account/login")
                .failureUrl("/account/login-error") // 로그인 실패 시 이동
                .defaultSuccessUrl("/") // 로그인 성공 시 redirect 이동
                .usernameParameter("member_email")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/account/login"))
                .invalidateHttpSession(true)
                .permitAll();


//        "/", "/index", "/account/**", "/about", "/menu", "/css2/**", "/css/**",
//                "/fonts/**", "/img/**", "/js/**", "/Marco - Doc/**", "/scss/**"


    }
}
