import { Component, Injector, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { _HttpClient, Menu, MenuService } from '@delon/theme';

import { SomHeaderMenuService } from './som-header-menu.service';
import { ADDRESS } from '@env/environment';

@Component({
    selector: 'som-header-menu',
    template: `
        <ul ngClass="alain-default__nav">
            <li *ngFor="let item of menus" ngClass="header-menu" (click)="changeSubmenu(item)">
                <div ngClass="header-menu-icon"><i nz-icon [type]="item.icon" theme="outline"></i></div>
                <div>{{ item.text }}</div>
            </li>
            <li ngClass="header-menu" (click)="userLogout()">
                <div ngClass="header-menu-icon"><i nz-icon type="exclamation-circle" theme="outline"></i></div>
                <div>退出</div>
            </li>
        </ul>
    `,
    styles: [
        '.header-menu { min-width: 72px; text-align: center; color: #fff; padding: 0 8px; cursor: pointer }',
        '.header-menu:hover { background-color: rgba(255,255,255,.2); }',
        '.header-menu-icon { font-size: 24px }'
    ]
})
export class SomHeaderMenuComponent implements OnInit {

    public menus: Menu[];

    constructor(private somHeaderMenuService: SomHeaderMenuService,
                private injector: Injector,
                private menuService: MenuService,
                private httpClient: _HttpClient) {

    }

    ngOnInit(): void {
        this.somHeaderMenuService.mainMenusChange.subscribe((menus: Menu[]) => {
            this.menus = menus;
            // 默认展开第一个菜单
            if (this.menus.length > 0) {
                this.changeSubmenu(this.menus[0]);
            }
        });
    }

    /**
     * 根据 head menu 获取左侧 菜单
     * @param item is head menu
     */
    changeSubmenu(item: Menu) {
        const data = {
            menuId: item.menuId
        };
        this.httpClient.post(ADDRESS.sysUrl + 'user/sideMenu', data)
            .subscribe((response: any) => {
                this.menuService.clear();
                this.menuService.add(response.data);
            });
    }

    /**
     * 用户退出登录
     */
    userLogout() {
        this.httpClient.post(ADDRESS.sysUrl + 'logout')
            .subscribe((response: any) => {
                this.injector.get(Router).navigateByUrl('/passport/login');
            });
    }

}
