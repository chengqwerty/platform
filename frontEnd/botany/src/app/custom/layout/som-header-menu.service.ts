import { Injectable } from '@angular/core';

import { BehaviorSubject, Observable } from 'rxjs';
import { share } from 'rxjs/operators';
import { Menu, MenuService } from '@delon/theme';

@Injectable({
    providedIn: 'root'
})
export class SomHeaderMenuService {

    private menus: Menu[];
    private _mainMenusChange$: BehaviorSubject<Menu[]> = new BehaviorSubject([]);

    constructor(private menuService: MenuService) {

    }

    addMainMenus(menus: Menu[]) {
        this.menus = menus;
        this._mainMenusChange$.next(this.menus);
    }

    get mainMenusChange(): Observable<Menu[]> {
        return this._mainMenusChange$.pipe(share());
    }

}
