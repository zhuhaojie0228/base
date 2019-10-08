package com.tnkj.project.system.line.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.tnkj.framework.aspectj.lang.annotation.Excel;
import com.tnkj.framework.web.domain.BaseEntity;

/**
 * 线路对象 sys_line
 * 
 * @author tnkj
 * @date 2019-09-25
 */
public class Line extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 线路代码 */
    @Excel(name = "线路代码")
    private String number;

    /** 线路名称 */
    @Excel(name = "线路名称")
    private String name;

    /** 线路名称（缩写） */
    @Excel(name = "线路名称", readConverterExp = "缩=写")
    private String shortName;

    /** 线路级别0：主线（被分段），1：主线，2：支线，3：区段 */
    @Excel(name = "线路级别",readConverterExp = "0=主线(被分段),1=主线,2=支线,3=区段")
    private String level;

    /** 线路类型：单线，复线，三线，四线 */
    @Excel(name = "线路类型",readConverterExp = "1=单线,2=复线,3=三线,4=四线")
    private String lineNumber;

    /** 线路等级：例如客运专线，繁忙干线,干线,其他等 */
    @Excel(name = "线路等级",readConverterExp = "1=客运专线,2=繁忙干线,3=干线,4=其他")
    private String classId;

    /** 排序号 */
    @Excel(name = "排序号")
    private Long sort;

    /** 删除标记 */
    private String delFlag;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setNumber(String number) 
    {
        this.number = number;
    }

    public String getNumber() 
    {
        return number;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setShortName(String shortName) 
    {
        this.shortName = shortName;
    }

    public String getShortName() 
    {
        return shortName;
    }
    public void setLevel(String level)
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }
    public void setLineNumber(String lineNumber) 
    {
        this.lineNumber = lineNumber;
    }

    public String getLineNumber() 
    {
        return lineNumber;
    }
    public void setClassId(String classId) 
    {
        this.classId = classId;
    }

    public String getClassId() 
    {
        return classId;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("number", getNumber())
            .append("name", getName())
            .append("shortName", getShortName())
            .append("level", getLevel())
            .append("lineNumber", getLineNumber())
            .append("classId", getClassId())
            .append("sort", getSort())
            .append("delFlag", getDelFlag())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
