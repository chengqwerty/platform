package som.make.botany.cryptography.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import som.make.botany.cryptography.common.beans.ResultBean;
import som.make.botany.cryptography.system.service.UserMenuService;

/**
 * 根据登录的用户获取相应的菜单
 */
@RestController
@RequestMapping("userMenu")
public class UserMenuController {

    private UserMenuService userMenuService;

    @Autowired
    public void setUserMenuService(UserMenuService userMenuService) {
        this.userMenuService = userMenuService;
    }

    @RequestMapping("headerMenu")
    public ResultBean headerMenu() {
        return new ResultBean<>(userMenuService.headerMenu());
    }

}
