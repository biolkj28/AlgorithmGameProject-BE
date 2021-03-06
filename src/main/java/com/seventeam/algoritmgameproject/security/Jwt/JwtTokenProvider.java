package com.seventeam.algoritmgameproject.security.Jwt;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seventeam.algoritmgameproject.domain.model.User;
import com.seventeam.algoritmgameproject.security.service.UserDetailImpl;
import io.jsonwebtoken.*;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TYPE = "Bearer";
    private static final String AUTHORITIES_KEY = "auth";
    private final String secretKey;
    private final ObjectMapper mapper;

    private static final long TOKEN_VALID_TIME = 1000L*60*30; //30분

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.mapper = new ObjectMapper();
    }

    public String createToken(User user) throws JsonProcessingException {

        String detail = mapper.writeValueAsString(user);
        Date now = new Date();
        return "Bearer " + Jwts.builder()   // 토큰 생성해서 리턴
                .setSubject(user.getUserId())
                .claim(AUTHORITIES_KEY, detail)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME))
                .compact();
    }


    // Token 에 담겨있는 정보를 이용 Authentication 객체 리턴
    public Authentication getAuthentication(String token) throws JsonProcessingException {
        Claims claims = Jwts    // token 을 이용해 Claims 만듬
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String user = claims.get(AUTHORITIES_KEY).toString();
        UserDetailImpl userDetail = new UserDetailImpl(mapper.readValue(user,User.class));
        return new UsernamePasswordAuthenticationToken(userDetail, "", userDetail.getAuthorities());
    }
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(BEARER_TYPE.length(),bearerToken.length());
        }
        return null;
    }

    // Token 유효성 검증 수행
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new JwtException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new JwtException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT 토큰이 잘못되었습니다.");
        }
    }
}
