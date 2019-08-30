package com.tnkj.project.system.line.service.impl;

import java.util.Date;
import java.util.List;
import com.tnkj.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tnkj.project.system.line.mapper.LineMapper;
import com.tnkj.project.system.line.domain.Line;
import com.tnkj.project.system.line.service.ILineService;
import com.tnkj.common.utils.text.Convert;

/**
 * 线路Service业务层处理
 * 
 * @author tnkj
 * @date 2019-08-19
 */
@Service
public class LineServiceImpl implements ILineService 
{
    @Autowired
    private LineMapper lineMapper;

    /**
     * 查询线路
     * 
     * @param id 线路ID
     * @return 线路
     */
    @Override
    public Line selectLineById(String id)
    {
        return lineMapper.selectLineById(id);
    }

    /**
     * 查询线路列表
     * 
     * @param line 线路
     * @return 线路
     */
    @Override
    public List<Line> selectLineList(Line line)
    {
        return lineMapper.selectLineList(line);
    }

    /**
     * 新增线路
     * 
     * @param line 线路
     * @return 结果
     */
    @Override
    public int insertLine(Line line)
    {
        line.setCreateTime(DateUtils.getNowDate());
        line.setId(Long.toString(new Date().getTime()));
        return lineMapper.insertLine(line);
    }

    /**
     * 修改线路
     * 
     * @param line 线路
     * @return 结果
     */
    @Override
    public int updateLine(Line line)
    {
        line.setUpdateTime(DateUtils.getNowDate());
        return lineMapper.updateLine(line);
    }

    /**
     * 删除线路对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteLineByIds(String ids)
    {
        return lineMapper.deleteLineByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除线路信息
     * 
     * @param id 线路ID
     * @return 结果
     */
    public int deleteLineById(String id)
    {
        return lineMapper.deleteLineById(id);
    }
}
