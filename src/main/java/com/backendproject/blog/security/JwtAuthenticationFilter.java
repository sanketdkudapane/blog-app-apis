package com.backendproject.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//1. Get Token from Request and fetch username from it
		String requestToken = request.getHeader("Authorization");
		System.out.println("RequestToken: " + requestToken);
		
		String username = null, token = null;
		
		if(requestToken != null && requestToken.startsWith("Bearer")) {
			try{
				token = requestToken.substring(7);
				username = this.jwtTokenHelper.getUsernameFromToken(token);
			}
			catch(IllegalArgumentException ex) {
				System.out.println("Exception: Unable to get JWT token");
			}
			catch(ExpiredJwtException ex) {
				System.out.println("Exception: JWT token has expired");
			}
			catch(MalformedJwtException ex) {
				System.out.println("Exception: Invalid JWT token");
			}
			catch(Exception ex) {
				System.out.println("Exception: "+ ex.getMessage());
			}
		}
		else {
			System.out.println("JWT Token is null OR  it does not starts with Bearer");
		}
		
		//2. Now validate the token
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if(this.jwtTokenHelper.validateToken(token, userDetails)) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				System.out.println("Inavlid JWT Token");
			}
		}
		else {
			System.out.println("Username is null OR context is not null");
		}
		filterChain.doFilter(request, response);
	}

}
