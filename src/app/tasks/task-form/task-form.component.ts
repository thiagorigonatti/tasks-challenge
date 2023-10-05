import {Component, OnInit} from '@angular/core';
import {FormGroup, NonNullableFormBuilder, Validators} from "@angular/forms";
import {TasksService} from "../service/tasks.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ActivatedRoute, Router} from "@angular/router";
import {Task} from "../model/task";


@Component({
  selector: 'app-task-form',
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.scss']
})

export class TaskFormComponent implements OnInit {

  form!: FormGroup

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private taskService: TasksService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private router: Router) {
  }


  ngOnInit(): void {

    const task: Task = this.route.snapshot.data['task'];

    if (task.cost !== '') {
      task.cost = new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL',
      }).format(parseFloat(task.cost)).replace("R$", "").trim();
    }


    this.form = this.formBuilder.group({
      _id: [task._id],


      name: [task.name, [Validators.pattern('^[A-Za-z0-9]+(\\s+[A-Za-z0-9]+)*$'), Validators.required, Validators.minLength(2), Validators.maxLength(128)]],


      cost: [task.cost, [Validators.required,
        Validators.pattern('^\\s*(?:[1-9]\\d{0,2}(?:\\.\\d{3})*|0)(?:,\\d{1,2})?$')]],


      limitDate: [task.limitDate, [Validators.required, Validators.pattern('(^(((0[1-9]|1[0-9]|2[0-8])/(0[1-9]|1[012]))|((29|30|31)/(0[13578]|1[02]))|((29|30)/(0[469]|11)))/(\\d{2}|\\d{2})\\d{2}$)|(^29/02/(\\d{2}|\\d{2})(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)')]]
    });
  }

  onSubmit() {
    if (this.form.valid) {

      this.taskService.save(this.form.value).subscribe(
        {
          next: value => this.snackBar.open(value.name + " saved successfully", "X",
            {duration: 4000, panelClass: "success-snackbar", horizontalPosition: "center", verticalPosition: "top",}
          ),
          error: err => this.snackBar.open(err.error.message, "X",
            {duration: 4000, panelClass: "error-snackbar", horizontalPosition: "center", verticalPosition: "top",}
          ),
          complete: () => this.router.navigate(['/tasks'])
        })
    } else {
      this.snackBar.open('Check all the fields');
    }
  }

  onCancel() {
    this.router.navigate(['/tasks']).then()
  }

  getErrorMessage(fieldName: string) {

    const field = this.form.get(fieldName);
    if (field?.hasError('required')) {
      return "This field is required";
    }

    if (field?.hasError('minlength')) {
      const requiredLength = field?.errors ? field.errors['minlength']['requiredLength'] : 5;
      return `Min length mus to be ${requiredLength}.`;
    }

    if (field?.hasError('maxlength')) {
      const requiredLength = field?.errors ? field.errors['maxlength']['requiredLength'] : 5;
      return `Max length reached ${requiredLength}.`;
    }

    if (field?.hasError('pattern')) {

      if (fieldName === 'limitDate') {
        return "This field must have the following format dd/MM/yyyy and be a valid date";

      } else if (fieldName === 'name') {
        return "Blank space not allowed at the beginning and at the end of the name field, only chars numbers and space between are allowed"

      } else if (fieldName === 'cost') {
        return "Cost field must have the following format: 2.147.483.647,00 (two billion, one hundred and forty-seven million, four hundred and eighty-three thousand, six hundred and forty-seven)"
      }
    }

    if (field?.hasError('min')) {
      if (fieldName === 'cost') {
        return "Minimum value allowed is: 0"
      }
    }

    if (field?.hasError('max')) {
      if (fieldName === 'cost') {
        return "Maximum value allowed is: 2.147.483.647,00"
      }
    }

    return "Check all the fields";
  }
}
