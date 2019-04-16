import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import {
    NzDropdownContextComponent,
    NzDropdownService,
    NzFormatEmitEvent, NzModalService, NzNotificationService,
    NzTreeComponent,
    NzTreeNode,
} from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { ADDRESS } from '@env/environment';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IconComponent } from './icon/icon.component';
import { ModalType } from '../../../custom/common/bean/modal.type';

@Component({
    selector: 'menu-manage',
    templateUrl: './menu-manage.component.html',
    styleUrls: ['./menu-manage.component.less']
})
export class MenuManageComponent implements OnInit {

    @ViewChild('treeCom')
    private treeCom: NzTreeComponent;
    public dropdown: NzDropdownContextComponent;
    public activatedNode: NzTreeNode;

    public nodes: NzTreeNode[] = []; // tree node data
    public dataSet = []; // table data

    public modalVisible = false; // 菜单 modal 控制
    public isButtonLoading = false; // modal button loading
    public modalTitle: string; // modal title
    public enumModalType = ModalType;
    public modalType: ModalType; // modal type
    public menuForm: FormGroup; // 表单

    constructor(private nzDropdownService: NzDropdownService,
                private modalService: NzModalService,
                private notification: NzNotificationService,
                private httpClient: _HttpClient,
                private formBuilder: FormBuilder) {
        this.menuForm = formBuilder.group({
            menuId: [null], // 修改使用
            menuName: [null, [Validators.required]],
            menuDescription: [null, [Validators.required]],
            menuViewPath: [null],
            menuSort: [null, [Validators.required, Validators.min(10)]],
            menuVisibleStatus: [null, [Validators.required]],
            menuIcon: [null]
        });
    }

    set menuSort(sort: number) {
        this.menuForm.get('menuSort').setValue(sort);
    }
    get menuVisibleStatus() {
        return this.menuForm.get('menuVisibleStatus').value;
    }
    set menuVisibleStatus(visible: boolean) {
        this.menuForm.get('menuVisibleStatus').setValue(visible);
    }
    get menuIcon() {
        return this.menuForm.get('menuIcon').value;
    }
    set menuIcon(icon: string) {
        this.menuForm.get('menuIcon').setValue(icon);
    }

    ngOnInit(): void {
        const startNode = new NzTreeNode({
            title: '菜单树',
            key: null,
            expanded: true
        });
        this.nodes = [startNode];
        this.getChildrenNodes(startNode);
        this.activatedNode = startNode;
        this.getTableData();
    }
    // 展开 node
    nzExpandChange($event: NzFormatEmitEvent) {
        this.getChildrenNodes($event.node);
    }
    // 获取子 node
    getChildrenNodes(node: NzTreeNode) {
        const data = {
            menuId: node.key
        };
        node.children.length = 0;
        this.httpClient.post(ADDRESS.sysUrl + 'sysMenu/nextLevelMenu', data)
            .subscribe((response: any) => {
                const list: any[] = response.data;
                for (const item of list) {
                    item.key = item.menuId;
                    item.title = item.menuName;
                }
                node.addChildren(list);
            });
    }
    // 当前 node
    activeNode(data: NzFormatEmitEvent): void {
        if (this.activatedNode !== data.node) {
            this.activatedNode = data.node;
            this.getTableData();
        }
    }

