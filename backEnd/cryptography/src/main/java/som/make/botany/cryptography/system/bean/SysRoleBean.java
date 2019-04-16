package som.make.botany.cryptography.system.bean;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import som.make.botany.cryptography.common.beans.PageBean;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sys_role", schema = "sys_token", catalog = "postgres")
@Where(clause="role_flag='0'")
@SQLDelete(sql = "update sys_role set role_flag='1' where role_id=?")
public class SysRoleBean extends PageBean {
    private String roleId;
    private String roleName;
    private String roleDescription;
    private String roleUsing;
    private Integer roleSort;
    private String createUser;
    private Timestamp createTime;
    private String updateUser;
    private Timestamp updateTime;
    private String roleFlag;

    @Id
    @Column(name = "role_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "role_description")
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    @Basic
    @Column(name = "role_using")
    public String getRoleUsing() {
        return roleUsing;
    }

    public void setRoleUsing(String roleUsing) {
        this.roleUsing = roleUsing;
    }

    @Basic
    @Column(name = "role_sort")
    public Integer getRoleSort() {
        return roleSort;
    }

    public void setRoleSort(Integer roleSort) {
        this.roleSort = roleSort;
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
    @Column(name = "role_flag")
    public String getRoleFlag() {
        return roleFlag;
    }

    public void setRoleFlag(String roleFlag) {
        this.roleFlag = roleFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRoleBean that = (SysRoleBean) o;
        return Objects.equals(roleId, that.roleId) &&
                Objects.equals(roleName, that.roleName) &&
                Objects.equals(roleDescription, that.roleDescription) &&
                Objects.equals(roleUsing, that.roleUsing) &&
                Objects.equals(roleSort, that.roleSort) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateUser, that.updateUser) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(roleFlag, that.roleFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName, roleDescription, roleUsing, roleSort, createUser, createTime, updateUser, updateTime, roleFlag);
    }
}
