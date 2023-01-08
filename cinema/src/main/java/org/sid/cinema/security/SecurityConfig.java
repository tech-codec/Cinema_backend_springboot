package org.sid.cinema.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Bean
    public DaoAuthenticationProvider authProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        /*auth.inMemoryAuthentication()
                .withUser("admin").password(encoder.encode("1234")).roles("ADMIN","USER")
                .and()
                .withUser("user").password(encoder.encode("1234")).roles("USER");

        auth.userDetailsService(userDetailsService)
        .passwordEncoder(bCryptPasswordEncoder);*/
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        //http.formLogin().permitAll();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers( "/dist/**","/plugins/**","/static/**","/resources/**","/password/**","/login/**","/request/**","/userRegister/**","/verify/**","/register/**","/imageFilm/**","/uploadPhoto/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/villes/**","/cinemas/**","/films/**","/places/**","/projections/**","/salles/**","/seances/**","/tickets/**","/categories/**");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/villes/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
        http.addFilterBefore(new JWTAuthorizationFiler(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring()
                .antMatchers("/resources/**", "/static/**");
    }
}
