package com.tnkj.project.system.dept.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.tnkj.framework.web.domain.DeptZtree;
import com.tnkj.project.syn.message.domain.Message;
import com.tnkj.project.syn.message.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tnkj.common.constant.UserConstants;
import com.tnkj.common.exception.BusinessException;
import com.tnkj.common.utils.StringUtils;
import com.tnkj.common.utils.security.ShiroUtils;
import com.tnkj.framework.aspectj.lang.annotation.DataScope;
import com.tnkj.framework.web.domain.Ztree;
import com.tnkj.project.system.dept.domain.Dept;
import com.tnkj.project.system.dept.mapper.DeptMapper;
import com.tnkj.project.system.role.domain.Role;

/**
 * 部门管理 服务实现
 * 
 * @author tnkj
 */
@Service
public class DeptServiceImpl implements IDeptService
{
    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private IMessageService messageService;

    /**
     * 查询部门管理数据
     * 
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<Dept> selectDeptList(Dept dept)
    {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 查询部门管理树
     * 
     * @param dept 部门信息
     * @return 所有部门信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<DeptZtree> selectDeptTree(Dept dept)
    {
        List<Dept> deptList = deptMapper.selectDeptList(dept);
        List<DeptZtree> ztrees = initZtree(deptList);
        return ztrees;
    }

    /**
     * 根据角色ID查询部门（数据权限）
     *
     * @param role 角色对象
     * @return 部门列表（数据权限）
     */
    @Override
    public List<DeptZtree> roleDeptTreeData(Role role)
    {
        Long roleId = role.getRoleId();
        List<DeptZtree> ztrees = new ArrayList<DeptZtree>();
        List<Dept> deptList = selectDeptList(new Dept());
        if (StringUtils.isNotNull(roleId))
        {
            List<String> roleDeptList = deptMapper.selectRoleDeptTree(roleId);
            ztrees = initZtree(deptList, roleDeptList);
        }
        else
        {
            ztrees = initZtree(deptList);
        }
        return ztrees;
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @return 树结构列表
     */
    public List<DeptZtree> initZtree(List<Dept> deptList)
    {
        return initZtree(deptList, null);
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 树结构列表
     */
    public List<DeptZtree> initZtree(List<Dept> deptList, List<String> roleDeptList)
    {
        List<DeptZtree> ztrees = new ArrayList<DeptZtree>();
        boolean isCheck = StringUtils.isNotNull(roleDeptList);
        for (Dept dept : deptList)
        {
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
            {
                DeptZtree ztree = new DeptZtree();
                ztree.setId(dept.getId());
                ztree.setpId(dept.getParentId());
                ztree.setName(dept.getName());
                ztree.setTitle(dept.getName());
                if (isCheck)
                {
                    ztree.setChecked(roleDeptList.contains(dept.getId() + dept.getName()));
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    /**
     * 查询部门人数
     * 
     * @param parentId 部门ID
     * @return 结果
     */
    @Override
    public int selectDeptCount(String parentId)
    {
        Dept dept = new Dept();
        dept.setParentId(parentId);
        return deptMapper.selectDeptCount(dept);
    }

    /**
     * 查询部门是否存在用户
     * 
     * @param id 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(String id)
    {
        int result = deptMapper.checkDeptExistUser(id);
        return result > 0 ? true : false;
    }

    /**
     * 删除部门管理信息
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(String deptId)
    {
        int row=deptMapper.deleteDeptById(deptId);
        //删除同步消息信息
        synDelDept(deptId);
        return row;
    }

    /**
     * 新增保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(Dept dept)
    {
        Dept info = deptMapper.selectDeptById(dept.getParentId());
        // 如果父节点不为"正常"状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
        {
            throw new BusinessException("部门停用，不允许新增");
        }
        dept.setCreateBy(ShiroUtils.getLoginName());
        dept.setId(UUID.randomUUID().toString());
        //dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        int row=deptMapper.insertDept(dept);
        //新增同步消息信息
        synDeptMessage(dept,"add");
        return row;
    }

    /**
     * 修改保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDept(Dept dept)
    {
        Dept newParentDept = deptMapper.selectDeptById(dept.getParentId());
        Dept oldDept = selectDeptById(dept.getId());
        /*if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept))
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }*/
        dept.setUpdateBy(ShiroUtils.getLoginName());
        int result = deptMapper.updateDept(dept);
        //新增同步消息信息
        synDeptMessage(dept,"edit");
        /*if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept);
        }*/
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     * 
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(Dept dept)
    {
        String updateBy = dept.getUpdateBy();
        dept = deptMapper.selectDeptById(dept.getId());
        dept.setUpdateBy(updateBy);
        deptMapper.updateDeptStatus(dept);
    }

    /**
     * 修改子元素关系
     * 
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(String deptId, String newAncestors, String oldAncestors)
    {
        List<Dept> children = deptMapper.selectChildrenDeptById(deptId);
        /*for (Dept child : children)
        {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }*/
        if (children.size() > 0)
        {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 修改子元素关系
     * 
     * @param deptId 部门ID
     * @param ancestors 元素列表
     */
    public void updateDeptChildren(String deptId, String ancestors)
    {
        Dept dept = new Dept();
        dept.setParentId(deptId);
        List<Dept> childrens = deptMapper.selectDeptList(dept);
        /*for (Dept children : childrens)
        {
            children.setAncestors(ancestors + "," + dept.getParentId());
        }*/
        if (childrens.size() > 0)
        {
            deptMapper.updateDeptChildren(childrens);
        }
    }

    /**
     * 根据部门ID查询信息
     * 
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public Dept selectDeptById(String deptId)
    {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 校验部门名称是否唯一
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(Dept dept)
    {
        String deptId = StringUtils.isNull(dept.getId()) ? "" : dept.getId();
        Dept info = deptMapper.checkDeptNameUnique(dept.getName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && !info.getId().equals(deptId))
        {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }

    public void synDeptMessage(Dept dept,String type){
        Message message =new Message();
        message.setSystem("ledger");
        message.setOprTable("sys_dept");
        message.setType(type);
        message.setSynStatus("未同步");
        JSONObject deptMessage=messageService.getDeptMessage(dept);
        deptMessage.put("type",type);
        message.setMessage(deptMessage.toString());
        int result =messageService.insertMessage(message);
    }

    public void synDelDept(String id){
        Message message =new Message();
        message.setSystem("ledger");
        message.setOprTable("sys_dept");
        message.setType("delete");
        message.setSynStatus("未同步");
        JSONObject deptMessage=new JSONObject();
        deptMessage.put("id",id);
        deptMessage.put("type","delete");
        message.setMessage(deptMessage.toString());
        int result =messageService.insertMessage(message);
    }
}
