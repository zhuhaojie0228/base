package com.tnkj.project.system.station.service.impl;

import java.util.List;
import java.util.UUID;

import com.tnkj.common.utils.DateUtils;
import com.tnkj.common.utils.StringUtils;
import com.tnkj.common.utils.security.ShiroUtils;
import com.tnkj.project.system.station.domain.DeptStation;
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
     * 机构车站管理页面查询车站管理列表
     *
     * @param station 车站管理
     * @return 车站管理
     */
    @Override
    public List<Station> selectStaByRel(Station station)
    {
        return stationMapper.selectStaByRel(station);
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
        station.setSort(stationMapper.selectCurSort());
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
     * 根据机构ID查询车站管理列表
     *
     * @param deptId 机构ID
     * @return 车站管理集合
     */
    public List<Station> selectStationByDeptId(String deptId){
        return stationMapper.selectStationByDeptId(deptId);
    }

    /**
     * 根据机构父级ID查询车站管理列表
     *
     * @param parentId 机构父级ID
     * @return 车站管理集合
     */
    public List<Station> selStationByDeptParId(String parentId){
        return stationMapper.selStationByDeptParId(parentId);
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

    /**
     * 保存机构车站关联关系
     *
     * @param deptId 机构ID
     * @param stationIds 车站IDs
     * @return 结果
     */
    public int saveRel(String deptId,String stationIds){
        try{
            stationMapper.deleteRelByDeptId(deptId);
            if(StringUtils.isNotEmpty(deptId) && StringUtils.isNotEmpty(stationIds)){
                if(stationIds.indexOf(",")>-1){
                    String [] stationList=stationIds.split(",");
                    for(int i=0;i<stationList.length;i++){
                        DeptStation deptStation=new DeptStation();
                        deptStation.setDeptId(deptId);
                        deptStation.setStationId(stationList[i]);
                        deptStation.setCreateBy(ShiroUtils.getLoginName());
                        deptStation.setCreateTime(DateUtils.getNowDate());
                        stationMapper.saveDeptStation(deptStation);
                    }
                }else{
                    DeptStation deptStation=new DeptStation();
                    deptStation.setDeptId(deptId);
                    deptStation.setStationId(stationIds);
                    deptStation.setCreateBy(ShiroUtils.getLoginName());
                    deptStation.setCreateTime(DateUtils.getNowDate());
                    stationMapper.saveDeptStation(deptStation);
                }
            }
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
}
