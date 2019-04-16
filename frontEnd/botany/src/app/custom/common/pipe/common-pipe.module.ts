import { NgModule } from '@angular/core';
import { DepartmentPipe } from './department/department.pipe';
import { RolePipe } from './role/role.pipe';

@NgModule({
    declarations: [
        DepartmentPipe,
        RolePipe
    ],
    exports: [
        DepartmentPipe,
        RolePipe
    ]
})
export class CommonPipeModule {

}
