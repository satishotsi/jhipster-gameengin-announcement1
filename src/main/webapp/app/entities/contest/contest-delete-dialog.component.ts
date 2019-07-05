import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContest } from 'app/shared/model/contest.model';
import { ContestService } from './contest.service';

@Component({
  selector: 'jhi-contest-delete-dialog',
  templateUrl: './contest-delete-dialog.component.html'
})
export class ContestDeleteDialogComponent {
  contest: IContest;

  constructor(protected contestService: ContestService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.contestService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'contestListModification',
        content: 'Deleted an contest'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-contest-delete-popup',
  template: ''
})
export class ContestDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contest }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ContestDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.contest = contest;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/contest', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/contest', { outlets: { popup: null } }]);
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
