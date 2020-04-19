package es.secdevoops.springboot.basic.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;	

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		if (SecurityConstants.SECURITY_ENABLED) {
			httpSecurity
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.cors().and()
			.csrf().disable()
	    	.authorizeRequests()
				.antMatchers(HttpMethod.POST, SecurityConstants.LOGIN_URL).permitAll()
				.antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_URL).permitAll()
				.antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_ADMIN_URL).permitAll()
				.antMatchers("/",
						"/swagger-ui.html",
						"/swagger-ui/**",
						"/v3/api-docs/**").permitAll()
				.anyRequest().authenticated().and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager()))
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);			
		} else {
			httpSecurity
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.cors().and()
			.csrf().disable()
			.authorizeRequests().antMatchers("/").permitAll();
		}
		
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
		ArrayList<String> allowedMethods = new ArrayList<>();
		allowedMethods.add("*");
		config.setAllowedMethods(allowedMethods);
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
