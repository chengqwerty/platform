import { Component, forwardRef, OnInit } from '@angular/core';
import { NzTreeNode } from 'ng-zorro-antd';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { ADDRESS } from '@env/environment';
import { _HttpClient } from '@delon/theme';

@Component({
    selector: 'area-select',
    providers: [
        {
            provide    : NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => AreaSelectComponent),
            multi      : true
        }
    ],
    template: `
        <nz-tree-select
            nzPlaceHolder="请选择区域"
            [nzDropdownMatchSelectWidth]="true"
            [nzDropdownStyle]="{ 'max-height': '300px' }"
            [(ngModel)]="value"
            (ngModelChange)="onModelChange($event)"
            [nzNodes]="nodes">
        </nz-tree-select>
    `
})
export class AreaSelectComponent implements ControlValueAccessor, OnInit {

    public value: string;
    public nodes: NzTreeNode[] = [];

    private onChange: (value: string) => void;

    constructor(private httpClient: _HttpClient) { }

    ngOnInit(): void {
        this.httpClient.post(ADDRESS.sysUrl + 'sysArea/getSelectArea')
            .subscribe((response: any) => {
                const list: any[] = response.data;
                for (const node of list) {
                    this.nodes.push(new NzTreeNode(node));
                }
            });
    }

    writeValue(obj: any): void {
        this.value = obj;
    }

    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    registerOnTouched(fn: any): void {

    }

    setDisabledState?(isDisabled: boolean): void {

    }

    onModelChange($event) {
        this.onChange($event);
    }

}
