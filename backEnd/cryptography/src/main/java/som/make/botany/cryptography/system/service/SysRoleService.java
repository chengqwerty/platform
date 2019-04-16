package som.make.botany.cryptography.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import som.make.botany.cryptography.system.bean.SelectRoleBean;
import som.make.botany.cryptography.system.bean.SysRoleBean;
import som.make.botany.cryptography.system.dao.SysRoleDao;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysRoleService {

    private SysRoleDao sysRoleDao;

    @Autowired
    public void setSysRoleDao(SysRoleDao sysRoleDao) {
        this.sysRoleDao = sysRoleDao;
    }

    public Page<SysRoleBean> querySysRole(SysRoleBean sysRoleBean) {
        PageRequest pageRequest = PageRequest.of(sysRoleBean.getQueryPage(), sysRoleBean.getSize(), new Sort(Sort.Direction.ASC, "roleSort"));
        return sysRoleDao.findAll(pageRequest);
    }

    /**
     * 返回下一个 role sort，默认间隔30
     * @return 下一个 role sort
     */
    public Integer nextRoleSort() {
        Integer sort;
        SysRoleBean sysRoleBean = sysRoleDao.findFirstByOrderByRoleSortDesc();
        if (sysRoleBean == null) {
            sort = 30;
        } else {
            sort = sysRoleBean.getRoleSort() + 30;
        }
        return sort;
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysRoleBean addRole(SysRoleBean sysRoleBean) {
        sysRoleBean.setRoleFlag("0");
        sysRoleBean.setCreateTime(Timestamp.from(Instant.now()));
        sysRoleBean.setCreateUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return sysRoleDao.save(sysRoleBean);
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysRoleBean updateRoleUsing(SysRoleBean sysRoleBean) {
        SysRoleBean role = sysRoleDao.findById(sysRoleBean.getRoleId()).get();
        String using = role.getRoleUsing();
        if (using.equals("0")) {
            role.setRoleUsing("1");
        } else if (using.equals("1")){
            role.setRoleUsing("0");
        }
        return sysRoleDao.save(role);
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysRoleBean updateRole(SysRoleBean sysRoleBean) {
        SysRoleBean role = sysRoleDao.findById(sysRoleBean.getRoleId()).get();
        role.setRoleName(sysRoleBean.getRoleName());
        role.setRoleDescription(sysRoleBean.getRoleDescription());
        role.setRoleSort(sysRoleBean.getRoleSort());
        role.setRoleUsing(sysRoleBean.getRoleUsing());
        return sysRoleDao.save(role);
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysRoleBean deleteRole(SysRoleBean sysRoleBean) {
        SysRoleBean role = sysRoleDao.findById(sysRoleBean.getRoleId()).get();
        sysRoleDao.delete(role);
        return role;
    }

    public List<SelectRoleBean> getSelectRole() {
        return sysRoleDao.queryRole();
    }

    public Map<String, String> getPipeRole() {
        List<SelectRoleBean> rootList = sysRoleDao.queryRole();
        return rootList.stream().collect(Collectors.toMap(SelectRoleBean::getKey, SelectRoleBean::getTitle, (k1, k2) -> k1));
    }

}