    // 获取表格数据
    getTableData() {
        const data = {
            menuId: this.activatedNode.key
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysMenu/nextLevelMenu', data)
            .subscribe((response: any) => {
                this.dataSet = response.data;
            });
    }
    // 表格中是否可见的 switch 开关
    clickSwitch(item: any) {
        const data = {
            menuId: item.menuId,
            menuVisible: item.menuVisible
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysMenu/updateMenuVisible', data)
            .subscribe((response: any) => {
                item.menuVisible = response.data.menuVisible;
            });
    }

    /**
     * 添加菜单 modal
     */
    addMenu() {
        this.menuForm.enable();
        this.menuForm.reset();
        this.modalTitle = '添加菜单';
        this.modalType = ModalType.Create;
        this.modalVisible = true;
        this.menuVisibleStatus = true;
        const data = {
            menuParentId: this.activatedNode.key
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysMenu/nextMenuSort', data)
            .subscribe((response: any) => {
                this.menuSort = response.data;
            });
    }
    addMenuCommit() {
        this.isButtonLoading = true;
        const parentNode = this.activatedNode;
        const data = {
            ...this.menuForm.value,
            menuVisible: this.menuVisibleStatus ? '0' : '1',
            menuParentId: parentNode.key
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysMenu/addMenu', data)
            .subscribe((response: any) => {
                this.notification.success('添加状态', '添加菜单成功');
                // 添加成功刷新左侧 node
                this.getChildrenNodes(parentNode);
                // 刷新表格
                this.getTableData();
                this.isButtonLoading = false;
                this.modalVisible = false;
            });
    }
    // 修改菜单
    updateMenu(item: any) {
        this.menuForm.enable();
        this.menuForm.reset();
        this.menuForm.setValue({
            menuId: item.menuId,
            menuName: item.menuName,
            menuDescription: item.menuDescription,
            menuViewPath: item.menuViewPath,
            menuSort: item.menuSort,
            menuVisibleStatus: item.menuVisible === '0' ? true : false,
            menuIcon: item.menuIcon
        });
        this.modalTitle = '修改菜单';
        this.modalType = ModalType.Update;
        this.modalVisible = true;
    }
    updateMenuCommit() {
        this.isButtonLoading = true;
        const parentNode = this.activatedNode;
        const data = {
            ...this.menuForm.value,
            menuVisible: this.menuVisibleStatus ? '0' : '1',
        };
        this.httpClient.post(ADDRESS.sysUrl + 'sysMenu/updateMenu', data)
            .subscribe((response: any) => {
                this.notification.success('修改状态', '修改菜单成功');
                // 修改成功刷新左侧 node
                this.getChildrenNodes(parentNode);
                // 刷新表格
                this.getTableData();
                this.isButtonLoading = false;
                this.modalVisible = false;
            });
    }
    // 查看菜单
    lookMenu(item: any) {
        this.menuForm.enable();
        this.menuForm.reset();
        this.menuForm.setValue({
            menuId: item.menuId,
            menuName: item.menuName,
            menuDescription: item.menuDescription,
            menuViewPath: item.menuViewPath,
            menuSort: item.menuSort,
            menuVisibleStatus: item.menuVisible === '0' ? true : false,
            menuIcon: item.menuIcon
        });
        // 设置 disable
        this.menuForm.disable();
        this.modalTitle = '查看菜单';
        this.modalType = ModalType.Read;
        this.modalVisible = true;
    }
    // 关闭菜单 menu
    handleCancel() {
        this.modalVisible = false;
    }
    // 删除菜单
    deleteMenu(item: any) {
        this.modalService.confirm({
            nzTitle  : '<i>确认删除这个菜单?</i>',
            nzContent: '<b>删除后无法恢复</b>',
            nzOnOk   : () => {
                this.httpClient.post(ADDRESS.sysUrl + 'sysMenu/deleteMenu', item)
                    .subscribe((response: any) => {
                        this.notification.success('删除状态', '删除菜单成功');
                        // 修改成功刷新左侧 node
                        this.getChildrenNodes(this.activatedNode);
                        // 刷新表格
                        this.getTableData();
                        this.modalVisible = false;
                    });
            }
        });
    }

    // 图标选择
    createIconModal(): void {
        const modal = this.modalService.create({
            nzTitle: '图标选择（第一次加载较慢，请耐心等待）',
            nzWidth: '720px',
            nzContent: IconComponent,
            nzBodyStyle: {
                'height': '560px',
                'overflow-y': 'scroll',
            },
            nzComponentParams: {
                selectedIcon: this.menuIcon
            },
            nzFooter: [
                {
                    label: '确定',
                    type: 'primary',
                    onClick: (componentInstance) => {
                        this.menuIcon = componentInstance.selectedIcon as string;
                        modal.close();
                    }
                },
                {
                    label: '关闭',
                    onClick: (componentInstance) => {
                        modal.close();
                    }
                }
            ]
        });
    }
    // 清除图标
    deleteIcon() {
        this.menuIcon = null;
    }

}
