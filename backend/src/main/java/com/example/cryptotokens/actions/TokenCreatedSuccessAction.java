package com.example.cryptotokens.actions;

import com.example.cryptotokens.dto.EnrichedToken;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TokenCreatedSuccessAction extends PayloadAction<EnrichedToken> {

    public TokenCreatedSuccessAction(EnrichedToken payload) {
        super(ActionType.TOKEN_CREATED_SUCCESS, payload);
    }
}
