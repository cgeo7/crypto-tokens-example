package com.example.cryptotokens.actions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public abstract class PayloadAction<T> extends Action {
    private T payload;

    public PayloadAction(ActionType type, T payload) {
        super(type);
        this.payload = payload;
    }
}
