import { Injectable } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { ADDRESS } from '@env/environment';
import { Observable, ReplaySubject } from 'rxjs';
import { ResultBean } from '../bean/resultBean';
import { DepartmentData, DepartmentPipeData } from '../bean/department';

@Injectable({
    providedIn: 'root'
})
export class DepartmentService {

    private _pipePromise: Promise<ResultBean<DepartmentPipeData>> = null; // 机构的管道数据
    private _departmentSubject$: ReplaySubject<ResultBean<DepartmentData[]>> = null; // 机构的下拉列表数据
    private refresh = false;

    constructor(private httpClient: _HttpClient) {

    }

    /**
     * 系统调用，在系统机构执行增删改后，
     * 用于通知前端，机构数据需要刷新
     * @param refresh 是否需要刷新
     */
    setRefresh(refresh: boolean) {
        this.refresh = refresh;
    }

    /**
     * 准备机构的下拉列表数据
     */
    prepareDepartmentData(): Observable<ResultBean<DepartmentData[]>> {
        if (this.refresh || !this._departmentSubject$) {
            this._departmentSubject$ = new ReplaySubject(1);
            const _observable$: Observable<ResultBean<DepartmentData[]>> = this.httpClient.post(ADDRESS.sysUrl + 'sysDepartment/getSelectDepartment');
            _observable$.subscribe(this._departmentSubject$);
            return this._departmentSubject$;
        } else {
            return this._departmentSubject$;
        }
    }

    /**
     * 准备机构的管道数据
     */
    prepareDepartmentPipeData(): Promise<ResultBean<DepartmentPipeData>> {
        if (this.refresh || !this._pipePromise) {
            this._pipePromise = this.httpClient.post(ADDRESS.sysUrl + 'sysDepartment/getPipeDepartment')
                .toPromise() as Promise<ResultBean<DepartmentPipeData>>;
        }
        return this._pipePromise;
    }

    /**
     * 返回机构的下拉列表数据
     */
    getDepartmentData() {
        if (this._departmentSubject$) {
            return this._departmentSubject$;
        } else {
            return this.prepareDepartmentData();
        }
    }

    /**
     * 返回机构的管道数据
     */
    getDepartmentPipeData(): Promise<ResultBean<DepartmentPipeData>> {
        if (this._pipePromise) {
            return this._pipePromise;
        } else {
            return this.prepareDepartmentPipeData();
        }
    }

}
