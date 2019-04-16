package som.make.botany.cryptography.system.bean;

import java.util.List;

/**
 * 前端使用的 menu bean
 */
public class AppMenu {

    private String text;
    private String link;
    private String icon;
    private Boolean hide;

    private String menuId;
    private String visible;
    private List<AppMenu> children;

    public AppMenu() {

    }

    public AppMenu(String menuId, String text, String link, String icon, String visible) {
        this.menuId = menuId;
        this.text = text;
        this.link = link;
        this.icon = icon;
        this.visible = visible;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public List<AppMenu> getChildren() {
        return children;
    }

    public void setChildren(List<AppMenu> children) {
        this.children = children;
    }
}
