package com.tnkj.common.utils;


import com.alibaba.fastjson.JSONObject;
import com.tnkj.common.utils.spring.SpringUtils;
import com.tnkj.project.syn.message.domain.Message;
import com.tnkj.project.syn.message.mapper.MessageMapper;
import com.tnkj.project.syn.message.service.IMessageService;
import com.tnkj.project.system.config.domain.Config;
import com.tnkj.project.system.config.service.IConfigService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Http接口调用
 * 
 * @author tnkj
 */
@Component("syn")
public class HttpUtils
{
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public void userOrDeptSyn(){
        log.info("#######HttpUtils###userOrDeptSyn###start");
        Message message =new Message();
        List<Message> list = SpringUtils.getBean(IMessageService.class).selectMessageList(message);
        if(list!=null && !list.isEmpty()){
            for(int i=0;i<list.size();i++){
                Message tempMes=list.get(i);
                if(StringUtils.isNotEmpty(tempMes.getSynStatus()) && !"同步成功".equals(tempMes.getSynStatus())){
                    try {
                        String respContent = null;
                        String url=getUrl(tempMes.getSystem(),tempMes.getOprTable());
                        if(StringUtils.isEmpty(url)){
                            tempMes.setSynStatus("同步失败");
                            tempMes.setErrCause(tempMes.getSystem()+"-"+tempMes.getOprTable()+"接口连接地址为空，请检查");
                            tempMes.setUpdateTime(DateUtils.getNowDate());
                            SpringUtils.getBean(MessageMapper.class).updateMessage(tempMes);
                            continue;
                        }
                        HttpPost httpPost = new HttpPost(url);
                        CloseableHttpClient client = HttpClients.createDefault();
                        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000)
                                .setConnectionRequestTimeout(5000).setStaleConnectionCheckEnabled(true).build();
                        StringEntity entity = new StringEntity(tempMes.getMessage(),"UTF-8");
                        entity.setContentEncoding("UTF-8");
                        entity.setContentType("application/json");
                        httpPost.setEntity(entity);
                        httpPost.setConfig(requestConfig);
                        HttpResponse resp = client.execute(httpPost);
                        if (resp != null && resp.getStatusLine().getStatusCode() == 200) {
                            HttpEntity he = resp.getEntity();
                            respContent = EntityUtils.toString(he, "UTF-8");
                        }else{
                            tempMes.setSynStatus("同步失败");
                            tempMes.setErrCause("接口连接失败，非200，请检查接口地址是否正确");
                            tempMes.setUpdateTime(DateUtils.getNowDate());
                            SpringUtils.getBean(MessageMapper.class).updateMessage(tempMes);
                            continue;
                        }
                        if(StringUtils.isNotEmpty(respContent)){
                            log.info("#######HttpUtils.userOrDeptSyn-response_status:"+resp.getStatusLine().getStatusCode());
                            log.info("#######HttpUtils-userOrDeptSyn-end-httpResponse-" + resp + "-status-"
                                    + resp.getStatusLine().getStatusCode()+"######respContent######"+respContent);
                            JSONObject obj=JSONObject.parseObject(respContent);
                            if(obj.containsKey("code") && "0".equals(obj.getString("code"))){
                                tempMes.setSynStatus("同步成功");
                                tempMes.setErrCause("同步成功");
                            }else{
                                tempMes.setSynStatus("同步失败");
                                if(obj.containsKey("msg") && StringUtils.isNotEmpty(obj.getString("msg"))){
                                    tempMes.setErrCause(obj.getString("msg"));
                                }
                            }

                        }else{
                            tempMes.setSynStatus("同步失败");
                            tempMes.setErrCause("接口返回参数为空，请检查");
                        }
                    }catch (Exception e) {
                        tempMes.setSynStatus("同步失败");
                        tempMes.setErrCause(e.getMessage());
                        log.error("######HttpUtils###httpPostData###faild###", e);
                    }
                    tempMes.setUpdateTime(DateUtils.getNowDate());
                    SpringUtils.getBean(MessageMapper.class).updateMessage(tempMes);
                }
            }
        }
        log.info("#######HttpUtils###userOrDeptSyn###end");
    }

    public String getUrl(String system,String table){
        if(StringUtils.isNotEmpty(table)){
            String configKey=null;
            if("sys_user".equals(table)){
                configKey="sys."+system+".synUser";
            }else if("sys_dept".equals(table)){
                configKey="sys."+system+".synDept";
            }
            if(StringUtils.isNotEmpty(configKey)){
                Config config=SpringUtils.getBean(IConfigService.class).getConfigBykey(configKey);
                if(config!=null && StringUtils.isNotEmpty(config.getConfigValue())){
                    return config.getConfigValue();
                }
            }
        }
        return null;
    }
}
