package com.tnkj.project.system.station.service;

import com.tnkj.project.system.station.domain.Station;
import java.util.List;

/**
 * 车站管理Service接口
 * 
 * @author tnkj
 * @date 2019-09-25
 */
public interface IStationService 
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
     * 批量删除车站管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStationByIds(String ids);

    /**
     * 删除车站管理信息
     * 
     * @param id 车站管理ID
     * @return 结果
     */
    public int deleteStationById(String id);
}
