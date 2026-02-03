package com.api_rate.config;

import com.api_rate.filter.RateLimiterFilter;
import com.api_rate.limiter.FixedWindowRateLimiter;
import com.api_rate.limiter.RateLimitInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public FixedWindowRateLimiter fixedWindowRateLimiter(){
        return new FixedWindowRateLimiter(5,60_000);
    }

    @Bean
    public RateLimiterFilter rateLimiterFilter(FixedWindowRateLimiter rateLimiter){
        return new RateLimiterFilter(rateLimiter);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            RateLimiterFilter rateLimiterFilter
    )throws Exception{
        http
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(auth->
                        auth.anyRequest().permitAll())
                .addFilterBefore(
                        rateLimiterFilter
                        , UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
}
