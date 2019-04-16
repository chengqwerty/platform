package som.make.botany.cryptography.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import som.make.botany.cryptography.system.bean.SelectRoleBean;
import som.make.botany.cryptography.system.bean.SysRoleBean;

import java.util.List;

public interface SysRoleDao extends JpaRepository<SysRoleBean, String>, JpaSpecificationExecutor<SysRoleBean> {

    SysRoleBean findFirstByOrderByRoleSortDesc();

    @Query(value = "select new som.make.botany.cryptography.system.bean.SelectRoleBean(roleId, roleName) from SysRoleBean order by roleSort")
    List<SelectRoleBean> queryRole();
}
