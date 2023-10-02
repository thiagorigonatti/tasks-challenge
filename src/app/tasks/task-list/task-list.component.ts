import {Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {Task} from "../model/task";
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";
import {MatSort, Sort} from "@angular/material/sort";
import {MatTable} from "@angular/material/table";
import {TaskDataSource} from "../service/tasks.datasource";
import {TasksService} from "../service/tasks.service";

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']
})

export class TaskListComponent {

  @Input() taskArr: Task[] = [];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter(false);
  @Output() delete = new EventEmitter(false);

  @ViewChild(MatTable) table!: MatTable<any>;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private taskService: TasksService) {
  }

  readonly displayedColumns: string[] = ['position', '_id', 'name', 'cost', 'dateLimit', 'action']

  datasource = new TaskDataSource(this.taskService);

  onAdd() {
    this.add.emit(true);
  }

  onEdit(task: Task) {
    this.edit.emit(task);
  }

  onDelete(task: Task) {
    this.delete.emit(task);
  }

  drop(event: CdkDragDrop<Number[]>) {
    moveItemInArray(this.taskArr, event.previousIndex, event.currentIndex);
    this.table.renderRows();
  }

  sortTask(sort: Sort): void {
    this.datasource.loadTasks(sort);
    this.datasource.connect().subscribe(value => this.taskArr = value);
  }
}
