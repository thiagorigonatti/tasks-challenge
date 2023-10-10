import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";

import {Task} from "../model/task";
import {Observable} from "rxjs";
import {Sort} from "@angular/material/sort";

@Injectable({
  providedIn: 'root'
})
export class TasksService {

  private readonly API = "http://localhost:8080/api/tasks" //backend endpoint here;

  constructor(private httpClient: HttpClient) {
  }

  findAll(sort: Sort): Observable<Task[]> {
    const params = new HttpParams().set("sort", sort.active).set("order", sort.direction);
    return this.httpClient.get<Task[]>(this.API, {params});
  }

  save(payload: Partial<Task>) {
    payload.cost = payload.cost?.replaceAll(".","");
    payload.cost = payload.cost?.replaceAll(",",".");
    if (payload._id) return this.replace(payload);
    return this.create(payload);
  }

  findById(id: number) {
    return this.httpClient.get<Task>(`${this.API}/${id}`);
  }

  create(payload: Partial<Task>) {
    return this.httpClient.post<Task>(this.API, payload);
  }

  replace(payload: Partial<Task>) {
    return this.httpClient.put<Task>(`${this.API}/${payload._id}`, payload);
  }

  delete(id: string) {
    return this.httpClient.delete(`${this.API}/${id}`);
  }

}
