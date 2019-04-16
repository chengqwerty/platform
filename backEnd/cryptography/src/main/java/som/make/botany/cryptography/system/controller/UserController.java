package som.make.botany.cryptography.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import som.make.botany.cryptography.common.beans.ResultBean;
import som.make.botany.cryptography.system.bean.SysMenuBean;
import som.make.botany.cryptography.system.bean.SysUserBean;
import som.make.botany.cryptography.system.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登录后初始化各种数据，菜单等
     * @return 初始化数据
     */
    @RequestMapping("loginInit")
    public ResultBean loginInit() {
        return new ResultBean<>(userService.loginInit());
    }

    @RequestMapping("sideMenu")
    public ResultBean sideMenu(@RequestBody SysMenuBean sysMenuBean) {
        return new ResultBean<>(userService.sideMenu(sysMenuBean));
    }

}
