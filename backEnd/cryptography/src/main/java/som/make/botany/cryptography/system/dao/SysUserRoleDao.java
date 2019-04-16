package som.make.botany.cryptography.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import som.make.botany.cryptography.system.bean.SysUserRoleBean;

import java.util.List;

public interface SysUserRoleDao extends JpaRepository<SysUserRoleBean, String> {

    Long deleteByUserId(String userId);

    List<SysUserRoleBean> removeByUserId(String userId);

}
