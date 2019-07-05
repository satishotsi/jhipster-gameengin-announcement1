import { Moment } from 'moment';
import { IContest } from 'app/shared/model/contest.model';

export const enum AnnouncementType {
  CODE_HUNT = 'CODE_HUNT',
  QUIZ = 'QUIZ',
  CHECKIN = 'CHECKIN',
  SELFIE = 'SELFIE',
  BILLUPLOAD = 'BILLUPLOAD',
  WRITE_REVIEW = 'WRITE_REVIEW',
  FOLLOW_BUSINESS = 'FOLLOW_BUSINESS'
}

export interface IAnnouncement {
  id?: number;
  title?: string;
  description?: string;
  active?: boolean;
  type?: AnnouncementType;
  rewardInfo?: string;
  createdDate?: Moment;
  contestId?: number;
  contestId?: IContest;
}

export class Announcement implements IAnnouncement {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public active?: boolean,
    public type?: AnnouncementType,
    public rewardInfo?: string,
    public createdDate?: Moment,
    public contestId?: number,
    public contestId?: IContest
  ) {
    this.active = this.active || false;
  }
}
