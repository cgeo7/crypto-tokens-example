package com.example.cryptotokens.controllers;

import static com.example.cryptotokens.config.Constants.DESTINATION_QUEUE_TOKENS;
import static com.example.cryptotokens.config.Constants.INCOMING_ENDPOINT_TOKENS_CONNECTED;
import static com.example.cryptotokens.config.Constants.INCOMING_ENDPOINT_TOKENS_CREATE;

import com.example.cryptotokens.actions.Action;
import com.example.cryptotokens.actions.ClientConnectedResponseAction;
import com.example.cryptotokens.actions.TokenCreatedAction;
import com.example.cryptotokens.actions.TokenCreatedFailAction;
import com.example.cryptotokens.actions.TokenCreatedSuccessAction;
import com.example.cryptotokens.domain.token.TokenService;
import com.example.cryptotokens.dto.EnrichedToken;
import java.util.Comparator;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final TokenService tokenService;

    @MessageMapping(INCOMING_ENDPOINT_TOKENS_CONNECTED)
    @SendToUser(DESTINATION_QUEUE_TOKENS)
    public ClientConnectedResponseAction handleClientConnected() {
        var tokens = tokenService.getEnrichedTokens().stream()
            .sorted(Comparator.comparing(EnrichedToken::getToken))
            .collect(Collectors.toList());
        return new ClientConnectedResponseAction(tokens);
    }

    @MessageMapping(INCOMING_ENDPOINT_TOKENS_CREATE)
    @SendToUser(DESTINATION_QUEUE_TOKENS)
    public Action handleActionCreate(@Payload TokenCreatedAction action) {
        var token = action.getPayload().toToken();
        try {
            tokenService.createToken(token);
            return new TokenCreatedSuccessAction(action.getPayload());
        } catch (Exception e) {
            return new TokenCreatedFailAction(action.getPayload());
        }
    }

}
