package com.example.studentmanager.config;


import com.example.studentmanager.impl.UserDetailService;
import com.example.studentmanager.oauth.OAuth2LoginSuccessHandler;
import com.example.studentmanager.security.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // cần xác thực 2 bean là UserDetailService và PasswordEncoder để thực hiên Authentication
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // đối tượng AuthenticationProvider thực hiện việc xác thực thông qua 2 trường dữ liệu
    // là username và password từ UserDetailService
    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/api/students/**").hasAuthority("ADMIN")
                .antMatchers("/api/users/**").hasAuthority("ADMIN")
                .antMatchers("/students/edit/**").hasAuthority("ADMIN")
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/verify?code=**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/students", true)
                .permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint().userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2LoginSuccessHandler)
                .defaultSuccessUrl("/students", true)
                .and()
                .logout()
                .deleteCookies()
                .permitAll()
                .and();
        // Set unauthorized requests exception handler
        http.exceptionHandling().accessDeniedPage("/not_permission");
    }

    @Autowired
    private CustomOAuth2UserService oAuth2UserService;

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;


}
