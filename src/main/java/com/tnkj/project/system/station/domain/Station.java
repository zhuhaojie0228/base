package com.tnkj.project.system.station.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.tnkj.framework.aspectj.lang.annotation.Excel;
import com.tnkj.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 车站管理对象 sys_station
 * 
 * @author tnkj
 * @date 2019-09-25
 */
public class Station extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 车站略号 */
    @Excel(name = "车站略号")
    private String stationNumber;

    /** 车站名称 */
    @Excel(name = "车站名称")
    private String name;

    /** 车站简称 */
    @Excel(name = "车站简称")
    private String shortName;

    /** 归属线别ID */
    private String lineId;

    /** 归属线路 */
    @Excel(name = "归属线路")
    private String lineName;

    /** 是否关联标志 */
    private String isRelated;

    /** 种类:1是车站，2是驼峰，3是道口，4是区间 */
    @Excel(name = "种类")
    private Long type;

    /** 车站类别(字典)区段站 中间站 其他 */
    @Excel(name = "车站类别")
    private String stationClass;

    /** 车站类型:1-大站 其它-小站 */
    @Excel(name = "车站类型")
    private Long flag;

    /** 上行公里标 */
    @Excel(name = "上行公里标")
    private Double upMark;

    /** 下行公里标 */
    @Excel(name = "下行公里标")
    private Double downMark;

    /** 值班电话 */
    @Excel(name = "值班电话")
    private String phone;

    /** 建设年 */
    @Excel(name = "建设年")
    private String build;

    /** 大修年 */
    @Excel(name = "大修年")
    private String repairH;

    /** 中修年 */
    @Excel(name = "中修年")
    private String repairM;

    /** 排序号 */
    @Excel(name = "排序号")
    private Long sort;

    /** 删除标识 */
    private String delFlag;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setStationNumber(String stationNumber) 
    {
        this.stationNumber = stationNumber;
    }

    public String getStationNumber() 
    {
        return stationNumber;
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
    public void setLineId(String lineId) 
    {
        this.lineId = lineId;
    }

    public String getLineId() 
    {
        return lineId;
    }
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }
    public void setStationClass(String stationClass) 
    {
        this.stationClass = stationClass;
    }

    public String getStationClass() 
    {
        return stationClass;
    }
    public void setFlag(Long flag) 
    {
        this.flag = flag;
    }

    public Long getFlag() 
    {
        return flag;
    }
    public void setUpMark(Double upMark) 
    {
        this.upMark = upMark;
    }

    public Double getUpMark() 
    {
        return upMark;
    }
    public void setDownMark(Double downMark) 
    {
        this.downMark = downMark;
    }

    public Double getDownMark() 
    {
        return downMark;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setBuild(String build)
    {
        this.build = build;
    }

    public String getBuild()
    {
        return build;
    }
    public void setRepairH(String repairH)
    {
        this.repairH = repairH;
    }

    public String getRepairH()
    {
        return repairH;
    }
    public void setRepairM(String repairM)
    {
        this.repairM = repairM;
    }

    public String getRepairM()
    {
        return repairM;
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

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getIsRelated() {
        return isRelated;
    }

    public void setIsRelated(String isRelated) {
        this.isRelated = isRelated;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("stationNumber", getStationNumber())
            .append("name", getName())
            .append("shortName", getShortName())
            .append("lineId", getLineId())
            .append("lineName", getLineName())
            .append("isRelated", getIsRelated())
            .append("type", getType())
            .append("stationClass", getStationClass())
            .append("flag", getFlag())
            .append("upMark", getUpMark())
            .append("downMark", getDownMark())
            .append("phone", getPhone())
            .append("build", getBuild())
            .append("repairH", getRepairH())
            .append("repairM", getRepairM())
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
