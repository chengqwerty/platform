package som.make.botany.cryptography.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import som.make.botany.cryptography.common.beans.ResultBean;
import som.make.botany.cryptography.system.bean.SysRoleBean;
import som.make.botany.cryptography.system.service.SysRoleService;

@RestController
@RequestMapping("sysRole")
public class SysRoleController {

    private SysRoleService sysRoleService;

    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @RequestMapping("")
    public String sysRole() {
        return "This is sysRole!";
    }

    @RequestMapping("querySysRole")
    public ResultBean querySysRole(@RequestBody SysRoleBean sysRoleBean) {
        return new ResultBean<>(sysRoleService.querySysRole(sysRoleBean));
    }

    /**
     * 获取下一个角色的 sort 序号
     * @return 下一个角色的 sort 序号
     */
    @RequestMapping("nextRoleSort")
    public ResultBean nextRoleSort() {
        return new ResultBean<>(sysRoleService.nextRoleSort());
    }

    /**
     * 添加角色
     * @param sysRoleBean 需要添加的角色
     * @return 添加结果，包括添加后的角色
     */
    @RequestMapping("addRole")
    public ResultBean addRole(@RequestBody SysRoleBean sysRoleBean) {
        return new ResultBean<>(sysRoleService.addRole(sysRoleBean));
    }

    /**
     * 更改角色是否可用的状态，如果不可用改为可用，如果可用改为不可用
     * @param sysRoleBean 需要修改的角色
     * @return 修改结果，包括修改后的角色
     */
    @RequestMapping("updateRoleUsing")
    public ResultBean updateRoleUsing(@RequestBody SysRoleBean sysRoleBean) {
        return new ResultBean<>(sysRoleService.updateRoleUsing(sysRoleBean));
    }

    /**
     * 修改角色信息
     * @param sysRoleBean 要修改的角色信息
     * @return 修改结果，包括修改后的角色
     */
    @RequestMapping("updateRole")
    public ResultBean updateRole(@RequestBody SysRoleBean sysRoleBean) {
        return new ResultBean<>(sysRoleService.updateRole(sysRoleBean));
    }

    /**
     * 删除角色信息
     * @param sysRoleBean 要删除的角色信息
     * @return 删除结果，包括删除后的角色
     */
    @RequestMapping("deleteRole")
    public ResultBean deleteRole(@RequestBody SysRoleBean sysRoleBean) {
        return new ResultBean<>(sysRoleService.deleteRole(sysRoleBean));
    }

    /**
     * 获取全部的角色，用于前端 tree select
     * @return 查询到的角色
     */
    @RequestMapping("getSelectRole")
    public ResultBean getSelectRole() {
        return new ResultBean<>(sysRoleService.getSelectRole());
    }

    @RequestMapping("getPipeRole")
    public ResultBean getPipeRole() {
        return new ResultBean<>(sysRoleService.getPipeRole());
    }

}
