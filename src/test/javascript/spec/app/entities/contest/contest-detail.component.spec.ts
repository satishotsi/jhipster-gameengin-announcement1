/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GameenginApplicationTestModule } from '../../../test.module';
import { ContestDetailComponent } from 'app/entities/contest/contest-detail.component';
import { Contest } from 'app/shared/model/contest.model';

describe('Component Tests', () => {
  describe('Contest Management Detail Component', () => {
    let comp: ContestDetailComponent;
    let fixture: ComponentFixture<ContestDetailComponent>;
    const route = ({ data: of({ contest: new Contest(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GameenginApplicationTestModule],
        declarations: [ContestDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContestDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContestDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contest).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
