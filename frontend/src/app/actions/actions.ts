import { Action } from "./action";
import { Token } from "../model/token";

export enum ActionTypes {
  CLIENT_CONNECTED = "CLIENT_CONNECTED",
  CLIENT_CONNECTED_RESPONSE = "CLIENT_CONNECTED_RESPONSE",
  TOKEN_UPDATED = "TOKEN_UPDATED",
  TOKEN_CREATED = "TOKEN_CREATED",
  TOKEN_CREATED_SUCCESS = "TOKEN_CREATED_SUCCESS",
  TOKEN_CREATED_FAIL = "TOKEN_CREATED_FAIL",
}

export class ClientConnectedAction implements Action {
  readonly type = ActionTypes.CLIENT_CONNECTED;
}

export class ClientConnectedResponseAction implements Action {
  readonly type = ActionTypes.CLIENT_CONNECTED_RESPONSE;
  constructor(public payload: Token[]) {
  }
}

export class TokenUpdatedAction implements Action {
  readonly type = ActionTypes.TOKEN_UPDATED;
  constructor(public payload: Token) {
  }
}

export class TokenCreatedAction implements Action {
  readonly type = ActionTypes.TOKEN_CREATED;
  constructor(public payload: Partial<Token>) {
  }
}

export class TokenCreateSuccessAction implements Action {
  readonly type = ActionTypes.TOKEN_CREATED_SUCCESS;
  constructor(public payload: Partial<Token>) {
  }
}

export class TokenCreateFailAction implements Action {
  readonly type = ActionTypes.TOKEN_CREATED_FAIL;
  constructor(public payload: Partial<Token>) {
  }
}

export type Actions = |
  ClientConnectedAction |
  ClientConnectedResponseAction |
  TokenUpdatedAction |
  TokenCreatedAction |
  TokenCreateFailAction |
  TokenCreateSuccessAction
