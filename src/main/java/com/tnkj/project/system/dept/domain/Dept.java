package com.tnkj.project.system.dept.domain;

import javax.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.tnkj.framework.web.domain.BaseEntity;

/**
 * 部门表 sys_dept
 * 
 * @author tnkj
 */
public class Dept extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 机构ID */
    private String id;

    /** 机构名称 */
    private String name;

    /** 简称 */
    private String shortName;

    /** 父级ID */
    private String parentId;

    /** 父级名称 */
    private String parentName;

    /** 父级机构级别 */
    private String parentLevel;

    /** 机构级别 */
    private String level;

    /** 机构类别 */
    private String kind;

    /** 生产机构分类 */
    private String classid;

    /** 机构状态:0正常,1停用 */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 排序 */
    private Long sort;

    /** 值班电话 */
    private String phone;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @NotBlank(message = "机构名称不能为空")
    @Size(min = 0, max = 30, message = "机构名称长度不能超过30个字符")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @NotBlank(message = "简称不能为空")
    @Size(min = 0, max = 30, message = "简称长度不能超过30个字符")
    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentLevel() {
        return parentLevel;
    }

    public void setParentLevel(String parentLevel) {
        this.parentLevel = parentLevel;
    }

    @Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("name", getName())
            .append("phone", getPhone())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
