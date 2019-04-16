package som.make.botany.cryptography.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import som.make.botany.cryptography.common.beans.ResultBean;
import som.make.botany.cryptography.system.bean.SysMenuBean;
import som.make.botany.cryptography.system.service.SysMenuService;

@RestController
@RequestMapping("sysMenu")
public class SysMenuController {

    private SysMenuService sysMenuService;

    @Autowired
    public void setSysMenuService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @RequestMapping("nextLevelMenu")
    public ResultBean nextLevelMenu(@RequestBody SysMenuBean sysMenuBean) {
        return new ResultBean<>(sysMenuService.nextLevelMenu(sysMenuBean));
    }

    /**
     * 获取下一个菜单的 sort 序号
     * @param sysMenuBean  查询参数menuParentId上级菜单
     * @return 下一个菜单的 sort 序号
     */
    @RequestMapping("nextMenuSort")
    public ResultBean nextMenuSort(@RequestBody SysMenuBean sysMenuBean) {
        return new ResultBean<>(sysMenuService.nextMenuSort(sysMenuBean));
    }

    /**
     * 添加菜单
     * @param sysMenuBean 需要添加的菜单
     * @return 菜单添加信息，包括新添加的菜单 bean
     */
    @RequestMapping("addMenu")
    public ResultBean addMenu(@RequestBody SysMenuBean sysMenuBean) {
        return new ResultBean<>(sysMenuService.addMenu(sysMenuBean));
    }

    /**
     * 更改菜单是否可见的状态，如果不可见改为可见，如果可见改为不可见
     * @param sysMenuBean 要修改的菜单
     * @return 更改的结果信息，包括更改后的菜单 bean
     */
    @RequestMapping("updateMenuVisible")
    public ResultBean updateMenuVisible(@RequestBody SysMenuBean sysMenuBean) {
        return new ResultBean<>(sysMenuService.updateMenuVisible(sysMenuBean));
    }

    /**
     * 修改菜单信息，暂时不能修改菜单的上级菜单
     * @param sysMenuBean 要修改的菜单
     * @return 修改的结果信息，包括修改后的菜单 bean
     */
    @RequestMapping("updateMenu")
    public ResultBean updateMenu(@RequestBody SysMenuBean sysMenuBean) {
        return new ResultBean<>(sysMenuService.updateMenu(sysMenuBean));
    }

    /**
     * 删除菜单
     * @param sysMenuBean 要删除的菜单
     * @return 删除的结果信息，包括删除后的菜单 bean
     */
    @RequestMapping("deleteMenu")
    public ResultBean deleteMenu(@RequestBody SysMenuBean sysMenuBean) {
        return new ResultBean<>(sysMenuService.deleteMenu(sysMenuBean));
    }

}
