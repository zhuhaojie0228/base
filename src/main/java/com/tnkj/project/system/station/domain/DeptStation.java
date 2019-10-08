package com.tnkj.project.system.station.domain;

import com.tnkj.framework.aspectj.lang.annotation.Excel;
import com.tnkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 机构车站管理对象 sys_dept_station
 * 
 * @author tnkj
 * @date 2019-09-25
 */
public class DeptStation{
    private static final long serialVersionUID = 1L;

    /** 部门ID */
    private String deptId;

    /** 线路ID */
    private String stationId;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deptId", getDeptId())
            .append("stationId", getStationId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
