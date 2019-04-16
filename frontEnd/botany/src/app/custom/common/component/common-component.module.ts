import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { AreaSelectComponent } from './area-select/area-select.component';
import { DepartmentSelectComponent } from './department-select/department-select.component';
import { RoleManageComponent } from '../../../routes/system/role/role-manage.component';
import { RoleSelectComponent } from './role-select/role-select.component';

@NgModule({
    imports: [
        SharedModule
    ],
    declarations: [
        AreaSelectComponent,
        DepartmentSelectComponent,
        RoleSelectComponent
    ],
    exports: [
        AreaSelectComponent,
        DepartmentSelectComponent,
        RoleSelectComponent
    ]
})
export class CommonComponentModule {

}
