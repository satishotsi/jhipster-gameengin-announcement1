/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GameenginApplicationTestModule } from '../../../test.module';
import { ContestUpdateComponent } from 'app/entities/contest/contest-update.component';
import { ContestService } from 'app/entities/contest/contest.service';
import { Contest } from 'app/shared/model/contest.model';

describe('Component Tests', () => {
  describe('Contest Management Update Component', () => {
    let comp: ContestUpdateComponent;
    let fixture: ComponentFixture<ContestUpdateComponent>;
    let service: ContestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GameenginApplicationTestModule],
        declarations: [ContestUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContestUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContestUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContestService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Contest(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Contest();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
