package com.inspire12.homepage.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;

    @Autowired
    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select username,password, enabled from user where email=?")
//                .authoritiesByUsernameQuery("select username, authority from authorities where username=?")
//                .passwordEncoder(passwordEncoder());

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/error/**", "/static/**", "/img/**", "/js/**", "/css/**", "/scss/**", "/plugins/**", "/fonts/**");
    }

    @Component
    public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }



    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.httpBasic().disable();


        httpSecurity
                .authorizeRequests()
//                .anyRequest().authenticated()
                .antMatchers("/signup", "/", "/index", "/about").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/*.js").permitAll()
                .antMatchers("/h2-console/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .antMatchers("/board").authenticated();
//                .antMatchers("/resources/**").permitAll().anyRequest().permitAll();
        httpSecurity
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);

        httpSecurity
//                .formLogin()
//                .loginPage("/").permitAll()
//                .and()
                .logout()
                .logoutSuccessUrl("/")
//                .and()
//                .ignoringAntMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().disable();

        httpSecurity.csrf().disable();
    }
}