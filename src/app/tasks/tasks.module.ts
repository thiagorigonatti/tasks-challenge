import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {TasksRoutingModule} from './tasks-routing.module';
import {TasksComponent} from './tasks/tasks.component';
import {MatTableModule} from '@angular/material/table';
import {CdkDrag, CdkDropList, DragDropModule} from '@angular/cdk/drag-drop';
import {MatCardModule} from '@angular/material/card';
import {MatSortModule} from '@angular/material/sort';
import {TasksService} from "./service/tasks.service";
import {TaskDataSource} from "./service/tasks.datasource";
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {TaskFormComponent} from './task-form/task-form.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from '@angular/material/input';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {TaskListComponent} from './task-list/task-list.component';
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
  declarations: [
    TasksComponent,
    TaskFormComponent,
    TaskListComponent
  ],
  imports: [
    CommonModule,
    TasksRoutingModule,
    MatTableModule,
    MatSortModule,
    CdkDrag,
    CdkDropList,
    MatCardModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatSnackBarModule,
    MatDialogModule
  ],
  providers: [TasksService, TaskDataSource],

  exports: [TasksComponent]

})
export class TasksModule {
}
