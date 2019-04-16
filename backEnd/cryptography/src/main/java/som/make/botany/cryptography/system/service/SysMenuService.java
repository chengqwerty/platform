package som.make.botany.cryptography.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import som.make.botany.cryptography.system.bean.SysMenuBean;
import som.make.botany.cryptography.system.dao.SysMenuDao;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class SysMenuService {

    // 菜单编码默认分隔符
    private static final String MENU_SEPARATOR = "-";

    private SysMenuDao sysMenuDao;

    @Autowired
    public void setSysMenuDao(SysMenuDao sysMenuDao) {
        this.sysMenuDao = sysMenuDao;
    }

    public List<SysMenuBean> nextLevelMenu(SysMenuBean sysMenuBean) {
        Sort sort = new Sort(Sort.Direction.ASC, "menuSort");
        List<SysMenuBean> menuList = sysMenuDao.findAllByMenuParentId(sysMenuBean.getMenuId(), sort);
        return menuList;
    }

    public Integer nextMenuSort(SysMenuBean sysMenuBean) {
        SysMenuBean menu = sysMenuDao.findFirstByMenuParentIdOrderByMenuSortDesc(sysMenuBean.getMenuParentId());
        // 默认菜单 sort 序号起始30，间隔30
        Integer sort = 30;
        if (menu!=null) {
            sort = sort + menu.getMenuSort();
        }
        return sort;
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysMenuBean addMenu(SysMenuBean sysMenuBean) {
//        Sort sort = new Sort(Sort.Direction.ASC, "menuLevelCode");
//        String menuParentId = sysMenuBean.getMenuParentId();
//        List<SysMenuBean> sysMenuBeanList = sysMenuDao.findAll(((root, query, criteriaBuilder) -> {
//            if (StringUtils.isEmpty(menuParentId)) {
//                return criteriaBuilder.and(criteriaBuilder.isNull(root.<String>get("menuParentId")));
//            } else {
//                return criteriaBuilder.and(criteriaBuilder.equal(root.<String>get("menuParentId"), menuParentId));
//            }
//        }), sort);
//        // 添加新菜单
//        // 查询上级菜单
        Integer menuLevel; // 菜单级别
        Integer menuLevelId; // 菜单级别 id
        String menuLevelCode; // 菜单级别 code
        String menuParentId = sysMenuBean.getMenuParentId();
        if (StringUtils.isEmpty(menuParentId)) { // 是否有上级菜单
            menuLevel = 1;
            menuLevelId = sysMenuDao.queryMaxLevelIdByParentNull();
            menuLevelId = menuLevelId == null ? 0 : menuLevelId + 1;
            menuLevelCode = menuLevelId.toString();
        } else {
            SysMenuBean parentMenu = sysMenuDao.findById(sysMenuBean.getMenuParentId()).get();
            menuLevel = parentMenu.getMenuLevel() + 1;
            menuLevelId = sysMenuDao.queryMaxLevelIdByParent(sysMenuBean.getMenuParentId());
            menuLevelId = menuLevelId == null ? 0 : menuLevelId + 1;
            menuLevelCode = parentMenu.getMenuLevelCode() + MENU_SEPARATOR + menuLevelId;
        }
        sysMenuBean.setMenuLevel(menuLevel);
        sysMenuBean.setMenuLevelId(menuLevelId);
        sysMenuBean.setMenuLevelCode(menuLevelCode);
        sysMenuBean.setMenuFlag("0");
        sysMenuBean.setCreateTime(Timestamp.from(Instant.now()));
        sysMenuBean.setCreateUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return sysMenuDao.save(sysMenuBean);
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysMenuBean updateMenuVisible(SysMenuBean sysMenuBean) {
        SysMenuBean menu = sysMenuDao.findById(sysMenuBean.getMenuId()).get();
        String visible = menu.getMenuVisible();
        if (visible.equals("0")) {
            menu.setMenuVisible("1");
        } else if (visible.equals("1")) {
            menu.setMenuVisible("0");
        }
        menu.setUpdateTime(Timestamp.from(Instant.now()));
        menu.setUpdateUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return sysMenuDao.save(menu);
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysMenuBean updateMenu(SysMenuBean sysMenuBean) {
        SysMenuBean menu = sysMenuDao.findById(sysMenuBean.getMenuId()).get();
        menu.setMenuName(sysMenuBean.getMenuName());
        menu.setMenuDescription(sysMenuBean.getMenuDescription());
        menu.setMenuViewPath(sysMenuBean.getMenuViewPath());
        menu.setMenuSort(sysMenuBean.getMenuSort());
        menu.setMenuVisible(sysMenuBean.getMenuVisible());
        menu.setMenuIcon(sysMenuBean.getMenuIcon());
        menu.setUpdateTime(Timestamp.from(Instant.now()));
        menu.setUpdateUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return sysMenuDao.save(menu);
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysMenuBean deleteMenu(SysMenuBean sysMenuBean) {
        SysMenuBean menu = sysMenuDao.findById(sysMenuBean.getMenuId()).get();
        sysMenuDao.delete(menu);
        return menu;
    }

}
