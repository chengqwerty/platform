import { Component, OnInit } from '@angular/core';
import { ModalType } from '../../../custom/common/bean/modal.type';
import { ADDRESS } from '@env/environment';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { _HttpClient } from '@delon/theme';
import { NzModalService, NzNotificationService } from 'ng-zorro-antd';
import { DepartmentService } from '../../../custom/common/service/department.service';
import { RoleService } from '../../../custom/common/service/role.service';

@Component({
    selector: 'user-manage',
    templateUrl: './user-manage.component.html'
})
export class UserManageComponent implements OnInit {

    public dataSet: any[] = []; // table data

    public modalVisible = false; // 菜单 modal 控制
    public isButtonLoading = false; // modal button loading
    public modalTitle: string; // modal title
    public enumModalType = ModalType;
    public modalType: ModalType; // modal type
    public userForm: FormGroup; // 表单

    constructor(private modalService: NzModalService,
                private notification: NzNotificationService,
                public departmentService: DepartmentService,
                public roleService: RoleService,
                private httpClient: _HttpClient,
                private formBuilder: FormBuilder) {
        this.userForm = formBuilder.group({
            userId: [null],
            loginName: [null, [Validators.required]],
            userDescription: [null],
            departmentId: [null, [Validators.required]],
            roleIds: [null]
        });
        // 管道数据准备
        this.roleService.prepareRolePipeData();
        this.departmentService.prepareDepartmentPipeData();
    }

    get roleIds() {
        return this.userForm.get('roleIds').value;
    }

    ngOnInit(): void {
        this.getTableData();
    }

    getTableData() {
        this.httpClient.post(ADDRESS.sysUrl + 'sysUser/querySysUser')
            .subscribe((response: any) => {
                this.dataSet = response.data;
            });
    }

    // 添加用户
    addUser() {
        this.userForm.reset();
        this.modalTitle = '添加用户';
        this.modalType = ModalType.Create;
        this.modalVisible = true;
    }
    addUserCommit() {
        this.isButtonLoading = true;
        const sysUserRoleBeanList = [];
        for (const role of this.roleIds) {
            sysUserRoleBeanList.push({roleId: role});
        }
        const data = {
            ...this.userForm.value,
            sysUserRoleBeanList: sysUserRoleBeanList
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysUser/addUser', data)
            .subscribe((response: any) => {
                this.notification.success('添加状态', '添加用户成功');
                this.isButtonLoading = false;
                this.modalVisible = false;
                this.getTableData();
            });
    }
    // 修改用户
    updateUser(item: any) {
        this.userForm.reset();
        const roleIds = [];
        item.sysUserRoleBeanList.forEach((userRole) => {
            roleIds.push(userRole.roleId);
        });
        this.userForm.setValue({
            userId: item.userId,
            loginName: item.loginName,
            userDescription: item.userDescription,
            departmentId: item.departmentId,
            roleIds: roleIds
        });
        this.modalTitle = '修改用户';
        this.modalType = ModalType.Update;
        this.modalVisible = true;
    }
    updateUserCommit() {
        this.isButtonLoading = true;
        const sysUserRoleBeanList = [];
        for (const role of this.roleIds) {
            sysUserRoleBeanList.push({roleId: role});
        }
        const data = {
            ...this.userForm.value,
            sysUserRoleBeanList: sysUserRoleBeanList
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysUser/updateUser', data)
            .subscribe((response: any) => {
                this.notification.success('修改状态', '修改用户成功');
                this.getTableData();
                this.isButtonLoading = false;
                this.modalVisible = false;
            });
    }
    // 关闭modal
    handleCancel() {
        this.modalVisible = false;
    }
    // 删除用户
    deleteUser(item: any) {
        this.modalService.confirm({
            nzTitle  : '<i>确认删除这个用户?</i>',
            nzContent: '<b>删除后无法恢复</b>',
            nzOnOk   : () => {
                this.httpClient.post(ADDRESS.sysUrl + 'sysUser/deleteUser', item)
                    .subscribe((response: any) => {
                        this.notification.success('删除状态', '删除用户成功');
                        // 刷新表格
                        this.getTableData();
                        this.modalVisible = false;
                    });
            }
        });
    }
    
}
