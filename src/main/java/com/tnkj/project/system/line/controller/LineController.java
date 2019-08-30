package com.tnkj.project.system.line.controller;

import java.util.List;
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
import com.tnkj.project.system.line.domain.Line;
import com.tnkj.project.system.line.service.ILineService;
import com.tnkj.framework.web.controller.BaseController;
import com.tnkj.framework.web.domain.AjaxResult;
import com.tnkj.common.utils.poi.ExcelUtil;
import com.tnkj.framework.web.page.TableDataInfo;

/**
 * 线路Controller
 * 
 * @author tnkj
 * @date 2019-08-19
 */
@Controller
@RequestMapping("/system/line")
public class LineController extends BaseController
{
    private String prefix = "system/line";

    @Autowired
    private ILineService lineService;

    @RequiresPermissions("system:line:view")
    @GetMapping()
    public String line()
    {
        return prefix + "/line";
    }

    /**
     * 查询线路列表
     */
    @RequiresPermissions("system:line:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Line line)
    {
        startPage();
        List<Line> list = lineService.selectLineList(line);
        return getDataTable(list);
    }

    /**
     * 导出线路列表
     */
    @RequiresPermissions("system:line:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Line line)
    {
        List<Line> list = lineService.selectLineList(line);
        ExcelUtil<Line> util = new ExcelUtil<Line>(Line.class);
        return util.exportExcel(list, "line");
    }

    /**
     * 新增线路
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存线路
     */
    @RequiresPermissions("system:line:add")
    @Log(title = "线路", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Line line)
    {
        return toAjax(lineService.insertLine(line));
    }

    /**
     * 修改线路
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        Line line = lineService.selectLineById(id);
        mmap.put("line", line);
        return prefix + "/edit";
    }

    /**
     * 修改保存线路
     */
    @RequiresPermissions("system:line:edit")
    @Log(title = "线路", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Line line)
    {
        return toAjax(lineService.updateLine(line));
    }

    /**
     * 删除线路
     */
    @RequiresPermissions("system:line:remove")
    @Log(title = "线路", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(lineService.deleteLineByIds(ids));
    }
}
