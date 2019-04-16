import { Injectable } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { ADDRESS } from '@env/environment';
import { Observable } from 'rxjs';
import { ResultBean } from '../bean/resultBean';
import { RoleData, RolePipeData } from '../bean/role';
import { DepartmentPipeData } from '../bean/department';

@Injectable({
    providedIn: 'root'
})
export class RoleService {

    private _pipePromise: Promise<ResultBean<RolePipeData>> = null; // 角色的管道数据
    private pipeObservable: Observable<ResultBean<RolePipeData>>;
    private roleObservable: Observable<ResultBean<RoleData[]>>; // 机构的下拉列表数据
    private refresh = false;

    constructor(private httpClient: _HttpClient) {

    }

    setRefresh(refresh: boolean) {
        this.refresh = refresh;
    }

    prepareRoleData(): Observable<ResultBean<RoleData[]>> {
        if (this.refresh || !this.roleObservable) {
            this.roleObservable = this.httpClient.post(ADDRESS.sysUrl + 'sysRole/getSelectRole');
            return this.roleObservable;
        } else {
            return this.roleObservable;
        }
    }

    prepareRolePipeData(): Promise<ResultBean<RolePipeData>> {
        if (this.refresh || !this._pipePromise) {
            this._pipePromise = this.httpClient.post(ADDRESS.sysUrl + 'sysRole/getPipeRole')
                .toPromise() as Promise<ResultBean<RolePipeData>>;
            return this._pipePromise;
        } else {
            return this._pipePromise;
        }
    }

    getRoleData() {
        if (this.roleObservable) {
            return this.roleObservable;
        } else {
            return this.prepareRoleData();
        }
    }

    getRolePipeData() {
        if (this._pipePromise) {
            return this._pipePromise;
        } else {
            return this.prepareRolePipeData();
        }
    }

}
