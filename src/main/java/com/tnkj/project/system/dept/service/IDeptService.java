package com.tnkj.project.system.dept.service;

import java.util.List;

import com.tnkj.framework.web.domain.DeptZtree;
import com.tnkj.framework.web.domain.Ztree;
import com.tnkj.project.system.dept.domain.Dept;
import com.tnkj.project.system.role.domain.Role;

/**
 * 部门管理 服务层
 * 
 * @author tnkj
 */
public interface IDeptService
{
    /**
     * 查询部门管理数据
     * 
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<Dept> selectDeptList(Dept dept);

    /**
     * 查询部门管理树
     * 
     * @param dept 部门信息
     * @return 所有部门信息
     */
    public List<DeptZtree> selectDeptTree(Dept dept);

    /**
     * 根据角色ID查询菜单
     *
     * @param role 角色对象
     * @return 菜单列表
     */
    public List<DeptZtree> roleDeptTreeData(Role role);

    /**
     * 查询部门人数
     * 
     * @param parentId 父部门ID
     * @return 结果
     */
    public int selectDeptCount(String parentId);

    /**
     * 查询部门是否存在用户
     * 
     * @param id 部门ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkDeptExistUser(String id);

    /**
     * 删除部门管理信息
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(String deptId);

    /**
     * 新增保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(Dept dept);

    /**
     * 修改保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(Dept dept);

    /**
     * 根据部门ID查询信息
     * 
     * @param deptId 部门ID
     * @return 部门信息
     */
    public Dept selectDeptById(String deptId);

    /**
     * 校验部门名称是否唯一
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public String checkDeptNameUnique(Dept dept);
}
