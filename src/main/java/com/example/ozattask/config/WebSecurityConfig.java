package com.example.ozattask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/signup/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/", "/resources/**", "/css/**", "/js/**", "/images/**", "/api/**", "/accountRestore/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginPage("/registration")
                .permitAll()
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/login");
    }

}
