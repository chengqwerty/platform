package som.make.botany.cryptography.system.bean;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sys_user", schema = "sys_token", catalog = "postgres")
@Where(clause="user_flag='0'")
@SQLDelete(sql = "update sys_user set user_flag='1' where user_id=?")
public class SysUserBean {
    private String userId;
    private String loginName;
    private String password;
    private String nickname;
    private String userDescription;
    private String departmentId;
    private Integer loginErrorCount;
    private String userStatus;
    private String createUser;
    private Timestamp createTime;
    private String updateUser;
    private Timestamp updateTime;
    private Timestamp lastLoginTime;
    private Timestamp lastLoginIp;
    private String userFlag;

    private List<SysUserRoleBean> sysUserRoleBeanList; // 用户的角色列表

    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "login_name")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "user_description")
    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    @Basic
    @Column(name = "department_id")
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "login_error_count")
    public Integer getLoginErrorCount() {
        return loginErrorCount;
    }

    public void setLoginErrorCount(Integer loginErrorCount) {
        this.loginErrorCount = loginErrorCount;
    }

    @Basic
    @Column(name = "user_status")
    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
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
    @Column(name = "last_login_time")
    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Basic
    @Column(name = "last_login_ip")
    public Timestamp getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(Timestamp lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    @Basic
    @Column(name = "user_flag")
    public String getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    public List<SysUserRoleBean> getSysUserRoleBeanList() {
        return sysUserRoleBeanList;
    }

    public void setSysUserRoleBeanList(List<SysUserRoleBean> sysUserRoleBeanList) {
        this.sysUserRoleBeanList = sysUserRoleBeanList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysUserBean that = (SysUserBean) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(loginName, that.loginName) &&
                Objects.equals(password, that.password) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(userDescription, that.userDescription) &&
                Objects.equals(departmentId, that.departmentId) &&
                Objects.equals(loginErrorCount, that.loginErrorCount) &&
                Objects.equals(userStatus, that.userStatus) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateUser, that.updateUser) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(lastLoginTime, that.lastLoginTime) &&
                Objects.equals(lastLoginIp, that.lastLoginIp) &&
                Objects.equals(userFlag, that.userFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, loginName, password, nickname, userDescription, departmentId, loginErrorCount, userStatus, createUser, createTime, updateUser, updateTime, lastLoginTime, lastLoginIp, userFlag);
    }
}
