package com.tnkj.project.system.station.service.impl;

import java.util.List;
import java.util.UUID;

import com.tnkj.common.utils.DateUtils;
import com.tnkj.common.utils.security.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tnkj.project.system.station.mapper.StationMapper;
import com.tnkj.project.system.station.domain.Station;
import com.tnkj.project.system.station.service.IStationService;
import com.tnkj.common.utils.text.Convert;

/**
 * 车站管理Service业务层处理
 * 
 * @author tnkj
 * @date 2019-09-25
 */
@Service
public class StationServiceImpl implements IStationService 
{
    @Autowired
    private StationMapper stationMapper;

    /**
     * 查询车站管理
     * 
     * @param id 车站管理ID
     * @return 车站管理
     */
    @Override
    public Station selectStationById(String id)
    {
        return stationMapper.selectStationById(id);
    }

    /**
     * 查询车站管理列表
     * 
     * @param station 车站管理
     * @return 车站管理
     */
    @Override
    public List<Station> selectStationList(Station station)
    {
        return stationMapper.selectStationList(station);
    }

    /**
     * 新增车站管理
     * 
     * @param station 车站管理
     * @return 结果
     */
    @Override
    public int insertStation(Station station)
    {
        station.setId(UUID.randomUUID().toString());
        station.setCreateBy(ShiroUtils.getLoginName());
        station.setCreateTime(DateUtils.getNowDate());
        return stationMapper.insertStation(station);
    }

    /**
     * 修改车站管理
     * 
     * @param station 车站管理
     * @return 结果
     */
    @Override
    public int updateStation(Station station)
    {
        station.setUpdateBy(ShiroUtils.getLoginName());
        station.setUpdateTime(DateUtils.getNowDate());
        return stationMapper.updateStation(station);
    }

    /**
     * 删除车站管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStationByIds(String ids)
    {
        return stationMapper.deleteStationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除车站管理信息
     * 
     * @param id 车站管理ID
     * @return 结果
     */
    @Override
    public int deleteStationById(String id)
    {
        return stationMapper.deleteStationById(id);
    }
}
