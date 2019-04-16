import { ChangeDetectorRef, OnDestroy, Pipe, PipeTransform } from '@angular/core';

import { ResultBean } from '../../bean/resultBean';
import { PromiseStrategy } from '../base/base.pipe';
import { RolePipeData } from '../../bean/role';
import { RoleService } from '../../service/role.service';

@Pipe({name: 'rolePipe', pure: false})
export class RolePipe implements PipeTransform, OnDestroy {

    private _promise: Promise<any>|null = null;
    private _obj: Promise<ResultBean<RolePipeData>>|null = null;
    private _strategy: PromiseStrategy = new PromiseStrategy();

    private _pipeData: ResultBean<RolePipeData> = null;

    constructor(private roleService: RoleService,
                private _ref: ChangeDetectorRef) { }

    ngOnDestroy(): void {
        if (this._promise) {
            this._dispose();
        }
    }

    transform(value: string): any {
        if (!this._obj) {
            this._obj = this.roleService.getRolePipeData();
            this._subscribe();
        }
        if (this._pipeData) {
            return this._pipeData.data[value];
        }
        return null;
    }

    private _subscribe(): void {
        this._promise = this._strategy.createSubscription(
            this._obj, (result: ResultBean<RolePipeData>) => this._updateLatestValue(result));
    }

    private _updateLatestValue(result: ResultBean<RolePipeData>): void {
        this._pipeData = result;
    }

    private _dispose(): void {
        this._strategy.dispose(this._promise);
        this._obj = null;
        this._promise = null;
    }

    // transform(value: string, roleService: RoleService): Observable<String> {
    //     return roleService.getRolePipeData().pipe(map(result => result.data[value]));
    // }

}
