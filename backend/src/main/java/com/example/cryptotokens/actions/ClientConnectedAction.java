package com.example.cryptotokens.actions;

import com.example.cryptotokens.dto.EnrichedToken;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ClientConnectedAction extends Action {

    public ClientConnectedAction(List<EnrichedToken> payload) {
        super(ActionType.CLIENT_CONNECTED);
    }
}
