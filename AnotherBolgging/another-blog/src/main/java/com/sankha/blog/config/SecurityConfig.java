package com.sankha.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sankha.blog.security.CustomUserDetailService;
import com.sankha.blog.security.JwtAuthenticationEntryPoint;
import com.sankha.blog.security.JwtAuthenticationFilter;

@SuppressWarnings("deprecation")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc
public class SecurityConfig /* extends WebSecurityConfigurerAdapter */{
	public static final String[] PUBLIC_URLS = {"/api/v1/auth/**", "/v3/api-docs", "/v2/api-docs",
            "/swagger-resources/**", "/swagger-ui/**", "/webjars/**"

    };

	
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception { //
	 * TODO Auto-generated method stub http. csrf() .disable()
	 * .authorizeHttpRequests() .anyRequest() .authenticated() .and() .httpBasic();
	 * 
	 * http.addFilterBefore(jwtAuthenticationFilter,
	 * UsernamePasswordAuthenticationFilter.class); }
	 */
	
	
	/*protected void configure(HttpSecurity http) throws Exception { 
		http. csrf() .disable() .authorizeHttpRequests()
		//.antMatchers("/api/v1/auth/login")
		.antMatchers(PUBLIC_URLS)
        .permitAll()
        .antMatchers(HttpMethod.GET)
        .permitAll()
		.anyRequest() 
		.authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and() .sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtAuthenticationFilter,
				UsernamePasswordAuthenticationFilter.class); 
	}*/
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http. csrf() .disable() .authorizeHttpRequests()
		//.antMatchers("/api/v1/auth/login")
		.antMatchers(PUBLIC_URLS)
        .permitAll()
        .antMatchers(HttpMethod.GET)
        .permitAll()
		.anyRequest() 
		.authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and() .sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtAuthenticationFilter,
				UsernamePasswordAuthenticationFilter.class); 
		http.authenticationProvider(daoAuthenticationProvider());
		DefaultSecurityFilterChain build = http.build();
		return build;
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
	}
	 
/*
	
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(this.customUserDetailService)
		.passwordEncoder(passwordEncoder());
	}*/
	
	 @Bean
	    public DaoAuthenticationProvider daoAuthenticationProvider() {

	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	        provider.setUserDetailsService(this.customUserDetailService);
	        provider.setPasswordEncoder(passwordEncoder());
	        return provider;

	    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
/*
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	*/
	

}
