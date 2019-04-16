package som.make.botany.cryptography.system.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import som.make.botany.cryptography.system.bean.SelectDepartmentBean;
import som.make.botany.cryptography.system.bean.SysDepartmentBean;

import java.util.List;

public interface SysDepartmentDao extends JpaRepository<SysDepartmentBean, String>, JpaSpecificationExecutor<SysDepartmentBean> {

    List<SysDepartmentBean> findAllByDepartmentParentId(String departmentParentId, Sort sort);

    SysDepartmentBean findFirstByDepartmentParentIdOrderByDepartmentSortDesc(String departmentParentId);

    /**
     * 因为实体类有 @Where，这个方法比较特殊。
     * 即便 department 被逻辑删除了 department_level_code 也不允许重复，所以必须查询所有的 department，不管是否删除。
     * 目前只想到了原生sql这一种方法
     */
    @Query(value = "select max(department_level_id) from sys_department where department_parent_id = :departmentParentId", nativeQuery = true)
    Integer queryMaxLevelIdByParent(@Param("departmentParentId") String departmentParentId);

    /**
     * @see SysDepartmentDao#queryMaxLevelIdByParent 的注释
     */
    @Query(value = "select max(department_level_id) from sys_department where department_parent_id is null", nativeQuery = true)
    Integer queryMaxLevelIdByParentNull();

    @Query(value = "select new som.make.botany.cryptography.system.bean.SelectDepartmentBean(departmentId, departmentName) from SysDepartmentBean where departmentParentId is null order by departmentSort")
    List<SelectDepartmentBean> queryDepartmentWhenParentNull();

    @Query(value = "select new som.make.botany.cryptography.system.bean.SelectDepartmentBean(departmentId, departmentName) from SysDepartmentBean where departmentParentId= :departmentParentId order by departmentSort")
    List<SelectDepartmentBean> queryDepartmentWhenParent(@Param("departmentParentId") String departmentParentId);

    @Query(value = "select new som.make.botany.cryptography.system.bean.SelectDepartmentBean(departmentId, departmentName) from SysDepartmentBean")
    List<SelectDepartmentBean> queryDepartmentPipe();

}
