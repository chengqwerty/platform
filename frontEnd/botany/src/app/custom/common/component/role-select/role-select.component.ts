import { Component, forwardRef, OnInit } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { RoleService } from '../../service/role.service';

@Component({
    selector: 'role-select',
    providers: [
        {
            provide    : NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => RoleSelectComponent),
            multi      : true
        }
    ],
    template: `
        <nz-select style="width: 100%" nzMode="multiple" nzPlaceHolder="请选择角色，可多选" 
                   [(ngModel)]="value" (ngModelChange)="onModelChange($event)">
            <nz-option *ngFor="let option of listOfOption" [nzLabel]="option.title" [nzValue]="option.key"></nz-option>
        </nz-select>
    `
})
export class RoleSelectComponent implements ControlValueAccessor, OnInit {

    public value: string[];
    public listOfOption: {key: string, title: string}[] = [];

    private onChange: (value: string) => void;

    constructor(private roleService: RoleService) { }

    ngOnInit(): void {
        this.roleService.getRoleData().subscribe(response => {
            this.listOfOption = response.data;
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
