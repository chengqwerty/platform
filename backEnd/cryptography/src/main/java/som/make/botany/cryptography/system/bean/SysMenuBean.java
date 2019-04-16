package som.make.botany.cryptography.system.bean;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sys_menu", schema = "sys_token", catalog = "postgres")
@Where(clause="menu_flag='0'")
@SQLDelete(sql = "update sys_menu set menu_flag='1' where menu_id=?")
public class SysMenuBean {
    private String menuId;
    private String menuName;
    private String menuDescription;
    private Integer menuLevel;
    private Integer menuLevelId;
    private String menuLevelCode;
    private String menuParentId;
    private String menuViewPath;
    private String menuVisible;
    private Integer menuSort;
    private String menuIcon;
    private String createUser;
    private Timestamp createTime;
    private String updateUser;
    private Timestamp updateTime;
    private String menuFlag;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "menu_id")
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Basic
    @Column(name = "menu_name")
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Basic
    @Column(name = "menu_description")
    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    @Basic
    @Column(name = "menu_level")
    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    @Basic
    @Column(name = "menu_level_id")
    public Integer getMenuLevelId() {
        return menuLevelId;
    }

    public void setMenuLevelId(Integer menuLevelId) {
        this.menuLevelId = menuLevelId;
    }

    @Basic
    @Column(name = "menu_level_code")
    public String getMenuLevelCode() {
        return menuLevelCode;
    }

    public void setMenuLevelCode(String menuLevelCode) {
        this.menuLevelCode = menuLevelCode;
    }

    @Basic
    @Column(name = "menu_parent_id")
    public String getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(String menuParentId) {
        this.menuParentId = menuParentId;
    }

    @Basic
    @Column(name = "menu_view_path")
    public String getMenuViewPath() {
        return menuViewPath;
    }

    public void setMenuViewPath(String menuViewPath) {
        this.menuViewPath = menuViewPath;
    }

    @Basic
    @Column(name = "menu_visible")
    public String getMenuVisible() {
        return menuVisible;
    }

    public void setMenuVisible(String menuVisible) {
        this.menuVisible = menuVisible;
    }

    @Basic
    @Column(name = "menu_sort")
    public Integer getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(Integer menuSort) {
        this.menuSort = menuSort;
    }

    @Basic
    @Column(name = "menu_icon")
    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    @Basic
    @Column(name = "create_user")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_user")
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "menu_flag")
    public String getMenuFlag() {
        return menuFlag;
    }

    public void setMenuFlag(String menuFlag) {
        this.menuFlag = menuFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysMenuBean that = (SysMenuBean) o;
        return Objects.equals(menuId, that.menuId) &&
                Objects.equals(menuName, that.menuName) &&
                Objects.equals(menuDescription, that.menuDescription) &&
                Objects.equals(menuLevel, that.menuLevel) &&
                Objects.equals(menuLevelId, that.menuLevelId) &&
                Objects.equals(menuLevelCode, that.menuLevelCode) &&
                Objects.equals(menuParentId, that.menuParentId) &&
                Objects.equals(menuViewPath, that.menuViewPath) &&
                Objects.equals(menuVisible, that.menuVisible) &&
                Objects.equals(menuSort, that.menuSort) &&
                Objects.equals(menuIcon, that.menuIcon) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateUser, that.updateUser) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(menuFlag, that.menuFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, menuName, menuDescription, menuLevel, menuLevelId, menuLevelCode, menuParentId, menuViewPath, menuVisible, menuSort, menuIcon, createUser, createTime, updateUser, updateTime, menuFlag);
    }

}
