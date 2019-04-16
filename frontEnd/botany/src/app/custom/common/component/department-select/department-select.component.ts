import { Component, forwardRef, OnInit } from '@angular/core';
import { NzTreeNode } from 'ng-zorro-antd';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { DepartmentService } from '../../service/department.service';

@Component({
    selector: 'department-select',
    providers: [
        {
            provide    : NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => DepartmentSelectComponent),
            multi      : true
        }
    ],
    template: `
        <nz-tree-select
            nzPlaceHolder="请选择机构"
            [nzDropdownMatchSelectWidth]="true"
            [nzDropdownStyle]="{ 'max-height': '300px' }"
            [(ngModel)]="value"
            (ngModelChange)="onModelChange($event)"
            [nzNodes]="nodes">
        </nz-tree-select>
    `
})
export class DepartmentSelectComponent implements ControlValueAccessor, OnInit {

    public value: string;
    public nodes: NzTreeNode[] = [];

    private onChange: (value: string) => void;

    constructor(private departmentService: DepartmentService) {

    }

    ngOnInit(): void {
        this.departmentService.getDepartmentData().subscribe(response => {
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
