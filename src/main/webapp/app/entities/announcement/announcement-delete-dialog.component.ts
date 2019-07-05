import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnnouncement } from 'app/shared/model/announcement.model';
import { AnnouncementService } from './announcement.service';

@Component({
  selector: 'jhi-announcement-delete-dialog',
  templateUrl: './announcement-delete-dialog.component.html'
})
export class AnnouncementDeleteDialogComponent {
  announcement: IAnnouncement;

  constructor(
    protected announcementService: AnnouncementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.announcementService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'announcementListModification',
        content: 'Deleted an announcement'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-announcement-delete-popup',
  template: ''
})
export class AnnouncementDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ announcement }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AnnouncementDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.announcement = announcement;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/announcement', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/announcement', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
