package com.cg.hrresource.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.cg.hrresource.security.JwtAuthenticationEntryPoint;
import com.cg.hrresource.security.JwtAuthenticationFilter;





@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

	@Autowired
	private JwtAuthenticationEntryPoint point;

	@Autowired
	private JwtAuthenticationFilter filter;

	@Autowired
	private AuthenticationProvider authProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.cors().and()
		.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/swagger-ui/**").permitAll()
						.requestMatchers("/v3/api-docs/**").permitAll()		// swagger configuration
						.requestMatchers("/api/v1/public/**").permitAll()
						.anyRequest().authenticated())
				.authenticationProvider(authProvider)
				.sessionManagement(mng -> mng.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		
//      http.cors(cors -> cors
//      .configurationSource(request -> {
//          CorsConfiguration config = new CorsConfiguration();
//          config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//          config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//          config.setAllowedHeaders(Arrays.asList("*"));
//          config.setExposedHeaders(Arrays.asList("Authorization"));
//          config.setAllowCredentials(true);
//          return config;
//      })
//  );
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
		
		


	}
}
