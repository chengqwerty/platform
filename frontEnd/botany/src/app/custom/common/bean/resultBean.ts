export interface ResultBean<T> {
    message: string;
    code: number;
    data: T;
}
