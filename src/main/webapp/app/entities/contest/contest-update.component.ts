import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IContest, Contest } from 'app/shared/model/contest.model';
import { ContestService } from './contest.service';

@Component({
  selector: 'jhi-contest-update',
  templateUrl: './contest-update.component.html'
})
export class ContestUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: []
  });

  constructor(protected contestService: ContestService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contest }) => {
      this.updateForm(contest);
    });
  }

  updateForm(contest: IContest) {
    this.editForm.patchValue({
      id: contest.id
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contest = this.createFromForm();
    if (contest.id !== undefined) {
      this.subscribeToSaveResponse(this.contestService.update(contest));
    } else {
      this.subscribeToSaveResponse(this.contestService.create(contest));
    }
  }

  private createFromForm(): IContest {
    return {
      ...new Contest(),
      id: this.editForm.get(['id']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContest>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
