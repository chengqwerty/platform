import { Component, Input, OnInit } from '@angular/core';

const categories = {
    direction : [
        'step-backward', 'step-forward', 'fast-backward',
        'fast-forward', 'shrink', 'arrows-alt', 'down', 'up', 'left',
        'right', 'caret-up', 'caret-down', 'caret-left', 'caret-right',
        'up-circle', 'down-circle', 'left-circle', 'right-circle',
        'double-right', 'double-left', 'vertical-left', 'vertical-right',
        'forward', 'backward', 'rollback', 'enter', 'retweet',
        'swap', 'swap-left', 'swap-right', 'arrow-up', 'arrow-down',
        'arrow-left', 'arrow-right', 'play-circle',
        'up-square', 'down-square', 'left-square', 'right-square',
        'login', 'logout', 'menu-fold', 'menu-unfold',
        'border-bottom', 'border-horizontal', 'border-inner',
        'border-left', 'border-right', 'border-top',
        'border-verticle', 'pic-center', 'pic-left', 'pic-right',
        'radius-bottomleft', 'radius-bottomright', 'radius-upleft',
        'fullscreen', 'fullscreen-exit'
    ],
    suggestion: [
        'question', 'question-circle',
        'plus', 'plus-circle', 'pause',
        'pause-circle', 'minus',
        'minus-circle', 'plus-square', 'minus-square',
        'info', 'info-circle',
        'exclamation', 'exclamation-circle',
        'close', 'close-circle', 'close-square',
        'check', 'check-circle',
        'check-square',
        'clock-circle', 'warning',
        'issues-close', 'stop'
    ],
    edit      : [
        'edit', 'form', 'copy', 'scissor', 'delete', 'snippets', 'diff', 'highlight',
        'align-center', 'align-left', 'align-right', 'bg-colors',
        'bold', 'italic', 'underline',
        'strikethrough', 'redo', 'undo', 'zoom-in', 'zoom-out',
        'font-colors', 'font-size', 'line-height', 'colum-height',
        'dash', 'small-dash', 'sort-ascending', 'sort-descending',
        'drag', 'ordered-list', 'radius-setting'
    ],
    data      : [
        'area-chart', 'pie-chart', 'bar-chart', 'dot-chart', 'line-chart',
        'radar-chart', 'heat-map', 'fall', 'rise', 'stock', 'box-plot', 'fund',
        'sliders'
    ],
    other     : [
        'lock', 'unlock', 'bars', 'book', 'calendar', 'cloud', 'cloud-download',
        'code', 'copy', 'credit-card', 'delete', 'desktop',
        'download', 'ellipsis', 'file', 'file-text',
        'file-unknown', 'file-pdf', 'file-word', 'file-excel',
        'file-jpg', 'file-ppt', 'file-markdown', 'file-add',
        'folder', 'folder-open', 'folder-add', 'hdd', 'frown',
        'meh', 'smile', 'inbox',
        'laptop', 'appstore', 'link',
        'mail', 'mobile', 'notification', 'paper-clip', 'picture',
        'poweroff', 'reload', 'search', 'setting', 'share-alt',
        'shopping-cart', 'tablet', 'tag', 'tags',
        'to-top', 'upload', 'user', 'video-camera',
        'home', 'loading', 'loading-3-quarters',
        'cloud-upload',
        'star', 'heart', 'environment',
        'eye', 'camera', 'save', 'team',
        'solution', 'phone', 'filter', 'exception', 'export',
        'customer-service', 'qrcode', 'scan', 'like',
        'dislike', 'message', 'pay-circle',
        'calculator', 'pushpin',
        'bulb', 'select', 'switcher', 'rocket', 'bell', 'disconnect',
        'database', 'compass', 'barcode', 'hourglass', 'key',
        'flag', 'layout', 'printer', 'sound', 'usb', 'skin', 'tool',
        'sync', 'wifi', 'car', 'schedule', 'user-add', 'user-delete',
        'usergroup-add', 'usergroup-delete', 'man', 'woman', 'shop',
        'gift', 'idcard', 'medicine-box', 'red-envelope', 'coffee',
        'copyright', 'trademark', 'safety', 'wallet', 'bank', 'trophy',
        'contacts', 'global', 'shake', 'api', 'fork', 'dashboard',
        'table', 'profile',
        'alert', 'audit', 'branches',
        'build', 'border', 'crown',
        'experiment', 'fire',
        'money-collect', 'property-safety', 'read', 'reconciliation',
        'rest', 'security-scan', 'insurance', 'interation', 'safety-certificate',
        'project', 'thunderbolt', 'block', 'cluster', 'deployment-unit',
        'dollar', 'euro', 'pound', 'file-done', 'file-exclamation', 'file-protect',
        'file-search', 'file-sync', 'gateway', 'gold', 'robot', 'shopping'
    ],
    logo      : [
        'android', 'apple', 'windows',
        'ie', 'chrome', 'github', 'aliwangwang',
        'dingding',
        'weibo-square', 'weibo-circle', 'taobao-circle', 'html5',
        'weibo', 'twitter', 'wechat', 'youtube', 'alipay-circle',
        'taobao', 'skype', 'qq', 'medium-workmark', 'gitlab', 'medium',
        'linkedin', 'google-plus', 'dropbox', 'facebook', 'codepen',
        'amazon', 'google', 'codepen-circle', 'alipay', 'ant-design',
        'aliyun', 'zhihu', 'slack', 'slack-square', 'behance',
        'behance-square', 'dribbble', 'dribbble-square',
        'instagram', 'yuque',
        'alibaba', 'yahoo'
    ]
};

@Component({
    selector: 'icon-model',
    templateUrl: './icon.component.html',
    styleUrls: [ './icon.component.less' ]
})
export class IconComponent implements OnInit {

    @Input()
    public selectedIcon: string;
    public displayedNames = {};
    public categoryNames = [];
    public currentTheme = 'outline';
    public localeObj = {
        chooseTheme: '选择图标主题风格',
        direction: '方向性图标',
        suggestion: '提示建议性图标',
        edit: '编辑类图标',
        data: '数据类图标',
        other: '网站通用图标',
        logo: '品牌和标识'
    };

    constructor() {

    }

    ngOnInit(): void {
        this.setIconsShouldBeDisplayed('outline');
    }

    setIconsShouldBeDisplayed(value: string): void {
        // tslint:disable
        const names = Object.keys(categories)
            .map(category => ({
                name : category,
                icons: categories[ category ]
            }));
        this.displayedNames = names;
        this.categoryNames = names.map(({ name }) => name);
        this.currentTheme = value;
    }

    trackByFn = (index: number, item: string) => {
        return `${item}-${this.currentTheme}`;
    }

    onIconClick(e: MouseEvent, icon: string): void {
        this.selectedIcon = icon;
    }

}
