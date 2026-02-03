package com.api_rate.filter;

import com.api_rate.dto.ErrorResponse;
import com.api_rate.limiter.FixedWindowRateLimiter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RateLimiterFilter extends OncePerRequestFilter {

    private final FixedWindowRateLimiter rateLimiter;

    public RateLimiterFilter(FixedWindowRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String userId = request.getHeader("X-User-Id");

        if (userId == null || userId.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing X-User-Id header");
            return;
        }

        boolean allowed = rateLimiter.allowedRequest(userId);
//        System.out.println("RateLimiter called for user: " + userId);



        if (!allowed) {
            response.setStatus(429);
            response.setContentType("application/json");
            ErrorResponse errorResponse=new ErrorResponse(429,
                    "Too Many Requests","Rate limit exceeded. Please try again later.");
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(errorResponse));
                    return;
        }


        filterChain.doFilter(request, response);
    }
}
