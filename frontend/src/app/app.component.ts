import { Component, OnDestroy, OnInit } from '@angular/core';
import { WebsocketService } from "./services/websocket.service";
import { TokenService } from "./services/token.service";
import { FormBuilder, FormControl, FormGroup } from "@angular/forms";
import { Token } from "./model/token";
import {
  BehaviorSubject,
  combineLatest,
  map,
  Observable,
  scan,
  shareReplay,
  startWith,
  Subject,
  takeUntil,
  tap
} from "rxjs";
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TokenCreatedAction } from "./actions/actions";
import { ToastrConfig, ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {


  private unsubscribe$ = new Subject<void>();



  isConnected$ = this.websocketService.isConnected$;
  filter = new FormControl('');

  modalFormGroup: FormGroup;

  private sortedColumn$ = new BehaviorSubject<string>('');
  private toastMessages$ = this.tokenService.messages$

  private sortDirection$ = this.sortedColumn$.pipe(
    scan<string, { col: string, dir: string }>((sort, val) => {
      return sort.col === (val || 'token')
        ? { col: val, dir: sort.dir === 'desc' ? 'asc' : 'desc' }
        : { col: val, dir: 'desc' }
    }, {col: 'token', dir: 'desc' }),
    shareReplay()
  )

  filteredTokens$ = combineLatest([
    this.filter.valueChanges.pipe(startWith('')),
    this.tokenService.tokens$,
    this.sortDirection$
  ]).pipe(
    map(([searchTerm, tokens, sort]) => {
      //first sort
      const sortedTokens = this.sortByColumn<Token>(tokens, sort.col , sort.dir)

      //then filter
      return searchTerm?.trim() === '' ? sortedTokens : this.search(sortedTokens, searchTerm)
    })
  )

  constructor(
    private websocketService: WebsocketService,
    private tokenService: TokenService,
    private modalService: NgbModal,
    private fb: FormBuilder,
    private toastService: ToastrService
  ) {
  }

  getSortedClass(column: 'token' | 'symbol' | 'price' | 'volume'): Observable<string> {
    return this.sortDirection$.pipe(
      map(sort => {
          return sort.col !== column
            ? 'bi-arrow-down invisible'
            : sort.dir === 'desc' ? 'bi-arrow-down' : 'bi-arrow-up';
        }
      )
    )
  }

  onSort(column: 'token' | 'symbol' | 'price' | 'volume') {
    this.sortedColumn$.next(column)
  }

  ngOnInit(): void {
    this.websocketService.connect();
    this.toastMessages$.pipe(
      takeUntil(this.unsubscribe$),
      tap(message => {
        switch (message.type) {
          case 'error':
            this.toastService.error(message.description, message.title);
            break;
          case 'success':
            this.toastService.success(message.description, message.title);
            break;
        }
      })
    ).subscribe();
  }

  ngOnDestroy(): void {
    this.websocketService.disconnect();
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  private search(tokens: Token[], term: string): Token[] {
    term = term.toLowerCase()
    return tokens.filter(token => {
      return Object.values(token).some(val => val.toString().toLowerCase().includes(term))
    })
  }

  private sortByColumn<T>(list: any[], column: string, direction = 'desc'): T[] {
    return (list || []).sort((a, b) => {
      let aColumn = a[column];
      let bColumn = b[column];

      if (aColumn < bColumn) {
        return (direction === 'desc') ? 1 : -1;
      }
      if (aColumn > bColumn) {
        return (direction === 'desc') ? -1 : 1;
      }
      return 0;
    });
  }

  onAddNewToken(content: any) {
    this.modalFormGroup = this.fb.group({
      token: [''],
      symbol: ['']
    })
    this.modalService.open(content, {centered: false, fullscreen: "md"})
  }

  onModalSave(modal: any) {
    const token: Partial<Token> = {...this.modalFormGroup.value }
    this.websocketService.sendAction(new TokenCreatedAction(token))
    modal.close();
  }

}
