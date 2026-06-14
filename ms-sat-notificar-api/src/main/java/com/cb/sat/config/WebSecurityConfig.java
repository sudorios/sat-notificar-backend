package com.cb.sat.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cb.sat.core.filter.JwtAuthenticationEntryPoint;
import com.cb.sat.core.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	private static final String[] AUTH_WHITELIST = { "/configuration/ui", "/webjars/**", "/swagger-ui/**",
			"/swagger-resources", "/configuration/security", "/v3/api-docs/**", "/api/web/**" };

	private static final String[] URL_ACCESS = { "http://localhost:4200",
			"http://ec2-3-134-227-179.us-east-2.compute.amazonaws.com",
			"http://ec2-18-222-140-59.us-east-2.compute.amazonaws.com",
			"http://ec2-3-147-0-234.us-east-2.compute.amazonaws.com",
			"http://ec2-3-146-177-151.us-east-2.compute.amazonaws.com", "http://inspecciones.grupoenergia.pe",
			"https://inspecciones.grupoenergia.pe" };

	private final UserDetailsService userDetailsService;

	private final JwtAuthenticationEntryPoint unauthorizedHandler;

	public WebSecurityConfig(@Lazy UserDetailsService userDetailsService,
			@Lazy JwtAuthenticationEntryPoint unauthorizedHandler) {
		this.userDetailsService = userDetailsService;
		this.unauthorizedHandler = unauthorizedHandler;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider(PasswordEncoder encoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(encoder);
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public JwtAuthenticationFilter authenticationTokenFilterBean() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public SecurityFilterChain filterChain(
			org.springframework.security.config.annotation.web.builders.HttpSecurity http,
			DaoAuthenticationProvider authProvider) throws Exception {

		http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
				.authenticationProvider(authProvider).authorizeHttpRequests(
						auth -> auth.requestMatchers(SecurityUtil.AUTENTICACION_PUBLIC, SecurityUtil.PUBLIC).permitAll()
								.requestMatchers(AUTH_WHITELIST).permitAll().anyRequest().authenticated());

		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(List.of(URL_ACCESS));
		config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "responseType", "Authorization"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}