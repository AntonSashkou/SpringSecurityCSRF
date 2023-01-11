package com.sashkou.SpringSecurityCSRF.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CustomCsrfTokenRepository implements CsrfTokenRepository {

    private static final String headerName = "X-CSRF-TOKEN";

    private final Map<String, Token> tokenRepository = new HashMap<>();

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        Optional<Token> tokenValueOpt = Optional.ofNullable(tokenRepository.get(username));

        if (tokenValueOpt.isEmpty()) {
            Token tokenObj = new Token();
            tokenObj.setUser(username);
            tokenObj.setToken(token.getToken());
            tokenRepository.put(username, tokenObj);
        }
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return new DefaultCsrfToken(headerName, "_csrf", generateRandomToken());
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        Optional<Token> tokenOpt = Optional.empty();
        String user = request.getParameter("username");
        if (Objects.nonNull(user)) {
            tokenOpt = Optional.ofNullable(tokenRepository.get(user));
        } else if (Objects.nonNull(
                SecurityContextHolder.getContext().getAuthentication())) {
            Object principal =
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = "";
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
            tokenOpt = Optional.ofNullable(tokenRepository.get(username));
        }

        if (tokenOpt.isPresent()) {
            Token tokenValue = tokenOpt.get();
            return new DefaultCsrfToken(
                    "X-CSRF-TOKEN",
                    "_csrf",
                    tokenValue.getToken());
        }
        return null;
    }

    private String generateRandomToken() {
        int random = ThreadLocalRandom.current().nextInt();
        return random + System.currentTimeMillis() + "";
    }
}
