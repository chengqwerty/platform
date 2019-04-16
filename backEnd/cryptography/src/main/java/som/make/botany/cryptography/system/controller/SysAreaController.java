package som.make.botany.cryptography.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import som.make.botany.cryptography.common.beans.ResultBean;
import som.make.botany.cryptography.system.bean.SysAreaBean;
import som.make.botany.cryptography.system.service.SysAreaService;

@RestController
@RequestMapping("sysArea")
public class SysAreaController {

    private SysAreaService sysAreaService;

    @Autowired
    public void setSysAreaService(SysAreaService sysAreaService) {
        this.sysAreaService = sysAreaService;
    }

    /**
     * 获取区域的下一级子区域
     * @param sysAreaBean 区域
     * @return 下一级的子区域
     */
    @RequestMapping("nextLevelArea")
    public ResultBean nextLevelArea(@RequestBody SysAreaBean sysAreaBean) {
        return new ResultBean<>(sysAreaService.nextLevelArea(sysAreaBean));
    }

    /**
     * 获取下一个区域的 sort 序号
     * @param sysAreaBean 查询参数 areaParentId 上级区域
     * @return 下一个区域的 sort 序号
     */
    @RequestMapping("nextAreaSort")
    public ResultBean nextAreaSort(@RequestBody SysAreaBean sysAreaBean) {
        return new ResultBean<>(sysAreaService.nextAreaSort(sysAreaBean));
    }

    /**
     * 添加区域
     * @param sysAreaBean 需要添加的区域
     * @return 添加的结果信息，包括新添加的区域
     */
    @RequestMapping("addArea")
    public ResultBean addArea(@RequestBody SysAreaBean sysAreaBean) {
        return new ResultBean<>(sysAreaService.addArea(sysAreaBean));
    }

    /**
     * 修改区域信息，暂时不能修改区域的上级菜单
     * @param sysAreaBean 要修改的区域
     * @return 修改的结果信息，包括修改后的区域
     */
    @RequestMapping("updateArea")
    public ResultBean updateArea(@RequestBody SysAreaBean sysAreaBean) {
        return new ResultBean<>(sysAreaService.updateArea(sysAreaBean));
    }

    /**
     * 删除区域
     * @param sysAreaBean 要删除的区域
     * @return 删除的结果信息，包括删除后的区域
     */
    @RequestMapping("deleteArea")
    public ResultBean deleteArea(@RequestBody SysAreaBean sysAreaBean) {
        return new ResultBean<>(sysAreaService.deleteArea(sysAreaBean));
    }

    /**
     * 递归获取全部的区域
     * @return 查询到的区域
     */
    @RequestMapping("getSelectArea")
    public ResultBean getSelectArea() {
        return new ResultBean<>(sysAreaService.getSelectArea());
    }

}
