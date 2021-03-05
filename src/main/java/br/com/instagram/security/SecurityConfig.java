package br.com.instagram.security;

import br.com.instagram.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final AuthService authService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/auth",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/favicon.ico"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .addFilter(new JwtFilter(authenticationManager(), handlerExceptionResolver, authService));
    }
}
