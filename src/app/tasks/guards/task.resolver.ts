import {ResolveFn} from '@angular/router';
import {TasksService} from "../service/tasks.service";
import {inject} from "@angular/core";
import {Observable, of} from "rxjs";
import {Task} from "../model/task";

export const taskResolver: ResolveFn<Observable<Task>> = (route, state, service: TasksService = inject(TasksService)) => {

  if (route.params?.['id']) {
    return service.findById(route.params['id']);
  }
  return of({_id: '', name: '', cost: '', rawCost: '', limitDate: '', position: ''});
};
