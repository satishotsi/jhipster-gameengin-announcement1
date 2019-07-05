/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GameenginApplicationTestModule } from '../../../test.module';
import { ContestComponent } from 'app/entities/contest/contest.component';
import { ContestService } from 'app/entities/contest/contest.service';
import { Contest } from 'app/shared/model/contest.model';

describe('Component Tests', () => {
  describe('Contest Management Component', () => {
    let comp: ContestComponent;
    let fixture: ComponentFixture<ContestComponent>;
    let service: ContestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GameenginApplicationTestModule],
        declarations: [ContestComponent],
        providers: []
      })
        .overrideTemplate(ContestComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContestComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContestService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Contest(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
