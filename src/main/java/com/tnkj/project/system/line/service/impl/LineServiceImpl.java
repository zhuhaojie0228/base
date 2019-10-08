package com.tnkj.project.system.line.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tnkj.common.utils.DateUtils;
import com.tnkj.common.utils.security.ShiroUtils;
import com.tnkj.framework.web.domain.DeptZtree;
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
 * @date 2019-09-25
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
        line.setId(UUID.randomUUID().toString());
        line.setSort(lineMapper.selectCurSort());
        line.setCreateBy(ShiroUtils.getLoginName());
        line.setCreateTime(DateUtils.getNowDate());
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
        line.setUpdateBy(ShiroUtils.getLoginName());
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
    @Override
    public int deleteLineById(String id)
    {
        return lineMapper.deleteLineById(id);
    }

    /**
     * 查询线路管理树
     *
     * @param line 线路信息
     * @return 所有线路信息
     */
    @Override
    public List<DeptZtree> selectLineTree(Line line){
        List<Line> lineList = lineMapper.selectLineList(line);
        List<DeptZtree> ztrees = initZtree(lineList);
        return ztrees;
    }

    /**
     * 对象转线路树
     *
     * @param lineList 线路列表
     * @return 树结构列表
     */
    public List<DeptZtree> initZtree(List<Line> lineList){
        List<DeptZtree> ztrees = new ArrayList<DeptZtree>();
        //添加模拟的集合：郑州电务段
        DeptZtree temp = new DeptZtree();
        temp.setId("1");
        temp.setpId("0");
        temp.setName("郑州电务段");
        temp.setTitle("郑州电务段");
        ztrees.add(temp);
        for (Line line : lineList){
            DeptZtree ztree = new DeptZtree();
            ztree.setId(line.getId());
            ztree.setpId("1");
            ztree.setName(line.getName());
            ztree.setTitle(line.getName());
            ztrees.add(ztree);
        }
        return ztrees;
    }
}
