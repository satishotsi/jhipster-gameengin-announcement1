import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { GameenginApplicationSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [GameenginApplicationSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [GameenginApplicationSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GameenginApplicationSharedModule {
  static forRoot() {
    return {
      ngModule: GameenginApplicationSharedModule
    };
  }
}
