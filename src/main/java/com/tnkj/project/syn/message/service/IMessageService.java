package com.tnkj.project.syn.message.service;

import com.alibaba.fastjson.JSONObject;
import com.tnkj.project.syn.message.domain.Message;
import com.tnkj.project.system.dept.domain.Dept;
import com.tnkj.project.system.user.domain.User;

import java.util.List;

/**
 * 同步消息Service接口
 * 
 * @author tnkj
 * @date 2019-08-30
 */
public interface IMessageService 
{
    /**
     * 查询同步消息
     * 
     * @param id 同步消息ID
     * @return 同步消息
     */
    public Message selectMessageById(Long id);

    /**
     * 查询同步消息列表
     * 
     * @param message 同步消息
     * @return 同步消息集合
     */
    public List<Message> selectMessageList(Message message);

    /**
     * 查询所有的同步消息列表
     *
     * @param message 同步消息
     * @return 同步消息集合
     */
    public List<Message> selectAllMessage(Message message);


    /**
     * 新增同步消息
     * 
     * @param message 同步消息
     * @return 结果
     */
    public int insertMessage(Message message);

    /**
     * 修改同步消息
     * 
     * @param message 同步消息
     * @return 结果
     */
    public int updateMessage(Message message);

    /**
     * 批量删除同步消息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMessageByIds(String ids);

    /**
     * 删除同步消息信息
     * 
     * @param id 同步消息ID
     * @return 结果
     */
    public int deleteMessageById(Long id);

    /**
     * 用户信息映射
     *
     * @param user 用户
     * @return 用户同步
     */
    public JSONObject getUserMessage(User user);

    /**
     * 机构信息映射
     *
     * @param dept 机构
     * @return 机构同步
     */
    public JSONObject getDeptMessage(Dept dept);
}
