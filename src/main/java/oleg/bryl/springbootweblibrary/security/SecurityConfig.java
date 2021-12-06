package oleg.bryl.springbootweblibrary.security;

import oleg.bryl.springbootweblibrary.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImp userDetailsServiceImp;

    /**
     *
     * @param userDetailsServiceImp
     */
    @Autowired
    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp) {
        this.userDetailsServiceImp = userDetailsServiceImp;
    }

    /**
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImp);
    }

    /**
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/library.png", "/css/**", "/locale", "/js/**", "/style.css").permitAll()
                .antMatchers("/console/**", "/paginator-nav").permitAll()
                .antMatchers( "/", "/index", "/register", "/login", "/welcome").permitAll()
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
