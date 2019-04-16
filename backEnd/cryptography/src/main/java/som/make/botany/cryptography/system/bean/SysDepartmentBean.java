package som.make.botany.cryptography.system.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sys_department", schema = "sys_token", catalog = "postgres")
@Where(clause="department_flag='0'")
@SQLDelete(sql = "update sys_department set department_flag='1' where department_id=?")
public class SysDepartmentBean {
    private String departmentId;
    private String departmentName;
    private String departmentDescription;
    private String departmentCode;
    private Integer departmentLevel;
    private Integer departmentLevelId;
    private String departmentLevelCode;
    private String departmentParentId;
    private Integer departmentSort;
    private String areaId;
    private String createUser;
    private Timestamp createTime;
    private String updateUser;
    private Timestamp updateTime;
    private String departmentFlag;

    @JsonIgnoreProperties(value={"hibernateLazyInitializer"})
    private SysAreaBean sysAreaBean;

    @Id
    @Column(name = "department_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "department_name")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Basic
    @Column(name = "department_description")
    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
    }

    @Basic
    @Column(name = "department_code")
    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    @Basic
    @Column(name = "department_level")
    public Integer getDepartmentLevel() {
        return departmentLevel;
    }

    public void setDepartmentLevel(Integer departmentLevel) {
        this.departmentLevel = departmentLevel;
    }

    @Basic
    @Column(name = "department_level_id")
    public Integer getDepartmentLevelId() {
        return departmentLevelId;
    }

    public void setDepartmentLevelId(Integer departmentLevelId) {
        this.departmentLevelId = departmentLevelId;
    }

    @Basic
    @Column(name = "department_level_code")
    public String getDepartmentLevelCode() {
        return departmentLevelCode;
    }

    public void setDepartmentLevelCode(String departmentLevelCode) {
        this.departmentLevelCode = departmentLevelCode;
    }

    @Basic
    @Column(name = "department_parent_id")
    public String getDepartmentParentId() {
        return departmentParentId;
    }

    public void setDepartmentParentId(String departmentParentId) {
        this.departmentParentId = departmentParentId;
    }

    @Basic
    @Column(name = "department_sort")
    public Integer getDepartmentSort() {
        return departmentSort;
    }

    public void setDepartmentSort(Integer departmentSort) {
        this.departmentSort = departmentSort;
    }

    @Basic
    @Column(name = "area_id")
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
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
    @Column(name = "department_flag")
    public String getDepartmentFlag() {
        return departmentFlag;
    }

    public void setDepartmentFlag(String departmentFlag) {
        this.departmentFlag = departmentFlag;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", referencedColumnName = "area_id", insertable = false, updatable = false)
    public SysAreaBean getSysAreaBean() {
        return sysAreaBean;
    }

    public void setSysAreaBean(SysAreaBean sysAreaBean) {
        this.sysAreaBean = sysAreaBean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysDepartmentBean that = (SysDepartmentBean) o;
        return Objects.equals(departmentId, that.departmentId) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(departmentDescription, that.departmentDescription) &&
                Objects.equals(departmentCode, that.departmentCode) &&
                Objects.equals(departmentLevel, that.departmentLevel) &&
                Objects.equals(departmentLevelId, that.departmentLevelId) &&
                Objects.equals(departmentLevelCode, that.departmentLevelCode) &&
                Objects.equals(departmentParentId, that.departmentParentId) &&
                Objects.equals(departmentSort, that.departmentSort) &&
                Objects.equals(areaId, that.areaId) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateUser, that.updateUser) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(departmentFlag, that.departmentFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, departmentName, departmentDescription, departmentCode, departmentLevel, departmentLevelId, departmentLevelCode, departmentParentId, departmentSort, areaId, createUser, createTime, updateUser, updateTime, departmentFlag);
    }
}
