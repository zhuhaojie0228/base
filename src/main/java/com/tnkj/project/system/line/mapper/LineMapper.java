package com.tnkj.project.system.line.mapper;

import com.tnkj.project.system.line.domain.Line;
import java.util.List;

/**
 * 线路Mapper接口
 * 
 * @author tnkj
 * @date 2019-09-25
 */
public interface LineMapper 
{
    /**
     * 查询线路
     * 
     * @param id 线路ID
     * @return 线路
     */
    public Line selectLineById(String id);

    /**
     * 查询线路列表
     * 
     * @param line 线路
     * @return 线路集合
     */
    public List<Line> selectLineList(Line line);

    /**
     * 新增线路
     * 
     * @param line 线路
     * @return 结果
     */
    public int insertLine(Line line);

    /**
     * 修改线路
     * 
     * @param line 线路
     * @return 结果
     */
    public int updateLine(Line line);

    /**
     * 删除线路
     * 
     * @param id 线路ID
     * @return 结果
     */
    public int deleteLineById(String id);

    /**
     * 批量删除线路
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteLineByIds(String[] ids);

    /*
     * 获取排序号
     * */
    public Long selectCurSort();
}
