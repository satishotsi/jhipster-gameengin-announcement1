import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Announcement } from 'app/shared/model/announcement.model';
import { AnnouncementService } from './announcement.service';
import { AnnouncementComponent } from './announcement.component';
import { AnnouncementDetailComponent } from './announcement-detail.component';
import { AnnouncementUpdateComponent } from './announcement-update.component';
import { AnnouncementDeletePopupComponent } from './announcement-delete-dialog.component';
import { IAnnouncement } from 'app/shared/model/announcement.model';

@Injectable({ providedIn: 'root' })
export class AnnouncementResolve implements Resolve<IAnnouncement> {
  constructor(private service: AnnouncementService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAnnouncement> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Announcement>) => response.ok),
        map((announcement: HttpResponse<Announcement>) => announcement.body)
      );
    }
    return of(new Announcement());
  }
}

export const announcementRoute: Routes = [
  {
    path: '',
    component: AnnouncementComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gameenginApplicationApp.announcement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnnouncementDetailComponent,
    resolve: {
      announcement: AnnouncementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gameenginApplicationApp.announcement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnnouncementUpdateComponent,
    resolve: {
      announcement: AnnouncementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gameenginApplicationApp.announcement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnnouncementUpdateComponent,
    resolve: {
      announcement: AnnouncementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gameenginApplicationApp.announcement.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const announcementPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AnnouncementDeletePopupComponent,
    resolve: {
      announcement: AnnouncementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gameenginApplicationApp.announcement.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
