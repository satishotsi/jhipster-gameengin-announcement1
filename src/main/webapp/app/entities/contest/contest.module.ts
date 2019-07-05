import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GameenginApplicationSharedModule } from 'app/shared';
import {
  ContestComponent,
  ContestDetailComponent,
  ContestUpdateComponent,
  ContestDeletePopupComponent,
  ContestDeleteDialogComponent,
  contestRoute,
  contestPopupRoute
} from './';

const ENTITY_STATES = [...contestRoute, ...contestPopupRoute];

@NgModule({
  imports: [GameenginApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ContestComponent,
    ContestDetailComponent,
    ContestUpdateComponent,
    ContestDeleteDialogComponent,
    ContestDeletePopupComponent
  ],
  entryComponents: [ContestComponent, ContestUpdateComponent, ContestDeleteDialogComponent, ContestDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GameenginApplicationContestModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
