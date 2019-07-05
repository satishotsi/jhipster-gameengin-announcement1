import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GameenginApplicationSharedModule } from 'app/shared';
import {
  AnnouncementComponent,
  AnnouncementDetailComponent,
  AnnouncementUpdateComponent,
  AnnouncementDeletePopupComponent,
  AnnouncementDeleteDialogComponent,
  announcementRoute,
  announcementPopupRoute
} from './';

const ENTITY_STATES = [...announcementRoute, ...announcementPopupRoute];

@NgModule({
  imports: [GameenginApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AnnouncementComponent,
    AnnouncementDetailComponent,
    AnnouncementUpdateComponent,
    AnnouncementDeleteDialogComponent,
    AnnouncementDeletePopupComponent
  ],
  entryComponents: [
    AnnouncementComponent,
    AnnouncementUpdateComponent,
    AnnouncementDeleteDialogComponent,
    AnnouncementDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GameenginApplicationAnnouncementModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
