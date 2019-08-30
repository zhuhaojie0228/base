package com.tnkj.project.syn.message.mapper;

import com.tnkj.project.syn.message.domain.Message;
import java.util.List;

/**
 * 同步消息Mapper接口
 * 
 * @author tnkj
 * @date 2019-08-30
 */
public interface MessageMapper 
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
     * 删除同步消息
     * 
     * @param id 同步消息ID
     * @return 结果
     */
    public int deleteMessageById(Long id);

    /**
     * 批量删除同步消息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMessageByIds(String[] ids);
}
