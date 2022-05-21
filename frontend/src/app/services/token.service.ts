import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable, Subject } from "rxjs";
import { Token } from "../model/token";
import { ActionTypes, TokenCreateFailAction, TokenCreateSuccessAction } from "../actions/actions";
import { ToastMessage } from "../model/toast-message";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  private tokens = new BehaviorSubject<{[key: string]: Token}>({ });
  private currentTokenSymbol: string;
//--disable-host-check
  private messages = new Subject<ToastMessage>();

  constructor() { }

  get tokens$(): Observable<Token[]> {
    return this.tokens.asObservable().pipe(
      map(tokenMap => Object.values(tokenMap)
        .sort((a, b) => a.token.localeCompare(b.token))
      )
    );
  }

  get messages$(): Observable<ToastMessage> {
    return this.messages.asObservable();
  }

  updateTokens(tokens: Token[]) {
    const tokenMap = tokens.reduce((acc, tok) => ({...acc, [tok.symbol]: tok}), {})
    this.tokens.next(tokenMap);
  }

  updateToken(token: Token) {
    const tokenMap = this.tokens.getValue()
    tokenMap[token.symbol] = { ...token, highlight: true }
    if (this.currentTokenSymbol && this.currentTokenSymbol !== token.symbol) {
      tokenMap[this.currentTokenSymbol].highlight = false;
    }
    this.currentTokenSymbol = token.symbol;
    this.tokens.next(tokenMap);
  }

  createMessage(actions: TokenCreateFailAction | TokenCreateSuccessAction) {
    switch (actions.type) {
      case ActionTypes.TOKEN_CREATED_FAIL:
        this.messages.next({title: 'Failure', description: 'Token creation failed.', type: 'error'})
        break;
      case ActionTypes.TOKEN_CREATED_SUCCESS:
        this.messages.next({title: 'Success', description: 'Token creation succeeded.', type: 'success'})
        break;
    }
  }

}
