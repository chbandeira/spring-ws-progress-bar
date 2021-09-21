package com.example.springwsprogressbar.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader("Authorization");
		if (header == null) {
			// token authorization by stomp client
            header = request.getParameter("token");
        }
		if (header != null && header.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken auth = this.getAuthentication(header.substring(7));
			if (auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		chain.doFilter(request, response);
	}

	// TODO implement your own JWT token Authentication
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if (token != null) {
			UserDetails user = new UserDetails(){

				@Override
				public Collection<? extends GrantedAuthority> getAuthorities() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String getPassword() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String getUsername() {
					// token is used as username for this example
					return token;
				}

				@Override
				public boolean isAccountNonExpired() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isAccountNonLocked() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isCredentialsNonExpired() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isEnabled() {
					// TODO Auto-generated method stub
					return false;
				}
				
			};
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}
}
