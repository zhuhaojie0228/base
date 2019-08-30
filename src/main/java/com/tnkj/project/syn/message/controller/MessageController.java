package com.tnkj.project.syn.message.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tnkj.framework.aspectj.lang.annotation.Log;
import com.tnkj.framework.aspectj.lang.enums.BusinessType;
import com.tnkj.project.syn.message.domain.Message;
import com.tnkj.project.syn.message.service.IMessageService;
import com.tnkj.framework.web.controller.BaseController;
import com.tnkj.framework.web.domain.AjaxResult;
import com.tnkj.common.utils.poi.ExcelUtil;
import com.tnkj.framework.web.page.TableDataInfo;

/**
 * 同步消息Controller
 * 
 * @author tnkj
 * @date 2019-08-30
 */
@Controller
@RequestMapping("/syn/message")
public class MessageController extends BaseController
{
    private String prefix = "syn/message";

    @Autowired
    private IMessageService messageService;

    @RequiresPermissions("syn:message:view")
    @GetMapping()
    public String message()
    {
        return prefix + "/message";
    }

    /**
     * 查询同步消息列表
     */
    @RequiresPermissions("syn:message:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Message message)
    {
        startPage();
        List<Message> list = messageService.selectMessageList(message);
        return getDataTable(list);
    }

    /**
     * 导出同步消息列表
     */
    @RequiresPermissions("syn:message:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Message message)
    {
        List<Message> list = messageService.selectMessageList(message);
        ExcelUtil<Message> util = new ExcelUtil<Message>(Message.class);
        return util.exportExcel(list, "message");
    }

    /**
     * 新增同步消息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存同步消息
     */
    @RequiresPermissions("syn:message:add")
    @Log(title = "同步消息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Message message)
    {
        return toAjax(messageService.insertMessage(message));
    }

    /**
     * 修改同步消息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Message message = messageService.selectMessageById(id);
        mmap.put("message", message);
        return prefix + "/edit";
    }

    /**
     * 修改保存同步消息
     */
    @RequiresPermissions("syn:message:edit")
    @Log(title = "同步消息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Message message)
    {
        return toAjax(messageService.updateMessage(message));
    }

    /**
     * 删除同步消息
     */
    @RequiresPermissions("syn:message:remove")
    @Log(title = "同步消息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(messageService.deleteMessageByIds(ids));
    }
}
