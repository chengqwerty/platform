package som.make.botany.cryptography.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import som.make.botany.cryptography.common.beans.AppBean;
import som.make.botany.cryptography.system.bean.AppMenu;
import som.make.botany.cryptography.system.bean.SysMenuBean;
import som.make.botany.cryptography.system.bean.SysUserBean;
import som.make.botany.cryptography.system.dao.SysMenuDao;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Selection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private AppBean appBean;
    private SysMenuDao sysMenuDao;

    @Autowired
    public void setAppBean(AppBean appBean) {
        this.appBean = appBean;
    }

    @Autowired
    public void setSysMenuDao(SysMenuDao sysMenuDao) {
        this.sysMenuDao = sysMenuDao;
    }

    public Map loginInit() {
        Map<String, Object> map = new HashMap<>();
        map.put("app", appBean);
        SysUserBean sysUserBean = new SysUserBean();
        map.put("user", sysUserBean);
        List<AppMenu> appMenus = sysMenuDao.queryHeaderMenu();
        map.put("menu", appMenus);
        return map;
    }

    public List<AppMenu> sideMenu(SysMenuBean sysMenuBean) {
        return circleSideMenu(sysMenuBean.getMenuId());
    }

    private List<AppMenu> circleSideMenu(String menuParentId) {
        List<AppMenu> appMenus = sysMenuDao.querySideMenu(menuParentId);
        for (AppMenu appMenu: appMenus) {
            List<AppMenu> children = circleSideMenu(appMenu.getMenuId());
            if (children.size() > 0) {
                appMenu.setChildren(children);
            }
        }
        return appMenus;
    }

}
