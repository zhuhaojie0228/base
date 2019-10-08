package com.tnkj.project.system.station.mapper;

import com.tnkj.project.system.station.domain.DeptStation;
import com.tnkj.project.system.station.domain.Station;
import java.util.List;

/**
 * 车站管理Mapper接口
 * 
 * @author tnkj
 * @date 2019-09-25
 */
public interface StationMapper 
{
    /**
     * 查询车站管理
     * 
     * @param id 车站管理ID
     * @return 车站管理
     */
    public Station selectStationById(String id);

    /**
     * 查询车站管理列表
     * 
     * @param station 车站管理
     * @return 车站管理集合
     */
    public List<Station> selectStationList(Station station);

    /**
     * 机构车站管理页面查询车站管理列表
     *
     * @param station 车站管理
     * @return 车站管理集合
     */
    public List<Station> selectStaByRel(Station station);

    /**
     * 新增车站管理
     * 
     * @param station 车站管理
     * @return 结果
     */
    public int insertStation(Station station);

    /**
     * 修改车站管理
     * 
     * @param station 车站管理
     * @return 结果
     */
    public int updateStation(Station station);

    /**
     * 删除车站管理
     * 
     * @param id 车站管理ID
     * @return 结果
     */
    public int deleteStationById(String id);

    /**
     * 批量删除车站管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStationByIds(String[] ids);

    /*
     * 获取排序号
     * */
    public Long selectCurSort();

    /**
     * 根据机构I父级D查询车站管理列表
     *
     * @param parentId 机构父级ID
     * @return 车站管理集合
     */
    public List<Station> selStationByDeptParId(String parentId);

    /**
     * 根据机构ID查询车站管理列表
     *
     * @param deptId 机构ID
     * @return 车站管理集合
     */
    public List<Station> selectStationByDeptId(String deptId);

    /**
     * 根据deptId删除机构车站关联关系
     *
     * @param deptId 机构ID
     * @return 结果
     */
    public int deleteRelByDeptId(String deptId);

    /**
     * 保存机构车站关联关系
     *
     * @param deptStation 机构车站关联关系
     * @return 结果
     */
    public int saveDeptStation(DeptStation deptStation);
}
