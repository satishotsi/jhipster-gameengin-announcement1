import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContest } from 'app/shared/model/contest.model';

@Component({
  selector: 'jhi-contest-detail',
  templateUrl: './contest-detail.component.html'
})
export class ContestDetailComponent implements OnInit {
  contest: IContest;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contest }) => {
      this.contest = contest;
    });
  }

  previousState() {
    window.history.back();
  }
}
