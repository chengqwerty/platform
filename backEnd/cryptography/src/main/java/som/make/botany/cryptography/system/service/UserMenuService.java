package som.make.botany.cryptography.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import som.make.botany.cryptography.system.bean.AppMenu;
import som.make.botany.cryptography.system.dao.SysMenuDao;

import java.util.List;

@Service
public class UserMenuService {

    private SysMenuDao sysMenuDao;

    @Autowired
    public void setSysMenuDao(SysMenuDao sysMenuDao) {
        this.sysMenuDao = sysMenuDao;
    }

    public List<AppMenu> headerMenu() {
        return sysMenuDao.queryHeaderMenu();
    }

}
