package es.secdevoops.springboot.basic.security;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(SecurityConstants.HEADER_AUTHORIZACION_KEY);
		if(header == null || !header.startsWith(SecurityConstants.TOKEN_BEARER_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
		
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws AuthenticationException {
		String token = request.getHeader(SecurityConstants.HEADER_AUTHORIZACION_KEY);
		if (token != null) {
			Claims body = Jwts.parser()
					.setSigningKey(SecurityConstants.SUPER_SECRET_KEY.getBytes())
					.parseClaimsJws(token.replace(SecurityConstants.TOKEN_BEARER_PREFIX, ""))
					.getBody();
			String user = body.getSubject();
			if(user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<GrantedAuthority>());
			}
			return null;
		}
		return null;
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Claims body) {
		Collection authorities = new ArrayList();
		if(body.get(SecurityConstants.AUTHORITIES_KEY)!=null && 
				body.get(SecurityConstants.AUTHORITIES_KEY).toString()!=null && 
				!body.get(SecurityConstants.AUTHORITIES_KEY).toString().equals(""))
			authorities = Arrays.stream(body.get(SecurityConstants.AUTHORITIES_KEY).toString().split(","))
	                    .map(SimpleGrantedAuthority::new)
	                    .collect(Collectors.toList());
		return authorities;
	}
	
}
