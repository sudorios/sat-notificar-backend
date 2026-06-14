package com.cb.sat.app.auth.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cb.sat.app.auth.service.AuthentificationService;
import com.cb.sat.app.security.service.UsuarioService;
import com.cb.sat.core.service.ServiceBase;
import com.cb.sat.domain.security.view.UsuarioDataGrid;

@Service
public class AuthentificationServiceImpl extends ServiceBase implements UserDetailsService, AuthentificationService {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioDataGrid usuarioDataGrid = usuarioService.loadUserByUsername(username);

		User user = new User(usuarioDataGrid.getUsuario(), usuarioDataGrid.getPassword(), true, // enabled,
				true, // accountNonExpired,
				true, // credentialsNonExpired,
				true, // accountNonLocked,
				getAuthority(usuarioDataGrid));
		new AccountStatusUserDetailsChecker().check(user);
		return user;
	}

	private Collection<SimpleGrantedAuthority> getAuthority(UsuarioDataGrid usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		// authorities.add(new SimpleGrantedAuthority(usuario.getRolCodigo()));
		return authorities;
	}
}
