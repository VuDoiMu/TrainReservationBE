package com.example.train_ticket_management.security;

import com.example.train_ticket_management.filter.CustomAuthenticationFilter;
import com.example.train_ticket_management.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/", "index", "/css/*", "/js/*", "/api/v1", "/token/refresh/**").permitAll();
        http.authorizeRequests().antMatchers("/api/v1/unauth/**").permitAll();
        http.authorizeRequests().antMatchers("/api/v1/management/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().permitAll();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }


   // @Autowired
  //  public AuthEntryPointJwt unauthorizedHandler;
    //  @Autowired
    //   public ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
    //      this.passwordEncoder = passwordEncoder;
    // }
    //   @Override
    //   protected void configure(HttpSecurity http) throws Exception {
    //       http
    //               .cors()
    //               .and()
    //               .csrf().disable()
    //               .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
    //               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
    //               .authorizeRequests()
    //               .antMatchers("/", "index", "/css/*", "/js/*", "/ticket/").permitAll()
    //               .antMatchers("/api/v1/management/**").hasRole(ADMIN.name())
    //               .antMatchers("api/v1/authorized/**").hasRole(USER.name())
    //              .anyRequest()
    //               .authenticated()
    //              .and()
    //              .formLogin();
}

