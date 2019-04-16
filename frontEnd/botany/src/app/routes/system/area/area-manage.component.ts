import { Component, OnInit } from '@angular/core';
import { ModalType } from '../../../custom/common/bean/modal.type';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzDropdownService, NzFormatEmitEvent, NzModalService, NzNotificationService, NzTreeNode } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { ADDRESS } from '@env/environment';

@Component({
    selector: 'area-manage',
    templateUrl: './area-manage.component.html',
    styleUrls: ['./area-manage.component.less']
})
export class AreaManageComponent implements OnInit {

    public activatedNode: NzTreeNode; // 当前选中的node
    public nodes: NzTreeNode[] = []; // tree node data
    public dataSet: any[] = []; // table data

    public modalVisible = false; // 菜单 modal 控制
    public isButtonLoading = false; // modal button loading
    public modalTitle: string; // modal title
    public enumModalType = ModalType;
    public modalType: ModalType; // modal type
    public areaForm: FormGroup; // 表单

    constructor(private nzDropdownService: NzDropdownService,
                private modalService: NzModalService,
                private notification: NzNotificationService,
                private httpClient: _HttpClient,
                private formBuilder: FormBuilder) {
        this.areaForm = formBuilder.group({
            areaId: [null],
            areaName: [null, [Validators.required]],
            areaDescription: [null, [Validators.required]],
            areaCode: [null, [Validators.required]],
            areaSort: [null, [Validators.required, Validators.min(10)]]
        });
    }

    set areaSort(sort: number) {
        this.areaForm.get('areaSort').setValue(sort);
    }

    ngOnInit(): void {
        const startNode = new NzTreeNode({
            title: '区域树',
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
            areaId: node.key
        };
        node.children.length = 0;
        this.httpClient.post(ADDRESS.sysUrl + 'sysArea/nextLevelArea', data)
            .subscribe((response: any) => {
                const list: any[] = response.data;
                for (const item of list) {
                    item.key = item.areaId;
                    item.title = item.areaName;
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
            areaId: this.activatedNode.key
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysArea/nextLevelArea', data)
            .subscribe((response: any) => {
                this.dataSet = response.data;
            });
    }

    // 添加区域
    addArea() {
        this.areaForm.reset();
        this.modalTitle = '添加区域';
        this.modalType = ModalType.Create;
        this.modalVisible = true;
        const data = {
            areaParentId: this.activatedNode.key
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysArea/nextAreaSort', data)
            .subscribe((response: any) => {
                this.areaSort = response.data;
            });
    }
    addAreaCommit() {
        this.isButtonLoading = true;
        const parentNode = this.activatedNode;
        const data = {
            ...this.areaForm.value,
            areaParentId: parentNode.key
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysArea/addArea', data)
            .subscribe((response: any) => {
                this.notification.success('添加状态', '添加区域成功');
                this.isButtonLoading = false;
                this.modalVisible = false;
                this.getChildrenNodes(parentNode);
                this.getTableData();
            });
    }
    // 修改区域
    updateArea(item) {
        this.areaForm.reset();
        this.areaForm.setValue({
            areaId: item.areaId,
            areaName: item.areaName,
            areaDescription: item.areaDescription,
            areaCode: item.areaCode,
            areaSort: item.areaSort
        });
        this.modalTitle = '修改区域';
        this.modalType = ModalType.Update;
        this.modalVisible = true;
    }
    updateAreaCommit() {
        const data = {
            ...this.areaForm.value
        };
        const parentNode = this.activatedNode;
        this.httpClient.post(ADDRESS.sysUrl + 'sysArea/updateArea', data)
            .subscribe((response: any) => {
                this.notification.success('修改状态', '修改区域成功');
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
    deleteArea(item: any) {
        this.modalService.confirm({
            nzTitle  : '<i>确认删除这个区域?</i>',
            nzContent: '<b>删除后无法恢复</b>',
            nzOnOk   : () => {
                this.httpClient.post(ADDRESS.sysUrl + 'sysArea/deleteArea', item)
                    .subscribe((response: any) => {
                        this.notification.success('删除状态', '删除区域成功');
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
