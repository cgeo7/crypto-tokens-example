import { Injectable } from '@angular/core';
import { CompatClient, Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { TokenService } from "./token.service";
import { Actions, ActionTypes, ClientConnectedAction } from "../actions/actions";
import { environment } from "../../environments/environment";
import { BehaviorSubject } from "rxjs";

const baseDestinationPrefix = '/app'

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private connected = new BehaviorSubject<boolean>(false)

  constructor(private tokenService: TokenService) {
  }

  private stompClient: CompatClient;

  private readonly incomingTopic = '/topic/tokens';
  private readonly incomingQueue = '/user/queue/tokens';
  private readonly destinationConnected = `${baseDestinationPrefix}/tokens/connected`;
  private readonly destinationTokenCreate = `${baseDestinationPrefix}/tokens/create`;

  connect() {
    this.stompClient = Stomp.over(() => new SockJS(environment.websocketEndpoint));
    this.stompClient.debug = () => {};
    this.stompClient.connect({}, (frame: any) => {

      this.stompClient.subscribe(this.incomingQueue, (message) => {
        const body = JSON.parse(message.body);
        this.handleIncomingMessage(body);
      });

      this.sendAction(new ClientConnectedAction())
    });
  }

  private subscribeToTokensTopic() {
    this.stompClient.subscribe(this.incomingTopic, (message) => {
      const body = JSON.parse(message.body);
      this.handleIncomingMessage(body);
    });
  }

  disconnect() {
    this.stompClient.disconnect(() => this.connected.next(false))
  }

  sendAction(action: Actions) {
    const body = JSON.stringify(action)
    switch (action.type) {
      case ActionTypes.CLIENT_CONNECTED:
        this.stompClient.send(this.destinationConnected, {}, body);
        break;
      case ActionTypes.TOKEN_CREATED:
        this.stompClient.send(this.destinationTokenCreate, {}, body);
        break;
    }
  }

  get isConnected$() {
    return this.connected.asObservable();
  }

  private handleIncomingMessage(action: Actions) {
    switch (action.type) {
      case ActionTypes.CLIENT_CONNECTED:
        break;
      case ActionTypes.CLIENT_CONNECTED_RESPONSE:
        this.connected.next(true)
        this.tokenService.updateTokens(action.payload)
        this.subscribeToTokensTopic()
        break;
      case ActionTypes.TOKEN_UPDATED:
        this.tokenService.updateToken(action.payload)
        break;
      case ActionTypes.TOKEN_CREATED_FAIL:
      case ActionTypes.TOKEN_CREATED_SUCCESS:
        this.tokenService.createMessage(action)
        break;
    }
  }

}
