package com.example.cryptotokens;

import com.example.cryptotokens.domain.token.TokenRepository;
import com.example.cryptotokens.domain.token.TokenService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootApplication
public class CryptoTokensExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoTokensExampleApplication.class, args);
    }

    @Bean
    public TokenService tokenService(TokenRepository tokenRepository, SimpMessagingTemplate messagingTemplate) {
        TokenService tokenService = new TokenService(tokenRepository, messagingTemplate);
        tokenService.init();
        return tokenService;
    }
}
