import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'contest',
        loadChildren: './contest/contest.module#GameenginApplicationContestModule'
      },
      {
        path: 'announcement',
        loadChildren: './announcement/announcement.module#GameenginApplicationAnnouncementModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GameenginApplicationEntityModule {}
