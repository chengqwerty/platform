import { OnDestroy, Pipe, PipeTransform } from '@angular/core';
import { DepartmentService } from '../../service/department.service';
import { PromiseStrategy } from '../base/base.pipe';
import { ResultBean } from '../../bean/resultBean';
import { DepartmentPipeData } from '../../bean/department';

@Pipe({name: 'departmentPipe', pure: false})
export class DepartmentPipe implements PipeTransform, OnDestroy {

    private _promise: Promise<any>|null = null;
    private _obj: Promise<ResultBean<DepartmentPipeData>>|null = null;
    private _strategy: PromiseStrategy = new PromiseStrategy();

    private _pipeData: ResultBean<DepartmentPipeData> = null;

    constructor(private departmentService: DepartmentService) { }

    ngOnDestroy(): void {
        if (this._promise) {
            this._dispose();
        }
    }

    // transform<T>(value: string, obj: null): null;
    // transform<T>(value: string, obj: undefined): undefined;
    // transform<T>(value: string, obj: Promise<T>|null|undefined): T|null;
    transform(value: string): any {
        if (!this._obj) {
            this._obj = this.departmentService.getDepartmentPipeData();
            this._subscribe();
        }
        if (this._pipeData) {
            return this._pipeData.data[value];
        }
        return null;
    }

    private _subscribe(): void {
        this._promise = this._strategy.createSubscription(
            this._obj, (result: ResultBean<DepartmentPipeData>) => this._updateLatestValue(result));
    }

    private _updateLatestValue(result: ResultBean<DepartmentPipeData>): void {
        this._pipeData = result;
    }

    private _dispose(): void {
        this._strategy.dispose(this._promise);
        this._obj = null;
        this._promise = null;
    }

    // transform(value: string, departmentService: DepartmentService): any {
    //     const pipeData = departmentService.getDepartmentPipeData();
    //     return pipeData[value];
    // }

    // transform(value: string, departmentService: DepartmentService): Observable<String> {
    //     return departmentService.getDepartmentPipeData().pipe(map(result => result.data[value]));
    // }

}
