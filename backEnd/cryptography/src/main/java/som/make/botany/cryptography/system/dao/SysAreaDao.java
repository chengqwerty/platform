package som.make.botany.cryptography.system.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import som.make.botany.cryptography.system.bean.SelectAreaBean;
import som.make.botany.cryptography.system.bean.SysAreaBean;

import java.util.List;

public interface SysAreaDao extends JpaRepository<SysAreaBean, String>, JpaSpecificationExecutor<SysAreaBean> {

    List<SysAreaBean> findAllByAreaParentId(String areaParentId, Sort sort);

    SysAreaBean findFirstByAreaParentIdOrderByAreaSortDesc(String areaParentId);

    /**
     * 因为实体类有 @Where，这个方法比较特殊。
     * 即便 area 被逻辑删除了 area_level_code 也不允许重复，所以必须查询所有的 area，不管是否删除。
     * 目前只想到了原生sql这一种方法
     */
    @Query(value = "select max(area_level_id) from sys_area where area_parent_id = :areaParentId", nativeQuery = true)
    Integer queryMaxLevelIdByParent(@Param("areaParentId") String areaParentId);

    /**
     * @see SysAreaDao#queryMaxLevelIdByParent 的注释
     */
    @Query(value = "select max(area_level_id) from sys_area where area_parent_id is null", nativeQuery = true)
    Integer queryMaxLevelIdByParentNull();

    @Query(value = "select new som.make.botany.cryptography.system.bean.SelectAreaBean(areaId, areaName) from SysAreaBean where areaParentId is null order by areaSort")
    List<SelectAreaBean> queryAreaWhenParentNull();

    @Query(value = "select new som.make.botany.cryptography.system.bean.SelectAreaBean(areaId, areaName) from SysAreaBean where areaParentId= :areaParentId order by areaSort")
    List<SelectAreaBean> queryAreaWhenParent(@Param("areaParentId") String areaParentId);

}
