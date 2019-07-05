import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnnouncement } from 'app/shared/model/announcement.model';

@Component({
  selector: 'jhi-announcement-detail',
  templateUrl: './announcement-detail.component.html'
})
export class AnnouncementDetailComponent implements OnInit {
  announcement: IAnnouncement;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ announcement }) => {
      this.announcement = announcement;
    });
  }

  previousState() {
    window.history.back();
  }
}
