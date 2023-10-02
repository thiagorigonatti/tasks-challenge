import {Injectable} from "@angular/core";
import {DataSource} from "@angular/cdk/collections";
import {Task} from "../model/task";
import {BehaviorSubject, map, Observable} from "rxjs";
import {TasksService} from "./tasks.service";
import {Sort} from "@angular/material/sort";

@Injectable()
export class TaskDataSource extends DataSource<Task> {

  tasks$ = new BehaviorSubject<Task[]>([]);
  isLoading$ = new BehaviorSubject<boolean>(false);

  constructor(private taskService: TasksService) {
    super();
  }

  connect(): Observable<Task[]> {
    return this.tasks$.asObservable();
  }

  disconnect(): void {
    this.tasks$.complete();
  }

  loadTasks(sort: Sort): void {
    this.isLoading$.next(true);
    this.taskService.findAll(sort).pipe(
      map((e) => e.map(value => (
        {name: value.name,
          cost: new Intl.NumberFormat('pt-BR', {
            style: 'currency',
            currency: 'BRL',
          }).format(parseFloat(value.cost)).trim(),
          _id: value._id,
          rawCost: value.cost,
          limitDate: value.limitDate,
          position: value.position
        })))).subscribe(value => {
      this.tasks$.next(value);
      this.isLoading$.next(true);
    })
  }
}
