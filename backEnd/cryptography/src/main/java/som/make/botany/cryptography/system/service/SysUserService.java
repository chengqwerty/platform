package som.make.botany.cryptography.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import som.make.botany.cryptography.system.bean.SysUserBean;
import som.make.botany.cryptography.system.bean.SysUserRoleBean;
import som.make.botany.cryptography.system.dao.SysUserDao;
import som.make.botany.cryptography.system.dao.SysUserRoleDao;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserService {

    private static final String USER_INIT_PASSWORD = "123456";

    private BCryptPasswordEncoder passwordEncoder;
    private SysUserDao sysUserDao;
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setSysUserDao(SysUserDao sysUserDao) {
        this.sysUserDao = sysUserDao;
    }

    @Autowired
    public void setSysUserRoleDao(SysUserRoleDao sysUserRoleDao) {
        this.sysUserRoleDao = sysUserRoleDao;
    }

    public List<SysUserBean> querySysUser() {
        return sysUserDao.findAll();
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysUserBean addUser(SysUserBean sysUserBean) {
        sysUserBean.setPassword(passwordEncoder.encode(USER_INIT_PASSWORD));
        sysUserBean.setNickname(sysUserBean.getLoginName());
        sysUserBean.setLoginErrorCount(0);
        sysUserBean.setUserStatus("0");
        sysUserBean.setUserFlag("0");
        sysUserBean.setCreateTime(Timestamp.from(Instant.now()));
        sysUserBean.setCreateUser(SecurityContextHolder.getContext().getAuthentication().getName());
        SysUserBean user = sysUserDao.save(sysUserBean);
        List<SysUserRoleBean> sysUserRoleBeanList = sysUserBean.getSysUserRoleBeanList();
        user.setSysUserRoleBeanList(new ArrayList<>());
        for (SysUserRoleBean sysUserRoleBean: sysUserRoleBeanList) {
            sysUserRoleBean.setUserId(user.getUserId());
            user.getSysUserRoleBeanList().add(sysUserRoleDao.save(sysUserRoleBean));
        }
        return user;
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysUserBean updateUser(SysUserBean sysUserBean) {
        SysUserBean user = sysUserDao.findById(sysUserBean.getUserId()).get();
        user.setUserDescription(sysUserBean.getUserDescription());
        user.setDepartmentId(sysUserBean.getDepartmentId());
        user.setUpdateTime(Timestamp.from(Instant.now()));
        user.setUpdateUser(SecurityContextHolder.getContext().getAuthentication().getName());
        user = sysUserDao.save(user);
        String userId = user.getUserId();
        sysUserRoleDao.deleteByUserId(userId);
        sysUserRoleDao.flush();
        List<SysUserRoleBean> oldUserRoleList = sysUserBean.getSysUserRoleBeanList();
        for (SysUserRoleBean sysUserRoleBean: oldUserRoleList) {
            sysUserRoleBean.setUserId(userId);
            sysUserRoleDao.save(sysUserRoleBean);
        }
        return user;
    }

    public SysUserBean deleteUser(SysUserBean sysUserBean) {
        SysUserBean user = sysUserDao.findById(sysUserBean.getUserId()).get();
        sysUserDao.delete(user);
        return user;
    }

}
