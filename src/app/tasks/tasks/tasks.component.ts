import {Component, OnInit, ViewChild} from '@angular/core';
import {TasksService} from "../service/tasks.service";
import {MatTable} from '@angular/material/table';
import {MatSort} from "@angular/material/sort";
import {TaskDataSource} from "../service/tasks.datasource";
import {ActivatedRoute, Router} from "@angular/router";
import {Task} from "../model/task";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmationDialogComponent} from "../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {map} from "rxjs";


@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})

export class TasksComponent implements OnInit {

  @ViewChild(MatTable) table!: MatTable<any>;
  @ViewChild(MatSort) sort!: MatSort;

  dataSource = new TaskDataSource(this.taskService)

  constructor(
    private taskService: TasksService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar,
    private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.dataSource.loadTasks({active: 'position', direction: 'asc'})
  }

  onAdd() {
    this.router.navigate(['new'], {relativeTo: this.route}).then();
  }

  onEdit(task: Task) {
    this.router.navigate(['edit', task._id], {relativeTo: this.route}).then();
  }

  refresh() {
    this.dataSource.loadTasks({active: 'position', direction: 'asc'})
  }

  onDelete(task: Task) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: 'This task will not be recoverable. Are you sure you want to delete it?'
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {

      if (result) {
        this.taskService.delete(task._id).subscribe(
          {
            next: value => {
              this.refresh();
              this.snackBar.open(task.name + " removed successfully", "X",
                {duration: 4000, panelClass: "success-snackbar", horizontalPosition: "center", verticalPosition: "top",}
              )
            },
            error: err => this.snackBar.open(err.error.message, "X",
              {duration: 4000, panelClass: "error-snackbar", horizontalPosition: "center", verticalPosition: "top",}
            ),
            complete: () => this.router.navigate(['/tasks'])
          })
      }
    });
  }

}


