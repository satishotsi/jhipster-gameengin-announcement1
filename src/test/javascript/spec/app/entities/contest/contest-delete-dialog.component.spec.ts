/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GameenginApplicationTestModule } from '../../../test.module';
import { ContestDeleteDialogComponent } from 'app/entities/contest/contest-delete-dialog.component';
import { ContestService } from 'app/entities/contest/contest.service';

describe('Component Tests', () => {
  describe('Contest Management Delete Component', () => {
    let comp: ContestDeleteDialogComponent;
    let fixture: ComponentFixture<ContestDeleteDialogComponent>;
    let service: ContestService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GameenginApplicationTestModule],
        declarations: [ContestDeleteDialogComponent]
      })
        .overrideTemplate(ContestDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContestDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContestService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
