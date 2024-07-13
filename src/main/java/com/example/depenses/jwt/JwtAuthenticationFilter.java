package com.example.depenses.jwt;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.depenses.services.user.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService,UserDetailsServiceImpl userDetailsService){
        this.jwtService=jwtService;
        this.userDetailsService=userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)throws ServletException,IOException{

                String authHeader= request.getHeader("Authorization");

                if( authHeader==null || !authHeader.startsWith("Bearer ")){
                        filterChain.doFilter(request,response);
                        return;
                }
                String token=authHeader.substring(7);
                String username=jwtService.extractUsername(token);
                
                if(username!=null  && SecurityContextHolder.getContext().getAuthentication()==null){

                    UserDetails userdetails=userDetailsService.loadUserByUsername(username);
                    if(jwtService.isValid(token, userdetails)){
                        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(
                            userdetails,null, userdetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
                filterChain.doFilter(request,response);
            }
            
    }

    
    
