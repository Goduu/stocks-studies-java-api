package com.goduu.stocksstudies.services;

import static java.util.Collections.emptyList;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goduu.stocksstudies.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
@Configuration
public class TokenAuthenticationService {

  @Value("${jwtExpirationInMs}")
  private long jwtExpirationInMs;

  @Value("${jwtSecret}")
  private String jwtSecret;

  private final String TOKEN_PREFIX = "Bearer";
  private final String HEADER_STRING = "Authorization";

  private final Logger log;

  public TokenAuthenticationService() {
    super();
    log = LoggerFactory.getLogger(this.getClass());
  }

  public String mintJWTHeader(String username) {
    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    String JWT = Jwts.builder().setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)).signWith(key).compact();
    return TOKEN_PREFIX + " " + JWT;
  }

  public void addAuthentication(HttpServletResponse res, String username) {
    String headerValue = mintJWTHeader(username);
    res.addHeader(HEADER_STRING, headerValue);
  }

  private String trimToken(String token) {
    return token.replace(TOKEN_PREFIX, "").trim();
  }

  public String getAuthenticationUser(String token) {
    try {
      SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
      // return JWTParser.parse(token).getParsedString();
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(trimToken(token)).getBody().getSubject();
    } catch (Exception e) {
      log.error("Cannot validate user token `{}`: error thrown - {}", token, e.getMessage());
    }
    return null;
  }

  public Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    if (token != null) {
      // parse the token.
      String user = getAuthenticationUser(token);
      return user != null ? new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
    }
    return null;
  }

  public String generateToken(Authentication authentication) throws UnsupportedEncodingException {

    User userPrincipal = (User) authentication.getPrincipal();

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

    // byte[] keyBytes = Decoder.BASE64.decode(jwtSecret);
    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    return Jwts.builder().setSubject(userPrincipal.getEmail()).setIssuedAt(new Date()).setExpiration(expiryDate)
        .signWith(key).compact();
  }
}
