import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TasksComponent} from "./tasks/tasks.component";
import {TaskFormComponent} from "./task-form/task-form.component";
import {taskResolver} from "./guards/task.resolver";


const routes: Routes = [
  {path: '', component: TasksComponent},
  {path: 'new', component: TaskFormComponent, resolve: {task: taskResolver}},
  {path: 'edit/:id', component: TaskFormComponent, resolve: {task: taskResolver}}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TasksRoutingModule {
}
