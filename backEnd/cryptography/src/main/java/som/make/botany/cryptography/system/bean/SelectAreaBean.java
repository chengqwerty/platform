package som.make.botany.cryptography.system.bean;

import java.util.List;

public class SelectAreaBean {

    private String key;
    private String title;
    private List<SelectAreaBean> children;

    public SelectAreaBean() {

    }

    public SelectAreaBean(String key, String title) {
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

    public List<SelectAreaBean> getChildren() {
        return children;
    }

    public void setChildren(List<SelectAreaBean> children) {
        this.children = children;
    }
}
