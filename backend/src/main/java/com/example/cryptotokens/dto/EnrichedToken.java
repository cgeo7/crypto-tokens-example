package com.example.cryptotokens.dto;

import com.example.cryptotokens.domain.token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrichedToken {

    public EnrichedToken(Token token, double price, int volume) {
        this.token = token.getToken();
        this.symbol = token.getSymbol();
        this.price = price;
        this.volume = volume;
    }

    private String token;

    private String symbol;

    private double price;

    private int volume;

    @JsonIgnore
    public Token toToken() {
        return new Token(null, token, symbol);
    }
}
