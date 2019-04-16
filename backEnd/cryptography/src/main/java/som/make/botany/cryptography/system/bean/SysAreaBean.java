package som.make.botany.cryptography.system.bean;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sys_area", schema = "sys_token", catalog = "postgres")
@Where(clause="area_flag='0'")
@SQLDelete(sql = "update sys_area set area_flag='1' where area_id=?")
public class SysAreaBean {
    private String areaId;
    private String areaName;
    private String areaDescription;
    private String areaCode;
    private Integer areaLevel;
    private Integer areaLevelId;
    private String areaLevelCode;
    private String areaParentId;
    private Integer areaSort;
    private String createUser;
    private Timestamp createTime;
    private String updateUser;
    private Timestamp updateTime;
    private String areaFlag;

    @Id
    @Column(name = "area_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Basic
    @Column(name = "area_name")
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Basic
    @Column(name = "area_description")
    public String getAreaDescription() {
        return areaDescription;
    }

    public void setAreaDescription(String areaDescription) {
        this.areaDescription = areaDescription;
    }

    @Basic
    @Column(name = "area_code")
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Basic
    @Column(name = "area_level")
    public Integer getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }

    @Basic
    @Column(name = "area_level_id")
    public Integer getAreaLevelId() {
        return areaLevelId;
    }

    public void setAreaLevelId(Integer areaLevelId) {
        this.areaLevelId = areaLevelId;
    }

    @Basic
    @Column(name = "area_level_code")
    public String getAreaLevelCode() {
        return areaLevelCode;
    }

    public void setAreaLevelCode(String areaLevelCode) {
        this.areaLevelCode = areaLevelCode;
    }

    @Basic
    @Column(name = "area_parent_id")
    public String getAreaParentId() {
        return areaParentId;
    }

    public void setAreaParentId(String areaParentId) {
        this.areaParentId = areaParentId;
    }

    @Basic
    @Column(name = "area_sort")
    public Integer getAreaSort() {
        return areaSort;
    }

    public void setAreaSort(Integer areaSort) {
        this.areaSort = areaSort;
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
    @Column(name = "area_flag")
    public String getAreaFlag() {
        return areaFlag;
    }

    public void setAreaFlag(String areaFlag) {
        this.areaFlag = areaFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysAreaBean that = (SysAreaBean) o;
        return Objects.equals(areaId, that.areaId) &&
                Objects.equals(areaName, that.areaName) &&
                Objects.equals(areaDescription, that.areaDescription) &&
                Objects.equals(areaCode, that.areaCode) &&
                Objects.equals(areaLevel, that.areaLevel) &&
                Objects.equals(areaLevelId, that.areaLevelId) &&
                Objects.equals(areaLevelCode, that.areaLevelCode) &&
                Objects.equals(areaParentId, that.areaParentId) &&
                Objects.equals(areaSort, that.areaSort) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateUser, that.updateUser) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(areaFlag, that.areaFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaId, areaName, areaDescription, areaCode, areaLevel, areaLevelId, areaLevelCode, areaParentId, areaSort, createUser, createTime, updateUser, updateTime, areaFlag);
    }
}
