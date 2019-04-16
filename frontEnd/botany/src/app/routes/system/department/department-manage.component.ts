import { Component, OnInit } from '@angular/core';
import { ModalType } from '../../../custom/common/bean/modal.type';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzDropdownService, NzFormatEmitEvent, NzModalService, NzNotificationService, NzTreeNode } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { ADDRESS } from '@env/environment';

@Component({
    selector: 'department-manage',
    templateUrl: './department-manage.component.html',
    styleUrls: ['./department-manage.component.less']
})
export class DepartmentManageComponent implements OnInit {

    public activatedNode: NzTreeNode; // 当前选中的node
    public nodes: NzTreeNode[] = []; // tree node data
    public dataSet: any[] = []; // table data

    public modalVisible = false; // 菜单 modal 控制
    public isButtonLoading = false; // modal button loading
    public modalTitle: string; // modal title
    public enumModalType = ModalType;
    public modalType: ModalType; // modal type
    public departmentForm: FormGroup; // 表单

    constructor(private nzDropdownService: NzDropdownService,
                private modalService: NzModalService,
                private notification: NzNotificationService,
                private httpClient: _HttpClient,
                private formBuilder: FormBuilder) {
        this.departmentForm = formBuilder.group({
            departmentId: [null],
            departmentName: [null, [Validators.required]],
            departmentDescription: [null, [Validators.required]],
            departmentCode: [null, [Validators.required]],
            departmentSort: [null, [Validators.required, Validators.min(10)]],
            areaId: [null, [Validators.required]]
        });
    }

    set departmentSort(sort: number) {
        this.departmentForm.get('departmentSort').setValue(sort);
    }

    ngOnInit(): void {
        const startNode = new NzTreeNode({
            title: '机构树',
            key: null,
            expanded: true
        });
        this.nodes = [startNode];
        this.activatedNode = startNode;
        this.getChildrenNodes(startNode);
        this.getTableData();
    }

    // 展开 node
    nzExpandChange($event: NzFormatEmitEvent) {
        this.getChildrenNodes($event.node);
    }
    // 获取子 node
    getChildrenNodes(node: NzTreeNode) {
        const data = {
            departmentId: node.key
        };
        node.children.length = 0;
        this.httpClient.post(ADDRESS.sysUrl + 'sysDepartment/nextLevelDepartment', data)
            .subscribe((response: any) => {
                const list: any[] = response.data;
                for (const item of list) {
                    item.key = item.departmentId;
                    item.title = item.departmentName;
                }
                node.addChildren(list);
            });
    }
    // 当前 node
    activateNode(data: NzFormatEmitEvent) {
        if (this.activatedNode !== data.node) {
            this.activatedNode = data.node;
            this.getTableData();
        }
    }
    // 获取表格数据
    getTableData() {
        const data = {
            departmentId: this.activatedNode.key
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysDepartment/nextLevelDepartment', data)
            .subscribe((response: any) => {
                this.dataSet = response.data;
            });
    }

    // 添加机构
    addDepartment() {
        this.departmentForm.reset();
        this.modalTitle = '添加机构';
        this.modalType = ModalType.Create;
        this.modalVisible = true;
        const data = {
            departmentParentId: this.activatedNode.key
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysDepartment/nextDepartmentSort', data)
            .subscribe((response: any) => {
                this.departmentSort = response.data;
            });
    }
    addDepartmentCommit() {
        this.isButtonLoading = true;
        const parentNode = this.activatedNode;
        const data = {
            ...this.departmentForm.value,
            departmentParentId: parentNode.key
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysDepartment/addDepartment', data)
            .subscribe((response: any) => {
                this.notification.success('添加状态', '添加机构成功');
                this.isButtonLoading = false;
                this.modalVisible = false;
                this.getChildrenNodes(parentNode);
                this.getTableData();
            });
    }
    // 修改区域
    updateDepartment(item) {
        this.departmentForm.reset();
        this.departmentForm.setValue({
            departmentId: item.departmentId,
            departmentName: item.departmentName,
            departmentDescription: item.departmentDescription,
            departmentCode: item.departmentCode,
            departmentSort: item.departmentSort,
            areaId: item.areaId
        });
        this.modalTitle = '修改机构';
        this.modalType = ModalType.Update;
        this.modalVisible = true;
    }
    updateDepartmentCommit() {
        this.isButtonLoading = true;
        const data = {
            ...this.departmentForm.value
        };
        const parentNode = this.activatedNode;
        this.httpClient.post(ADDRESS.sysUrl + 'sysDepartment/updateDepartment', data)
            .subscribe((response: any) => {
                this.notification.success('修改状态', '修改机构成功');
                // 修改成功刷新左侧 node
                this.getChildrenNodes(parentNode);
                // 刷新表格
                this.getTableData();
                this.isButtonLoading = false;
                this.modalVisible = false;
            });
    }
    // 关闭modal
    handleCancel() {
        this.modalVisible = false;
    }

    // 删除区域
    deleteDepartment(item: any) {
        this.modalService.confirm({
            nzTitle  : '<i>确认删除这个机构?</i>',
            nzContent: '<b>删除后无法恢复</b>',
            nzOnOk   : () => {
                this.httpClient.post(ADDRESS.sysUrl + 'sysDepartment/deleteDepartment', item)
                    .subscribe((response: any) => {
                        this.notification.success('删除状态', '删除机构成功');
                        // 修改成功刷新左侧 node
                        this.getChildrenNodes(this.activatedNode);
                        // 刷新表格
                        this.getTableData();
                        this.modalVisible = false;
                    });
            }
        });
    }

}
