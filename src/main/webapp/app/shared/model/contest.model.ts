export interface IContest {
  id?: number;
}

export class Contest implements IContest {
  constructor(public id?: number) {}
}
