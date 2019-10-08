package com.tnkj.project.system.station.controller;

import java.util.List;

import com.tnkj.common.utils.StringUtils;
import com.tnkj.project.system.dept.domain.Dept;
import com.tnkj.project.system.dept.service.IDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tnkj.framework.aspectj.lang.annotation.Log;
import com.tnkj.framework.aspectj.lang.enums.BusinessType;
import com.tnkj.project.system.station.domain.Station;
import com.tnkj.project.system.station.service.IStationService;
import com.tnkj.framework.web.controller.BaseController;
import com.tnkj.framework.web.domain.AjaxResult;
import com.tnkj.common.utils.poi.ExcelUtil;
import com.tnkj.framework.web.page.TableDataInfo;

/**
 * 车站管理Controller
 * 
 * @author tnkj
 * @date 2019-09-25
 */
@Controller
@RequestMapping("/system/station")
public class StationController extends BaseController
{
    private String prefix = "system/station";

    @Autowired
    private IStationService stationService;

    @Autowired
    private IDeptService deptService;

    @RequiresPermissions("system:station:view")
    @GetMapping()
    public String station()
    {
        return prefix + "/station";
    }

    @RequiresPermissions("system:station:deptView")
    @GetMapping("/deptStation")
    public String deptStation()
    {
        return prefix + "/dept_station";
    }

    /**
     * 查询车站管理列表
     */
    @RequiresPermissions("system:station:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Station station) {
        startPage();
        List<Station> list = stationService.selectStationList(station);
        return getDataTable(list);
    }

    @PostMapping("/queryList")
    @ResponseBody
    public List<Station> queryList(Station station) {
        /*if(StringUtils.isNotEmpty(station.getDeptId())) {
            Dept dept = deptService.selectDeptById(station.getDeptId());
            if ("9".equals(dept.getLevel())) {
                station.setParentId(station.getDeptId());
                station.setDeptId(null);
            }
        }*/
        return stationService.selectStationList(station);
    }


    /**
     * 导出车站管理列表
     */
    @RequiresPermissions("system:station:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Station station)
    {
        List<Station> list = stationService.selectStationList(station);
        ExcelUtil<Station> util = new ExcelUtil<Station>(Station.class);
        return util.exportExcel(list, "station");
    }

    /**
     * 新增车站管理
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存车站管理
     */
    @RequiresPermissions("system:station:add")
    @Log(title = "车站管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Station station)
    {
        return toAjax(stationService.insertStation(station));
    }

    /**
     * 修改车站管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        Station station = stationService.selectStationById(id);
        mmap.put("station", station);
        return prefix + "/edit";
    }

    /**
     * 修改保存车站管理
     */
    @RequiresPermissions("system:station:edit")
    @Log(title = "车站管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Station station)
    {
        return toAjax(stationService.updateStation(station));
    }

    @PostMapping("/queryStation")
    @ResponseBody
    public List<Station> queryStation(String deptId) {
        return stationService.selectStationByDeptId(deptId);
    }

    @PostMapping("/queryChiStation")
    @ResponseBody
    public List<Station> queryChiStation(String parentId) {
        return stationService.selStationByDeptParId(parentId);
    }

    /**
     * 删除车站管理
     */
    @RequiresPermissions("system:station:remove")
    @Log(title = "车站管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(stationService.deleteStationByIds(ids));
    }

    @Log(title = "机构车站关联管理", businessType = BusinessType.UPDATE)
    @PostMapping( "/saveRel")
    @ResponseBody
    public AjaxResult saveRel(String deptId,String stationIds) {
        return toAjax(stationService.saveRel(deptId,stationIds));
    }
}
