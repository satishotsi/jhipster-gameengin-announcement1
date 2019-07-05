import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContest } from 'app/shared/model/contest.model';

type EntityResponseType = HttpResponse<IContest>;
type EntityArrayResponseType = HttpResponse<IContest[]>;

@Injectable({ providedIn: 'root' })
export class ContestService {
  public resourceUrl = SERVER_API_URL + 'api/contests';

  constructor(protected http: HttpClient) {}

  create(contest: IContest): Observable<EntityResponseType> {
    return this.http.post<IContest>(this.resourceUrl, contest, { observe: 'response' });
  }

  update(contest: IContest): Observable<EntityResponseType> {
    return this.http.put<IContest>(this.resourceUrl, contest, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
