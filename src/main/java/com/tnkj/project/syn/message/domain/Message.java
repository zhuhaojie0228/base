package com.tnkj.project.syn.message.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.tnkj.framework.aspectj.lang.annotation.Excel;
import com.tnkj.framework.web.domain.BaseEntity;

/**
 * 同步消息对象 sys_message
 * 
 * @author tnkj
 * @date 2019-08-30
 */
public class Message extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 系统标识 */
    @Excel(name = "系统标识")
    private String system;

    /** 操作表 */
    @Excel(name = "操作表")
    private String oprTable;

    /** 发送消息 */
    @Excel(name = "发送消息")
    private String message;

    /** 同步类型 */
    @Excel(name = "同步类型")
    private String type;

    /** 同步状态 */
    @Excel(name = "同步状态")
    private String synStatus;

    /** 失败原因 */
    @Excel(name = "失败原因")
    private String errCause;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSystem(String system) 
    {
        this.system = system;
    }

    public String getSystem() 
    {
        return system;
    }
    public void setOprTable(String oprTable) 
    {
        this.oprTable = oprTable;
    }

    public String getOprTable() 
    {
        return oprTable;
    }
    public void setMessage(String message) 
    {
        this.message = message;
    }

    public String getMessage() 
    {
        return message;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setSynStatus(String synStatus) 
    {
        this.synStatus = synStatus;
    }

    public String getSynStatus() 
    {
        return synStatus;
    }
    public void setErrCause(String errCause) 
    {
        this.errCause = errCause;
    }

    public String getErrCause() 
    {
        return errCause;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("system", getSystem())
            .append("oprTable", getOprTable())
            .append("message", getMessage())
            .append("type", getType())
            .append("synStatus", getSynStatus())
            .append("errCause", getErrCause())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
