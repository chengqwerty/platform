package som.make.botany.cryptography.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import som.make.botany.cryptography.system.bean.SysUserBean;

import java.util.List;

public interface SysUserDao extends JpaRepository<SysUserBean, String> {

    SysUserBean findByLoginName(String loginName);
}
