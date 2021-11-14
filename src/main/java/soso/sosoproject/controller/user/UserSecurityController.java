package soso.sosoproject.controller.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import soso.sosoproject.controller.user.handler.UserLoginFailHandler;
import soso.sosoproject.controller.user.handler.UserLoginSuccessHandler;
import soso.sosoproject.service.Account.MemberService;

@Order(1)
@Configuration
@EnableWebSecurity
public class UserSecurityController extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/Marco - Doc/**", "/assets/**",
                "/css2/**", "/fonts/**", "/img/**", "/scss/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().ignoringAntMatchers("/user/account/sameEmail/check", "/user/account/certificationEmail/check",
                "/user/order/menu", "/user/orderMenu/pay/ammount", "/user/Reserve/soso/order", "/user/Reserve/soso/order/normal");

        http
                .antMatcher("/user/**")
                .authorizeRequests()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/oauth2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/account/login")
                .loginProcessingUrl("/user/login")
                .successHandler(new UserLoginSuccessHandler()).failureHandler(new UserLoginFailHandler())
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/user/index")
                .invalidateHttpSession(true)
                .and().rememberMe().userDetailsService(userDetailsService()).tokenValiditySeconds(2900000);

        http
                .sessionManagement()
                .maximumSessions(50) //50 로그인 제한
                .maxSessionsPreventsLogin(true)
                .sessionRegistry(sessionRegistry());

    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new MemberService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
