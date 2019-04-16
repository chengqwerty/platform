package som.make.botany.cryptography.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import som.make.botany.cryptography.system.bean.SelectDepartmentBean;
import som.make.botany.cryptography.system.bean.SysDepartmentBean;
import som.make.botany.cryptography.system.dao.SysDepartmentDao;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysDepartmentService {

    // department level code 默认分隔符
    private static final String DEPARTMENT_SEPARATOR = "-";

    private SysDepartmentDao sysDepartmentDao;

    @Autowired
    public void setSysDepartmentDao(SysDepartmentDao sysDepartmentDao) {
        this.sysDepartmentDao = sysDepartmentDao;
    }

    public List<SysDepartmentBean> nextLevelDepartment(SysDepartmentBean sysDepartmentBean) {
        return sysDepartmentDao.findAllByDepartmentParentId(sysDepartmentBean.getDepartmentId(), new Sort(Sort.Direction.ASC, "departmentSort"));
    }

    public Integer nextDepartmentSort(SysDepartmentBean sysDepartmentBean) {
        SysDepartmentBean department = sysDepartmentDao.findFirstByDepartmentParentIdOrderByDepartmentSortDesc(sysDepartmentBean.getDepartmentParentId());
        // 默认菜单 sort 序号起始30，间隔30
        Integer sort = 30;
        if (department!=null) {
            sort = sort + department.getDepartmentSort();
        }
        return sort;
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysDepartmentBean addDepartment(SysDepartmentBean sysDepartmentBean) {
        String departmentParentId = sysDepartmentBean.getDepartmentParentId();
        Integer departmentLevel; // 机构级别
        Integer departmentLevelId; // 机构级别 id
        String departmentLevelCode; // 机构级别 code
        if (StringUtils.isEmpty(departmentParentId)) { // 是否有上级机构
            departmentLevel = 1;
            departmentLevelId = sysDepartmentDao.queryMaxLevelIdByParentNull();
            departmentLevelId = departmentLevelId == null ? 0 : departmentLevelId + 1;
            departmentLevelCode = departmentLevelId.toString();
        } else {
            SysDepartmentBean parentDepartment = sysDepartmentDao.findById(departmentParentId).get();
            departmentLevel = parentDepartment.getDepartmentLevel() + 1;
            departmentLevelId = sysDepartmentDao.queryMaxLevelIdByParent(departmentParentId);
            departmentLevelId = departmentLevelId == null ? 0 : departmentLevelId + 1;
            departmentLevelCode = parentDepartment.getDepartmentLevelCode() + DEPARTMENT_SEPARATOR + departmentLevelId;
        }
        sysDepartmentBean.setDepartmentLevel(departmentLevel);
        sysDepartmentBean.setDepartmentLevelId(departmentLevelId);
        sysDepartmentBean.setDepartmentLevelCode(departmentLevelCode);
        sysDepartmentBean.setDepartmentFlag("0");
        sysDepartmentBean.setCreateTime(Timestamp.from(Instant.now()));
        sysDepartmentBean.setCreateUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return sysDepartmentDao.save(sysDepartmentBean);
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysDepartmentBean updateDepartment(SysDepartmentBean sysDepartmentBean) {
        SysDepartmentBean department = sysDepartmentDao.findById(sysDepartmentBean.getDepartmentId()).get();
        department.setDepartmentName(sysDepartmentBean.getDepartmentName());
        department.setDepartmentCode(sysDepartmentBean.getDepartmentCode());
        department.setAreaId(sysDepartmentBean.getAreaId());
        department.setDepartmentDescription(sysDepartmentBean.getDepartmentDescription());
        department.setDepartmentSort(sysDepartmentBean.getDepartmentSort());
        department.setUpdateTime(Timestamp.from(Instant.now()));
        department.setUpdateUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return sysDepartmentDao.save(department);
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysDepartmentBean deleteDepartment(SysDepartmentBean sysDepartmentBean) {
        SysDepartmentBean department = sysDepartmentDao.findById(sysDepartmentBean.getDepartmentId()).get();
        sysDepartmentDao.delete(department);
        return department;
    }

    public List<SelectDepartmentBean> getSelectDepartment() {
        List<SelectDepartmentBean> rootList = sysDepartmentDao.queryDepartmentWhenParentNull();
        rootList.forEach(selectDepartmentBean -> selectDepartmentBean.setChildren(circleSelectDepartment(selectDepartmentBean.getKey())));
        return rootList;
    }

    private List<SelectDepartmentBean> circleSelectDepartment(String departmentParentId) {
        List<SelectDepartmentBean> departmentList = sysDepartmentDao.queryDepartmentWhenParent(departmentParentId);
        for (SelectDepartmentBean department: departmentList) {
            List<SelectDepartmentBean> children = circleSelectDepartment(department.getKey());
            if (children.size() > 0) {
                department.setChildren(children);
            }
        }
        return departmentList;
    }

    public Map<String, String> getPipeDepartment() {
        List<SelectDepartmentBean> rootList = sysDepartmentDao.queryDepartmentPipe();
        return rootList.stream().collect(Collectors.toMap(SelectDepartmentBean::getKey, SelectDepartmentBean::getTitle, (k1, k2) -> k1));
    }

}
