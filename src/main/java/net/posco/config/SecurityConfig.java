package net.posco.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import net.posco.user.UserProfileService;

import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserProfileService();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
              .authorizeHttpRequests(authorize -> authorize
                      .requestMatchers("/css/**", "/font/**", 
                      "/img/**", "/js/**", 
                      "/vendor/**", "/login/**", 
                      "/construction/**", "/maintanance/**")
                      .permitAll()
                      .requestMatchers("/users/**").hasAuthority("Admin")
                      .anyRequest()
                      .authenticated())
              .authenticationProvider(authenticationProvider())
              .formLogin(form -> form
                      .loginPage("/login")
                      .defaultSuccessUrl("/")
                      .usernameParameter("email")
                      .permitAll())
              .logout(logout -> logout
                      .logoutUrl("/logout")
                      .logoutSuccessUrl("/login") // comment this line to show logout param
                      .permitAll())
              .rememberMe(me -> me.key("AbcDefgHijklmnOpqrs_1234567890")
              .tokenValiditySeconds(30 * 24 * 60 * 60));
    return http.build();
  }

  // @Bean
  // public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
  //   return (web) -> web
  //       .ignoring()
  //       .requestMatchers("/css/**")
  //       .requestMatchers("/font/**")
  //       .requestMatchers("/img/**")
  //       .requestMatchers("/js/**")
  //       .requestMatchers("/vendor/**");
  // }
}
