package com.example.cryptotokens.actions;

import com.example.cryptotokens.dto.EnrichedToken;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ClientConnectedResponseAction extends PayloadAction<List<EnrichedToken>> {

    public ClientConnectedResponseAction(List<EnrichedToken> payload) {
        super(ActionType.CLIENT_CONNECTED_RESPONSE, payload);
    }
}
