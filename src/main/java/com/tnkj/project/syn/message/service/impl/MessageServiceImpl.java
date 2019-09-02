package com.tnkj.project.syn.message.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.tnkj.common.utils.DateUtils;
import com.tnkj.common.utils.security.ShiroUtils;
import com.tnkj.project.system.dept.domain.Dept;
import com.tnkj.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tnkj.project.syn.message.mapper.MessageMapper;
import com.tnkj.project.syn.message.domain.Message;
import com.tnkj.project.syn.message.service.IMessageService;
import com.tnkj.common.utils.text.Convert;

/**
 * 同步消息Service业务层处理
 * 
 * @author tnkj
 * @date 2019-08-30
 */
@Service
public class MessageServiceImpl implements IMessageService 
{
    @Autowired
private MessageMapper messageMapper;

    /**
     * 查询同步消息
     * 
     * @param id 同步消息ID
     * @return 同步消息
     */
    @Override
    public Message selectMessageById(Long id)
    {
        return messageMapper.selectMessageById(id);
    }

    /**
     * 查询同步消息列表
     * 
     * @param message 同步消息
     * @return 同步消息
     */
    @Override
    public List<Message> selectMessageList(Message message)
    {
        return messageMapper.selectMessageList(message);
    }

    /**
     * 查询所有的同步消息列表
     *
     * @param message 同步消息
     * @return 同步消息
     */
    @Override
    public List<Message> selectAllMessage(Message message)
    {
        return messageMapper.selectAllMessage(message);
    }

    /**
     * 新增同步消息
     * 
     * @param message 同步消息
     * @return 结果
     */
    @Override
    public int insertMessage(Message message)
    {
        message.setCreateTime(DateUtils.getNowDate());
        return messageMapper.insertMessage(message);
    }

    /**
     * 修改同步消息
     * 
     * @param message 同步消息
     * @return 结果
     */
    @Override
    public int updateMessage(Message message)
    {
        message.setUpdateTime(DateUtils.getNowDate());
        return messageMapper.updateMessage(message);
    }

    /**
     * 删除同步消息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMessageByIds(String ids)
    {
        return messageMapper.deleteMessageByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除同步消息信息
     * 
     * @param id 同步消息ID
     * @return 结果
     */
    public int deleteMessageById(Long id)
    {
        return messageMapper.deleteMessageById(id);
    }

    /**
     * 用户信息映射
     *
     * @param user 用户
     * @return 用户同步
     */
    @Override
    public JSONObject getUserMessage(User user) {
        JSONObject userMessage=new JSONObject();
        userMessage.put("userId",user.getUserId());
        userMessage.put("loginName",user.getLoginName());
        userMessage.put("deptId",user.getDeptId());
        userMessage.put("userName",user.getUserName());
        userMessage.put("password",user.getPassword());
        userMessage.put("no",user.getNo());
        userMessage.put("wageNumber",user.getWageNumber());
        userMessage.put("idcard",user.getIdcard());
        userMessage.put("sex",user.getSex());
        userMessage.put("email",user.getEmail());
        userMessage.put("phone",user.getPhone());
        userMessage.put("phonenumber",user.getPhonenumber());
        userMessage.put("status",user.getStatus());
        userMessage.put("delFlag",user.getDelFlag());
        return userMessage;
    }

    /**
     * 机构信息映射
     *
     * @param dept 机构
     * @return 机构同步
     */
    @Override
    public JSONObject getDeptMessage(Dept dept) {
        JSONObject deptMessage=new JSONObject();
        deptMessage.put("id",dept.getId());
        deptMessage.put("name",dept.getName());
        deptMessage.put("shortName",dept.getShortName());
        deptMessage.put("parentId",dept.getParentId());
        deptMessage.put("level",dept.getLevel());
        deptMessage.put("kind",dept.getKind());
        deptMessage.put("classid",dept.getClassid());
        deptMessage.put("sort",dept.getSort());
        deptMessage.put("phone",dept.getPhone());
        deptMessage.put("status",dept.getStatus());
        deptMessage.put("delFlag",dept.getDelFlag());
        return deptMessage;
    }
}
