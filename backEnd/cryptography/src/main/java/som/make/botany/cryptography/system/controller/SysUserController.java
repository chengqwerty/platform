package som.make.botany.cryptography.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import som.make.botany.cryptography.common.beans.ResultBean;
import som.make.botany.cryptography.system.bean.SysUserBean;
import som.make.botany.cryptography.system.service.SysUserService;

@RestController
@RequestMapping("sysUser")
public class SysUserController {

    private SysUserService sysUserService;

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @RequestMapping("querySysUser")
    public ResultBean querySysUser() {
        return new ResultBean<>(sysUserService.querySysUser());
    }

    @RequestMapping("addUser")
    public ResultBean addDepartment(@RequestBody SysUserBean sysUserBean) {
        return new ResultBean<>(sysUserService.addUser(sysUserBean));
    }

    @RequestMapping("updateUser")
    public ResultBean updateUser(@RequestBody SysUserBean sysUserBean) {
        return new ResultBean<>(sysUserService.updateUser(sysUserBean));
    }

    @RequestMapping("deleteUser")
    public ResultBean deleteUser(@RequestBody SysUserBean sysUserBean) {
        return new ResultBean<>(sysUserService.deleteUser(sysUserBean));
    }

}
