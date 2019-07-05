/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GameenginApplicationTestModule } from '../../../test.module';
import { AnnouncementDeleteDialogComponent } from 'app/entities/announcement/announcement-delete-dialog.component';
import { AnnouncementService } from 'app/entities/announcement/announcement.service';

describe('Component Tests', () => {
  describe('Announcement Management Delete Component', () => {
    let comp: AnnouncementDeleteDialogComponent;
    let fixture: ComponentFixture<AnnouncementDeleteDialogComponent>;
    let service: AnnouncementService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GameenginApplicationTestModule],
        declarations: [AnnouncementDeleteDialogComponent]
      })
        .overrideTemplate(AnnouncementDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnnouncementDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnnouncementService);
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
