import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { _HttpClient } from '@delon/theme';
import { ADDRESS } from '@env/environment';
import { Page } from '../../../custom/common/bean/page';
import { NzModalService, NzNotificationService } from 'ng-zorro-antd';
import { ModalType } from '../../../custom/common/bean/modal.type';

@Component({
    selector: 'role-manage',
    templateUrl: './role-manage.component.html'
})
export class RoleManageComponent implements OnInit {

    public dataSet = [];

    public modalVisible = false; // 角色modal弹出框控制
    public isButtonLoading = false;
    public modalTitle: string; // modal title
    public enumModalType = ModalType;
    public modalType: ModalType; // modal type
    public roleForm: FormGroup; // 表单

    public pageIndex = 1;
    public pageSize = 10;
    public total = 0;

    constructor(
        private httpClient: _HttpClient,
        private formBuilder: FormBuilder,
        private modalService: NzModalService,
        private notification: NzNotificationService) {
        this.roleForm = formBuilder.group({
            roleId: [null],
            roleName: [null, Validators.required],
            roleDescription: [null, Validators.required],
            roleSort: [null, [Validators.required, Validators.min(10)]],
            roleUsingStatus: [null, [Validators.required]],
        });
    }

    set roleUsingStatus(status: boolean) {
        this.roleForm.get('roleUsingStatus').setValue(status);
    }
    get roleUsingStatus() {
        return this.roleForm.get('roleUsingStatus').value;
    }
    set roleSort(sort: number) {
        this.roleForm.get('roleSort').setValue(sort);
    }
    get roleSort() {
        return this.roleForm.get('roleSort').value;
    }

    ngOnInit(): void {
        this.getTableData();
    }

    getTableData() {
        const data = {
            page: this.pageIndex,
            size: this.pageSize
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysRole/querySysRole', data)
            .subscribe((response: any) => {
                const page: Page = response.data;
                this.dataSet = page.content;
                this.total = page.totalElements;
            });
    }

    // 表格中是否可用的 switch 开关
    clickSwitch(item: any) {
        const data = {
            roleId: item.roleId
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysRole/updateRoleUsing', data)
            .subscribe((response: any) => {
                item.roleUsing = response.data.roleUsing;
            });
    }

    // 添加角色
    addRole() {
        this.roleForm.reset();
        this.roleUsingStatus = true;
        this.modalTitle = '添加角色';
        this.modalType = ModalType.Create;
        this.modalVisible = true;
        // 查询设置 role sort
        this.httpClient.post(ADDRESS.sysUrl + 'sysRole/nextRoleSort')
            .subscribe((response: any) => {
                this.roleSort = response.data;
            });
    }
    addRoleCommit() {
        this.isButtonLoading = true;
        const data = {
            ...this.roleForm.value,
            roleUsing: this.roleUsingStatus ? '0' : '1',
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysRole/addRole', data)
            .subscribe((response: any) => {
                this.notification.success('添加状态', '添加角色成功');
                // 刷新表格
                this.getTableData();
                this.isButtonLoading = false;
                this.modalVisible = false;
            });
    }
    // 修改角色
    updateRole(item: any) {
        this.roleForm.reset();
        this.roleForm.setValue({
            roleId: item.roleId,
            roleName: item.roleName,
            roleDescription: item.roleDescription,
            roleSort: item.roleSort,
            roleUsingStatus: item.roleUsing === '0' ? true : false
        });
        this.modalTitle = '修改角色';
        this.modalType = ModalType.Update;
        this.modalVisible = true;
    }
    updateRoleCommit() {
        this.isButtonLoading = true;
        const data = {
            ...this.roleForm.value,
            roleUsing: this.roleUsingStatus ? '0' : '1',
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysRole/updateRole', data)
            .subscribe((response: any) => {
                this.notification.success('修改状态', '修改角色成功');
                // 刷新表格
                this.getTableData();
                this.isButtonLoading = false;
                this.modalVisible = false;
            });
    }
    // 关闭角色 modal
    handleCancel() {
        this.modalVisible = false;
    }

    // 删除菜单
    deleteRole(item: any) {
        this.modalService.confirm({
            nzTitle  : '<i>确认删除这个角色?</i>',
            nzContent: '<b>删除后无法恢复</b>',
            nzOnOk   : () => {
                this.httpClient.post(ADDRESS.sysUrl + 'sysRole/deleteRole', item)
                    .subscribe((response: any) => {
                        this.notification.success('删除状态', '删除角色成功');
                        // 刷新表格
                        this.getTableData();
                    });
            }
        });
    }

}
