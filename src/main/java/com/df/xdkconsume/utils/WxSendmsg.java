package com.df.xdkconsume.utils;

import com.df.xdkconsume.entity.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * 发送模板消息
 */
public class WxSendmsg {
    /**
     * 发送
     */
    public static void sendWxMsg(PersonDossier user, ClientDossier clientDossier,ComsumeCodeParam param) throws Exception {
        String openid = user.getOpenId();
        String openid1 = user.getOpenId1();
        String openid2 = user.getOpenId2();
        String openid3 = user.getOpenId3();
        String openid4 = user.getOpenId4();
        String resultmap = HttpUtil.post("localhost:9600/xdkWeb/hs/getAccessToken?clientid="+clientDossier.getClientid(),"");
        if(StringUtils.isEmpty(resultmap)){
            System.out.println("没有拿到access_token");
            return;
        }
        HashMap<String,String> re =  GsonUtils.getInstance().j2T(resultmap,new TypeToken<HashMap<String,String>>(){}.getType());
        if(re == null||re.get("token")== null){
            System.out.println("没有拿到access_token**map");
            return;
        }
        String access_token = re.get("token");
        if(!StringUtils.isEmpty(openid)){
            startSendmsg(openid,user.getPdName(),clientDossier,param,access_token,user.getPdCashmoney());
        }
        if(!StringUtils.isEmpty(openid1)){
            startSendmsg(openid1,user.getPdName(),clientDossier,param,access_token,user.getPdCashmoney());
        }
        if(!StringUtils.isEmpty(openid2)){
            startSendmsg(openid2,user.getPdName(),clientDossier,param,access_token,user.getPdCashmoney());
        }
        if(!StringUtils.isEmpty(openid3)){
            startSendmsg(openid3,user.getPdName(),clientDossier,param,access_token,user.getPdCashmoney());
        }
        if(!StringUtils.isEmpty(openid4)){
            startSendmsg(openid4,user.getPdName(),clientDossier,param,access_token,user.getPdCashmoney());
        }
    }
    public static void startSendmsg(String openid,String name,ClientDossier clientDossier,ComsumeCodeParam param,String access_token,double restmoney) throws Exception {
        WxTemplate temp = new WxTemplate();
        temp.setTouser(openid);
        temp.setTopcolor("#009100");
        HashMap<String,TemplateData> m = new HashMap<String,TemplateData>();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
        temp.setTemplate_id("5mkN06o_kfpwyTYVhenO0p6Bo4MMNLr4ZRTE4v4sL2w");
        TemplateData first = new TemplateData();
        first.setColor("#009100");
        first.setValue("您好"+name+"，您有一笔一卡通消费记录");
        m.put("first", first);
        TemplateData keyword1 = new TemplateData();
        keyword1.setColor("#009100");
        keyword1.setValue(param.getSpendDate()+" "+param.getSpendTime());
        m.put("keyword1", keyword1);
        TemplateData keyword2 = new TemplateData();
        keyword2.setColor("#009100");
        keyword2.setValue("扫码消费");
        m.put("keyword2", keyword2);
        TemplateData keyword3 = new TemplateData();
        keyword3.setColor("#009100");
        keyword3.setValue(param.getSpendMoney()+"元");
        m.put("keyword2", keyword3);
        TemplateData keyword4 = new TemplateData();
        keyword4.setColor("#009100");
        keyword4.setValue("交易成功");
        m.put("keyword4", keyword4);
        TemplateData keyword5 = new TemplateData();
        keyword5.setColor("#009100");
        keyword5.setValue(restmoney+"元");
        m.put("keyword5", keyword5);
        TemplateData remark = new TemplateData();
        remark.setColor("#009100");
        remark.setValue("感谢您的使用");
        m.put("remark", remark);
        temp.setData(m);
        String jsonString = GsonUtils.getInstance().o2J(temp);
        HttpUtil.post(url, jsonString);
    }
}
