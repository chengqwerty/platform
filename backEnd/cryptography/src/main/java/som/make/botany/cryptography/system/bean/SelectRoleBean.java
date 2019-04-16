package som.make.botany.cryptography.system.bean;

public class SelectRoleBean {

    private String key;
    private String title;

    public SelectRoleBean() {

    }

    public SelectRoleBean(String key, String title) {
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

}
