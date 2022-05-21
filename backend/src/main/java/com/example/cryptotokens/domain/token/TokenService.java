package com.example.cryptotokens.domain.token;

import static com.example.cryptotokens.config.Constants.ENDPOINT_TOKENS_CONNECTED;

import com.example.cryptotokens.actions.TokenUpdatedAction;
import com.example.cryptotokens.dto.EnrichedToken;
import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    private final SimpMessagingTemplate messagingTemplate;

    private final CopyOnWriteArrayList<EnrichedToken> enrichedTokens = new CopyOnWriteArrayList<>();


    public void init() {
        tokenRepository.findAll().stream()
            .map(t -> new EnrichedToken(t, createRandomPrice(), createRandomVolume()))
            .forEach(enrichedTokens::add);
    }

    public void updateToken() {
        int index = ThreadLocalRandom.current().nextInt(enrichedTokens.size());
        var token = enrichedTokens.get(index);
        token.setPrice(createRandomPrice());
        token.setVolume(createRandomVolume());
        messagingTemplate.convertAndSend(ENDPOINT_TOKENS_CONNECTED, new TokenUpdatedAction(token));
    }

    public void createToken(Token token) {
        Token savedToken = this.tokenRepository.save(token);
        EnrichedToken enrichedToken = new EnrichedToken(
            savedToken.getToken(), savedToken.getSymbol(), createRandomPrice(), createRandomVolume()
        );
        enrichedTokens.add(enrichedToken);
        messagingTemplate.convertAndSend(ENDPOINT_TOKENS_CONNECTED, new TokenUpdatedAction(enrichedToken));
    }

    private int createRandomVolume() {
        return ThreadLocalRandom.current().nextInt(0, 1000);
    }

    private double createRandomPrice() {
        double price = ThreadLocalRandom.current().nextDouble(1, 50000);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(6);
        nf.setGroupingUsed(false);
        return Double.parseDouble(nf.format(price));
    }

    public List<EnrichedToken> getEnrichedTokens() {
        return enrichedTokens;
    }

}