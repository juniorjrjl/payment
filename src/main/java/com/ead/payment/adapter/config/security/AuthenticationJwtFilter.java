package com.ead.payment.adapter.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Log4j2
@AllArgsConstructor
public class AuthenticationJwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        try{
            var jwtStr = getTokenHeader(request);
            if (jwtProvider.validateJwt(jwtStr)){
                var id = jwtProvider.getSubjectJwt(jwtStr);
                var roles = jwtProvider.getClaimNameJwt(jwtStr, "roles");
                UserDetails userDetails = UserDetailsImpl.build(UUID.fromString(id), roles);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            log.error("Cannot set User Authentication", e);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenHeader(final HttpServletRequest request){
        var headerAuth = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7);
        }
        return null;
    }

}
