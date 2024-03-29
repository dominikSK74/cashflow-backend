package pl.sci.cashflowbackend.config;

import org.json.HTTP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.sci.cashflowbackend.jwt.JwtFilter;
import pl.sci.cashflowbackend.user.Role;

@Configuration
class SecurityConfig{

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().httpBasic().and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/user/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/user/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/category/get-all").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/category/add-private-category").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/expenses/add").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/expenses/upload-image").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/products/set-category").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/settings/get-settings").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/api/settings/set-settings").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/expenses/get-data").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/expenses/get-data-year").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/expenses/get-data-day").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/expenses/get-data-week").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/expenses/get-all-user-expenses").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/expenses/delete-expense/").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/expenses/get-expense-by-id").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/expenses/edit-expense").authenticated()
                .anyRequest()
                .authenticated()
                .and().csrf().disable()
                .exceptionHandling().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

