1) Enable CORS and disable CSRF.
2) Set session management to stateless.
3) Set unauthorized requests exception handler.
4) Set permissions on endpoints.
5) Add JWT token filter.

private final JwtTokenFilter jwtTokenFilter;

http = http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and();

// Set unauthorized requests exception handler
        http = http
            .exceptionHandling()
            .authenticationEntryPoint(
                (request, response, ex) -> {
                    response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        ex.getMessage()
                    );
                }
            )
            .and();

