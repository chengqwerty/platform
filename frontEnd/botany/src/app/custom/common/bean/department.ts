export interface DepartmentData {
    key: string;
    title: string;
    children: DepartmentData;
}

export interface DepartmentPipeData {
    [index: string]: string;
}
