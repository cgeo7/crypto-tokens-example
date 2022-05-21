package com.example.cryptotokens.actions;

import com.example.cryptotokens.dto.EnrichedToken;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TokenUpdatedAction extends PayloadAction<EnrichedToken> {

    public TokenUpdatedAction(EnrichedToken payload) {
        super(ActionType.TOKEN_UPDATED, payload);
    }
}
