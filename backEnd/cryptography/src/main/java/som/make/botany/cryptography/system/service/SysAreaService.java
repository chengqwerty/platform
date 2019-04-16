package som.make.botany.cryptography.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import som.make.botany.cryptography.system.bean.SelectAreaBean;
import som.make.botany.cryptography.system.bean.SysAreaBean;
import som.make.botany.cryptography.system.dao.SysAreaDao;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class SysAreaService {

    // area level code 默认分隔符
    private static final String AREA_SEPARATOR = "-";

    private SysAreaDao sysAreaDao;

    @Autowired
    public void setSysAreaDao(SysAreaDao sysAreaDao) {
        this.sysAreaDao = sysAreaDao;
    }

    public List<SysAreaBean> nextLevelArea(SysAreaBean sysAreaBean) {
        return sysAreaDao.findAllByAreaParentId(sysAreaBean.getAreaId(), new Sort(Sort.Direction.ASC, "areaSort"));
    }

    public Integer nextAreaSort(SysAreaBean sysAreaBean) {
        SysAreaBean area = sysAreaDao.findFirstByAreaParentIdOrderByAreaSortDesc(sysAreaBean.getAreaParentId());
        // 默认菜单 sort 序号起始30，间隔30
        Integer sort = 30;
        if (area!=null) {
            sort = sort + area.getAreaSort();
        }
        return sort;
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysAreaBean addArea(SysAreaBean sysAreaBean) {
        String areaParentId = sysAreaBean.getAreaParentId();
        Integer areaLevel; // 区域级别
        Integer areaLevelId; // 区域级别 id
        String areaLevelCode; // 区域级别 code
        if (StringUtils.isEmpty(areaParentId)) { // 是否有上级区域
            areaLevel = 1;
            areaLevelId = sysAreaDao.queryMaxLevelIdByParentNull();
            areaLevelId = areaLevelId == null ? 0 : areaLevelId + 1;
            areaLevelCode = areaLevelId.toString();
        } else {
            SysAreaBean parentArea = sysAreaDao.findById(areaParentId).get();
            areaLevel = parentArea.getAreaLevel() + 1;
            areaLevelId = sysAreaDao.queryMaxLevelIdByParent(areaParentId);
            areaLevelId = areaLevelId == null ? 0 : areaLevelId + 1;
            areaLevelCode = parentArea.getAreaLevelCode() + AREA_SEPARATOR + areaLevelId;
        }
        sysAreaBean.setAreaLevel(areaLevel);
        sysAreaBean.setAreaLevelId(areaLevelId);
        sysAreaBean.setAreaLevelCode(areaLevelCode);
        sysAreaBean.setAreaFlag("0");
        sysAreaBean.setCreateTime(Timestamp.from(Instant.now()));
        sysAreaBean.setCreateUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return sysAreaDao.save(sysAreaBean);
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysAreaBean updateArea(SysAreaBean sysAreaBean) {
        SysAreaBean area = sysAreaDao.findById(sysAreaBean.getAreaId()).get();
        area.setAreaName(sysAreaBean.getAreaName());
        area.setAreaDescription(sysAreaBean.getAreaDescription());
        area.setAreaCode(sysAreaBean.getAreaCode());
        area.setAreaSort(sysAreaBean.getAreaSort());
        area.setUpdateTime(Timestamp.from(Instant.now()));
        area.setUpdateUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return sysAreaDao.save(area);
    }

    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    public SysAreaBean deleteArea(SysAreaBean sysAreaBean) {
        SysAreaBean area = sysAreaDao.findById(sysAreaBean.getAreaId()).get();
        sysAreaDao.delete(area);
        return area;
    }

    public List<SelectAreaBean> getSelectArea() {
        List<SelectAreaBean> rootList = sysAreaDao.queryAreaWhenParentNull();
        rootList.forEach(selectAreaBean -> selectAreaBean.setChildren(circleSelectArea(selectAreaBean.getKey())));
        return rootList;
    }

    private List<SelectAreaBean> circleSelectArea(String areaParentId) {
        List<SelectAreaBean> areaList = sysAreaDao.queryAreaWhenParent(areaParentId);
        for (SelectAreaBean area: areaList) {
            List<SelectAreaBean> children = circleSelectArea(area.getKey());
            if (children.size() > 0) {
                area.setChildren(children);
            }
        }
        return areaList;
    }

}
