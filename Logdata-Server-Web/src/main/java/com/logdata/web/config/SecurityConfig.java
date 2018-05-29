package com.logdata.web.config;

import com.logdata.web.handler.CustomLoginSuccessHandler;
import com.logdata.web.handler.CustomRequestMatcher;
import com.logdata.web.handler.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/script/**", "image/**", "/fonts/**", "lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatcher(new CustomRequestMatcher())
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/log-data").authenticated()
                .antMatchers("/crash").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(new CustomLoginSuccessHandler("/"))
                .loginProcessingUrl("/login")
                .failureUrl("/login")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .sessionManagement()
                .maximumSessions(1);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//        auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
    }
}
