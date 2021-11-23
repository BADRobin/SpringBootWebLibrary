package oleg.bryl.springbootweblibrary.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp) {
        this.userDetailsServiceImp = userDetailsServiceImp;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImp);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/library.png", "/css/**", "/locale", "/js/**", "/style.css").permitAll()
                .antMatchers("/console/**").permitAll()
                .antMatchers("/index", "/register", "/login", "/welcome", "/").permitAll()
                .antMatchers("/userpanel/**").hasRole("USER")
                .antMatchers("/adminpanel/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                    .and()
                        .formLogin()
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/welcome", true)
                    .and()
                        .logout()
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/");
                http.csrf().disable();
                http.headers().frameOptions().disable();
    }


}
