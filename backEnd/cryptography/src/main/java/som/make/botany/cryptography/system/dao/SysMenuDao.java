package som.make.botany.cryptography.system.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import som.make.botany.cryptography.system.bean.AppMenu;
import som.make.botany.cryptography.system.bean.SysMenuBean;

import java.util.List;

public interface SysMenuDao extends JpaRepository<SysMenuBean, String>, JpaSpecificationExecutor<SysMenuBean> {

    List<SysMenuBean> findAllByMenuParentId(String menuParentId, Sort sort);

    SysMenuBean findFirstByMenuParentIdOrderByMenuSortDesc(String menuParentId);

    /**
     * 因为实体类有 @Where，这个方法比较特殊。
     * 即便 menu 被逻辑删除了 menu_level_code 也不允许重复，所以必须查询所有的 menu，不管是否删除。
     * 目前只想到了原生sql这一种方法
     */
    @Query(value = "select max(menu_level_id) from sys_menu where menu_parent_id = :menuParentId", nativeQuery = true)
    Integer queryMaxLevelIdByParent(@Param("menuParentId") String menuParentId);

    /**
     * @see SysMenuDao#queryMaxLevelIdByParent 的注释
     */
    @Query(value = "select max(menu_level_id) from sys_menu where menu_parent_id is null", nativeQuery = true)
    Integer queryMaxLevelIdByParentNull();

    @Query(value = "select new som.make.botany.cryptography.system.bean.AppMenu(menuId, menuName, menuViewPath, menuIcon, menuVisible) from SysMenuBean where menuLevel = 1 and menuVisible='0' order by menuSort")
    List<AppMenu> queryHeaderMenu();

    @Query(value = "select new som.make.botany.cryptography.system.bean.AppMenu(menuId, menuName, menuViewPath, menuIcon, menuVisible) from SysMenuBean where menuParentId = :menuParentId and menuVisible='0' order by menuSort")
    List<AppMenu> querySideMenu(@Param("menuParentId") String menuParentId);

}
