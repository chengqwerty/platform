package som.make.botany.cryptography.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import som.make.botany.cryptography.common.beans.ResultBean;
import som.make.botany.cryptography.system.bean.SysDepartmentBean;
import som.make.botany.cryptography.system.service.SysDepartmentService;

@RestController
@RequestMapping("sysDepartment")
public class SysDepartmentController {

    private SysDepartmentService sysDepartmentService;

    @Autowired
    public void setSysDepartmentService(SysDepartmentService sysDepartmentService) {
        this.sysDepartmentService = sysDepartmentService;
    }

    /**
     * 获取机构的下一级子机构
     * @param sysDepartmentBean 机构
     * @return 下一级的子机构
     */
    @RequestMapping("nextLevelDepartment")
    public ResultBean nextLevelDepartment(@RequestBody SysDepartmentBean sysDepartmentBean) {
        return new ResultBean<>(sysDepartmentService.nextLevelDepartment(sysDepartmentBean));
    }

    /**
     * 获取下一个机构的 sort 序号
     * @param sysDepartmentBean 查询参数 departmentParentId 上级机构
     * @return 下一个机构的 sort 序号
     */
    @RequestMapping("nextDepartmentSort")
    public ResultBean nextDepartmentSort(@RequestBody SysDepartmentBean sysDepartmentBean) {
        return new ResultBean<>(sysDepartmentService.nextDepartmentSort(sysDepartmentBean));
    }

    /**
     * 添加机构
     * @param sysDepartmentBean 需要添加的机构
     * @return 添加的结果信息，包括新添加的机构
     */
    @RequestMapping("addDepartment")
    public ResultBean addDepartment(@RequestBody SysDepartmentBean sysDepartmentBean) {
        return new ResultBean<>(sysDepartmentService.addDepartment(sysDepartmentBean));
    }

    /**
     * 修改机构信息，暂时不能修改机构的上级菜单
     * @param sysDepartmentBean 要修改的机构
     * @return 修改的结果信息，包括修改后的机构
     */
    @RequestMapping("updateDepartment")
    public ResultBean updateDepartment(@RequestBody SysDepartmentBean sysDepartmentBean) {
        return new ResultBean<>(sysDepartmentService.updateDepartment(sysDepartmentBean));
    }

    /**
     * 删除机构
     * @param sysDepartmentBean 要删除的机构
     * @return 删除的结果信息，包括删除后的机构
     */
    @RequestMapping("deleteDepartment")
    public ResultBean deleteDepartment(@RequestBody SysDepartmentBean sysDepartmentBean) {
        return new ResultBean<>(sysDepartmentService.deleteDepartment(sysDepartmentBean));
    }

    /**
     * 递归获取全部的机构，用于前端 tree select
     * @return 查询到的机构
     */
    @RequestMapping("getSelectDepartment")
    public ResultBean getSelectDepartment() {
        return new ResultBean<>(sysDepartmentService.getSelectDepartment());
    }

    /**
     * 获取机构的管道数据，用于前端的管道展示
     * @return 查询到的机构
     */
    @RequestMapping("getPipeDepartment")
    public ResultBean getPipeDepartment() {
        return new ResultBean<>(sysDepartmentService.getPipeDepartment());
    }

}
