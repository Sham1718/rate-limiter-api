# API Rate Limiter (Fixed Window)

A backend-focused API Rate Limiter built with Spring Boot, implementing a per-user fixed window rate limiting algorithm.  
This project demonstrates how to control API traffic at the filter level using clean, thread-safe core logic.

------------------------------------------------------------

FEATURES

- Fixed Window rate limiting algorithm
- Per-user request limiting
- In-memory storage using ConcurrentHashMap
- Thread-safe request counting
- Servlet Filter–based enforcement (before controller execution)
- Proper HTTP status handling (429 Too Many Requests)
- Structured JSON error response
- Clean, minimal backend-only design

------------------------------------------------------------

TECH STACK

- Java 17
- Spring Boot 3.2.x
- Spring Web
- Spring Security
- Maven

------------------------------------------------------------

PROJECT STRUCTURE

src/main/java/com/api_rate

config  
- SecurityConfig.java  

filter  
- RateLimiterFilter.java  

limiter  
- FixedWindowRateLimiter.java  
- RateLimitInfo.java  

dto  
- ErrorResponse.java  

controller  
- TestController.java  

ApiRateApplication.java

------------------------------------------------------------

RATE LIMITING BEHAVIOR

- Algorithm: Fixed Window
- Scope: Per User
- Limit: 5 requests per 60 seconds
- Storage: In-memory (ConcurrentHashMap)
- Enforcement Layer: Servlet Filter
- HTTP Status on Limit Exceed: 429 Too Many Requests
- Retry Header: Retry-After

Requests are blocked before reaching the controller to save system resources.

------------------------------------------------------------

HOW IT WORKS

1. Every incoming request passes through a custom filter
2. The user is identified using the X-User-Id request header
3. Request count is tracked per user within a fixed time window
4. If the limit is exceeded:
   - Request is blocked
   - 429 Too Many Requests is returned
5. Once the window expires, the counter resets automatically

------------------------------------------------------------

HOW TO TEST (POSTMAN)

Endpoint:
GET /test

Header:
X-User-Id : user1

Expected Result:
- Requests 1–5 → Allowed
- Request 6 → 429 Too Many Requests
- After 60 seconds → Allowed again

------------------------------------------------------------

DESIGN NOTES

- Authentication (JWT) is intentionally not included
- The project focuses purely on rate limiting logic
- In real-world systems, the user identifier would typically be extracted from:
  - JWT claims
  - API Gateway
  - Session context

This separation keeps the project focused and easy to reason about.

------------------------------------------------------------

POSSIBLE ENHANCEMENTS

- Sliding Window or Token Bucket algorithm
- Redis-based distributed rate limiting
- JWT-based user identification
- Per-endpoint rate limits
- Configuration via application properties

------------------------------------------------------------

AUTHOR

Shyam  
Backend / Full Stack Developer  
Focused on building clean, scalable backend systems

------------------------------------------------------------

If you find this project useful, consider giving it a star ⭐
