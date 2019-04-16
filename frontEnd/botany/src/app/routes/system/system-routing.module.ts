import { RouterModule, Routes } from '@angular/router';
import { MenuManageComponent } from './menu/menu-manage.component';
import { NgModule } from '@angular/core';
import { RoleManageComponent } from './role/role-manage.component';
import { AreaManageComponent } from './area/area-manage.component';
import { DepartmentManageComponent } from './department/department-manage.component';
import { UserManageComponent } from './user/user-manage.component';

const routes: Routes = [
    {
        path: 'menu',
        children: [
            { path: 'manage', component: MenuManageComponent, data: { title: '菜单管理' } }
        ]
    },
    {
        path: 'role',
        component: RoleManageComponent,
        data: { title: '角色管理' }
    },
    {
        path: 'area',
        component: AreaManageComponent,
        data: { title: '区域管理' }
    },
    {
        path: 'department',
        component: DepartmentManageComponent,
        data: { title: '机构管理' }
    },
    {
        path: 'user',
        component: UserManageComponent,
        data: { title: '用户管理' }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class SystemRoutingModule {

}
