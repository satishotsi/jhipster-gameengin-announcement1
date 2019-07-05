import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Contest } from 'app/shared/model/contest.model';
import { ContestService } from './contest.service';
import { ContestComponent } from './contest.component';
import { ContestDetailComponent } from './contest-detail.component';
import { ContestUpdateComponent } from './contest-update.component';
import { ContestDeletePopupComponent } from './contest-delete-dialog.component';
import { IContest } from 'app/shared/model/contest.model';

@Injectable({ providedIn: 'root' })
export class ContestResolve implements Resolve<IContest> {
  constructor(private service: ContestService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContest> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Contest>) => response.ok),
        map((contest: HttpResponse<Contest>) => contest.body)
      );
    }
    return of(new Contest());
  }
}

export const contestRoute: Routes = [
  {
    path: '',
    component: ContestComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gameenginApplicationApp.contest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContestDetailComponent,
    resolve: {
      contest: ContestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gameenginApplicationApp.contest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContestUpdateComponent,
    resolve: {
      contest: ContestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gameenginApplicationApp.contest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContestUpdateComponent,
    resolve: {
      contest: ContestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gameenginApplicationApp.contest.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const contestPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ContestDeletePopupComponent,
    resolve: {
      contest: ContestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gameenginApplicationApp.contest.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
