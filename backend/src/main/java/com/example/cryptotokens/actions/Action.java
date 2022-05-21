package com.example.cryptotokens.actions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ClientConnectedAction.class, name = "CLIENT_CONNECTED"),
    @JsonSubTypes.Type(value = ClientConnectedResponseAction.class, name = "CLIENT_CONNECTED_RESPONSE"),
    @JsonSubTypes.Type(value = TokenUpdatedAction.class, name = "TOKEN_UPDATED"),
    @JsonSubTypes.Type(value = TokenCreatedAction.class, name = "TOKEN_CREATED"),
    @JsonSubTypes.Type(value = TokenCreatedSuccessAction.class, name = "TOKEN_CREATED_SUCCESS"),
    @JsonSubTypes.Type(value = TokenCreatedFailAction.class, name = "TOKEN_CREATED_FAIL"),
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Action {
    private ActionType type;
}
