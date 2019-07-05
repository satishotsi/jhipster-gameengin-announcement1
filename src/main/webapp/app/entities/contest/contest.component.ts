import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IContest } from 'app/shared/model/contest.model';
import { AccountService } from 'app/core';
import { ContestService } from './contest.service';

@Component({
  selector: 'jhi-contest',
  templateUrl: './contest.component.html'
})
export class ContestComponent implements OnInit, OnDestroy {
  contests: IContest[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected contestService: ContestService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.contestService
      .query()
      .pipe(
        filter((res: HttpResponse<IContest[]>) => res.ok),
        map((res: HttpResponse<IContest[]>) => res.body)
      )
      .subscribe(
        (res: IContest[]) => {
          this.contests = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInContests();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContest) {
    return item.id;
  }

  registerChangeInContests() {
    this.eventSubscriber = this.eventManager.subscribe('contestListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
