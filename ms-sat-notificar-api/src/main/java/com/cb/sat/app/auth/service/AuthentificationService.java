package com.cb.sat.app.auth.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthentificationService {
	
	UserDetails loadUserByUsername(String username);
	
}