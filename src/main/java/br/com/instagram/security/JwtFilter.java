package br.com.instagram.security;

import br.com.instagram.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
public class JwtFilter extends BasicAuthenticationFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final AuthService authService;

    public JwtFilter(AuthenticationManager authenticationManager,
                     HandlerExceptionResolver handlerExceptionResolver,
                     AuthService authService) {
        super(authenticationManager);
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authService.findById(getClientHeaderFromRequest(request)),
                    null,
                    null
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info("Current client: {}", authenticationToken.getPrincipal());
            chain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

    private String getClientHeaderFromRequest(HttpServletRequest request) {
        return Optional
                .ofNullable(request.getHeader("client"))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Missing client header"));
    }
}
