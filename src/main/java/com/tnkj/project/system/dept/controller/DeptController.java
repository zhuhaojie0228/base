package com.tnkj.project.system.dept.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.tnkj.common.utils.security.ShiroUtils;
import com.tnkj.framework.web.domain.DeptZtree;
import com.tnkj.project.system.line.domain.Line;
import com.tnkj.project.system.line.service.ILineService;
import com.tnkj.project.system.station.domain.Station;
import com.tnkj.project.system.station.service.IStationService;
import com.tnkj.project.system.user.domain.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.tnkj.common.constant.UserConstants;
import com.tnkj.common.utils.StringUtils;
import com.tnkj.framework.aspectj.lang.annotation.Log;
import com.tnkj.framework.aspectj.lang.enums.BusinessType;
import com.tnkj.framework.web.controller.BaseController;
import com.tnkj.framework.web.domain.AjaxResult;
import com.tnkj.framework.web.domain.Ztree;
import com.tnkj.project.system.dept.domain.Dept;
import com.tnkj.project.system.dept.service.IDeptService;
import com.tnkj.project.system.role.domain.Role;

/**
 * 部门信息
 * 
 * @author tnkj
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController
{
    private String prefix = "system/dept";

    @Autowired
    private IDeptService deptService;

    @Autowired
    private ILineService lineService;

    @Autowired
    private IStationService stationService;

    @RequiresPermissions("system:dept:view")
    @GetMapping()
    public String dept()
    {
        return prefix + "/dept";
    }

    @RequiresPermissions("system:dept:list")
    @PostMapping("/list")
    @ResponseBody
    public List<Dept> list(Dept dept)
    {
        List<Dept> deptList = deptService.selectDeptList(dept);
        return deptList;
    }

    @PostMapping("/upOrDownRow")
    @ResponseBody
    public AjaxResult upOrDownRow(String id,String upOrDwon) {
        Dept dept=deptService.selectDeptById(id);
        Dept temp=new Dept();
        temp.setLevel(dept.getLevel());
        temp.setParentId("1");
        List<Dept> deptList=deptService.selectDeptList(temp);
        Dept curDept=new Dept();
        Dept tempDept=new Dept();
        if("up".equals(upOrDwon)){
            int rowNumber=0;
            for(int i=0;i<deptList.size();i++){
                if(id.equals(deptList.get(i).getId())){
                    rowNumber=i;
                    break;
                }
            }
            if(rowNumber==0){
                return AjaxResult.warn("当前部门已经是该级别第一条数据，无需上移");
            }else{
                curDept=deptList.get(rowNumber);
                tempDept=deptList.get(rowNumber-1);
            }
        }else if("down".equals(upOrDwon)){
            int rowNumber=0;
            for(int i=0;i<deptList.size();i++){
                if(id.equals(deptList.get(i).getId())){
                    rowNumber=i;
                    break;
                }
            }
            if((rowNumber+1)==deptList.size()){
                return AjaxResult.warn("当前部门已经是该级别最后一条数据，无需下移");
            }else{
                curDept=deptList.get(rowNumber);
                tempDept=deptList.get(rowNumber+1);
            }
        }
        Long curSort=curDept.getSort();
        Long tempSort=tempDept.getSort();
        curDept.setSort(tempSort);
        tempDept.setSort(curSort);
        deptService.updateDept(curDept);
        deptService.updateDept(tempDept);
        return AjaxResult.success();
    }

    @PostMapping("/getDept")
    @ResponseBody
    public List<Dept> getDept(Dept dept)
    {
        List<Dept> deptList = deptService.selectDeptList(dept);
        return deptList;
    }

    /**
     * 新增部门
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") String parentId, ModelMap mmap)
    {
        mmap.put("dept", deptService.selectDeptById(parentId));
        return prefix + "/add";
    }

    /**
     * 新增保存部门
     */
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dept:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated Dept dept)
    {
        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
        {
            return error("新增部门'" + dept.getName() + "'失败，部门名称已存在");
        }
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        Dept dept = deptService.selectDeptById(id);
        if (StringUtils.isNotNull(dept) && id.equals("1"))
        {
            dept.setParentName("无");
        }
        mmap.put("dept", dept);
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dept:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated Dept dept)
    {
        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return error("修改部门'" + dept.getName() + "'失败，部门名称已存在");
        }
        if(StringUtils.isNotNull(dept.getStatus()) && "1".equals(dept.getStatus())){
            if(deptService.selectDeptCount(dept.getId()) > 0) {
                return AjaxResult.warn("存在下级机构,不允许停用");
            }else if(deptService.checkDeptExistUser(dept.getId())) {
                return AjaxResult.warn("机构存在用户,不允许停用");
            }
            //校验部门下是否存在车站
            List<Station> stationList=stationService.selectStationByDeptId(dept.getId());
            if(stationList!=null && !stationList.isEmpty()){
                return AjaxResult.warn("机构存在车站,不允许删除");
            }
        }
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除
     */
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dept:remove")
    @GetMapping("/remove/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("id") String id)
    {
        if (deptService.selectDeptCount(id) > 0)
        {
            return AjaxResult.warn("存在下级机构,不允许删除");
        }
        if (deptService.checkDeptExistUser(id))
        {
            return AjaxResult.warn("机构存在用户,不允许删除");
        }
        //校验部门下是否存在车站
        List<Station> stationList=stationService.selectStationByDeptId(id);
        if(stationList!=null && !stationList.isEmpty()){
            return AjaxResult.warn("机构存在车站,不允许删除");
        }

        return toAjax(deptService.deleteDeptById(id));
    }

    /**
     * 校验部门名称
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public String checkDeptNameUnique(Dept dept)
    {
        return deptService.checkDeptNameUnique(dept);
    }

    /**
     * 选择部门树
     */
    @GetMapping("/selectDeptTree/{deptId}")
    public String selectDeptTree(@PathVariable("deptId") String deptId, ModelMap mmap)
    {
        mmap.put("dept", deptService.selectDeptById(deptId));
        return prefix + "/tree";
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<DeptZtree> treeData()
    {
        List<DeptZtree> ztrees = deptService.selectDeptTree(new Dept());
        return ztrees;
    }

    /**
     * 选择部门树:车间和工区
     */
    @GetMapping("/selectWorkDeptTree/{deptId}")
    public String selectWorkDeptTree(@PathVariable("deptId") String deptId, ModelMap mmap)
    {
        mmap.put("dept", deptService.selectDeptById(deptId));
        return prefix + "/workTree";
    }

    /**
     * 加载部门列表树:车间和工区
     */
    @GetMapping("/workTreeData")
    @ResponseBody
    public List<DeptZtree> workTreeData() {
        User user = ShiroUtils.getSysUser();
        Dept curDept=deptService.selectDeptById(user.getDeptId());
        Dept dept=new Dept();
        if(!user.isAdmin()){
            if("9".equals(curDept.getLevel())){
                dept.setParentId(curDept.getId());
            }else if("10".equals(curDept.getLevel())){
                dept.setId(curDept.getId());
                //当登录账户部门为工区时，查询出对应的车间
                dept.setRemark(curDept.getParentId());
            }else{
                dept.setId("1");
            }
            List<DeptZtree> ztrees = deptService.selectWorkByDept(dept);
            return ztrees;
        }
        List<DeptZtree> ztrees = deptService.selectWorkDeptTree(dept);
        return ztrees;
    }

    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/roleDeptTreeData")
    @ResponseBody
    public List<DeptZtree> deptTreeData(Role role)
    {
        List<DeptZtree> ztrees = deptService.roleDeptTreeData(role);
        return ztrees;
    }
}
