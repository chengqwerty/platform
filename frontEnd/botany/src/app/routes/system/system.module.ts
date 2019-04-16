import { NgModule } from '@angular/core';

import { SharedModule } from '@shared/shared.module';
import { SystemRoutingModule } from './system-routing.module';
import { MenuManageComponent } from './menu/menu-manage.component';
import { IconComponent } from './menu/icon/icon.component';
import { RoleManageComponent } from './role/role-manage.component';
import { AreaManageComponent } from './area/area-manage.component';
import { DepartmentManageComponent } from './department/department-manage.component';
import { CommonComponentModule } from '../../custom/common/component/common-component.module';
import { UserManageComponent } from './user/user-manage.component';
import { CommonPipeModule } from '../../custom/common/pipe/common-pipe.module';

@NgModule({
    imports: [
        SharedModule,
        CommonComponentModule,
        CommonPipeModule,
        SystemRoutingModule
    ],
    declarations: [
        MenuManageComponent,
        IconComponent,
        RoleManageComponent,
        AreaManageComponent,
        DepartmentManageComponent,
        UserManageComponent
    ],
    entryComponents: [
        IconComponent
    ]
})
export class SystemModule {

}
