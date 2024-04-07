package org.booking.reservation.config.security;

import io.jsonwebtoken.Claims;
import org.booking.reservation.dto.UserDto;

import java.util.function.Function;

public interface JwtService {
    UserDto extractUser(String jwtToken);

    <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver);

    boolean isTokenValid(String jwtToken);
}