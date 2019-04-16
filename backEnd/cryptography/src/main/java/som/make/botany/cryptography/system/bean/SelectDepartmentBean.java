package som.make.botany.cryptography.system.bean;

import java.util.List;

public class SelectDepartmentBean {

    private String key;
    private String title;
    private List<SelectDepartmentBean> children;

    public SelectDepartmentBean() {

    }

    public SelectDepartmentBean(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SelectDepartmentBean> getChildren() {
        return children;
    }

    public void setChildren(List<SelectDepartmentBean> children) {
        this.children = children;
    }

}
