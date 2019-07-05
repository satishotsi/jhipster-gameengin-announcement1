import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAnnouncement, Announcement } from 'app/shared/model/announcement.model';
import { AnnouncementService } from './announcement.service';
import { IContest } from 'app/shared/model/contest.model';
import { ContestService } from 'app/entities/contest';

@Component({
  selector: 'jhi-announcement-update',
  templateUrl: './announcement-update.component.html'
})
export class AnnouncementUpdateComponent implements OnInit {
  isSaving: boolean;

  contests: IContest[];

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    active: [],
    type: [],
    rewardInfo: [],
    createdDate: [],
    contestId: [],
    contestId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected announcementService: AnnouncementService,
    protected contestService: ContestService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ announcement }) => {
      this.updateForm(announcement);
    });
    this.contestService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IContest[]>) => mayBeOk.ok),
        map((response: HttpResponse<IContest[]>) => response.body)
      )
      .subscribe((res: IContest[]) => (this.contests = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(announcement: IAnnouncement) {
    this.editForm.patchValue({
      id: announcement.id,
      title: announcement.title,
      description: announcement.description,
      active: announcement.active,
      type: announcement.type,
      rewardInfo: announcement.rewardInfo,
      createdDate: announcement.createdDate != null ? announcement.createdDate.format(DATE_TIME_FORMAT) : null,
      contestId: announcement.contestId,
      contestId: announcement.contestId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const announcement = this.createFromForm();
    if (announcement.id !== undefined) {
      this.subscribeToSaveResponse(this.announcementService.update(announcement));
    } else {
      this.subscribeToSaveResponse(this.announcementService.create(announcement));
    }
  }

  private createFromForm(): IAnnouncement {
    return {
      ...new Announcement(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      description: this.editForm.get(['description']).value,
      active: this.editForm.get(['active']).value,
      type: this.editForm.get(['type']).value,
      rewardInfo: this.editForm.get(['rewardInfo']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      contestId: this.editForm.get(['contestId']).value,
      contestId: this.editForm.get(['contestId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnnouncement>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackContestById(index: number, item: IContest) {
    return item.id;
  }
}
