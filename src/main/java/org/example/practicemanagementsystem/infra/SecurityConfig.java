package org.example.practicemanagementsystem.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(crsf -> crsf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        //patient
                        .requestMatchers(HttpMethod.POST,"/api/v1/patient").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/patient/delete-patient/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/patient/all").hasRole("ADMIN")

                        //login and register
                        .requestMatchers(HttpMethod.POST,"/api/v1/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/register").permitAll()


                        //doctor
                        .requestMatchers(HttpMethod.POST, "/api/v1/doctor").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/doctor/delete-doctor/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/doctor").hasAnyRole("DOCTOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/doctor/{id}").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/doctor/by-name").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/doctor/all-doctors").hasAnyRole("ADMIN", "DOCTOR")

                        //appointment
                        .requestMatchers(HttpMethod.POST, "/api/v1/appointment").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/appointment").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/appointment/by-doctor").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/appointment/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/appointment/{id}").hasAnyRole("DOCTOR", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/appointment/delete-appointment/{id}").hasRole("ADMIN")

                        //prescription
                        .requestMatchers(HttpMethod.POST, "/api/v1/prescription").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/prescription").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/prescription/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/prescription/by-doctor").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/prescription/delete-prescription/{id}").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
