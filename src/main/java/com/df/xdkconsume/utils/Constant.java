package com.df.xdkconsume.utils;
/**
 * 
 * @author 段帆
 * 2018年8月2日下午2:10:14
 */
public interface Constant {
	String SIGN_KEY = "cinzn2055";
    int CODE_PERSON_NO_USER = 201;
    String MSG_PERSON_NO_MONEY = "余额不足";//用户现金+补助余额不足user have no money
    int CODE_PERSON_NO_MOENY = 202;
    String MSG_PERSON_NO_USER = "无此用户";//没有找到此用户user not find by cardid
    int CODE_COMSUME_INSERT_WRONG = 204;
    String MSG_COMSUME_INSERT_WRONG = "消费失败";//消费失败comsume insert fail
    int CODE_PERSON_UPDATE_WRONG = 203;
    String MSG_PERSON_UPDATE_WRONG = "消费失败";//余额更新失败user update money wrong
    int CODE_PERSON_LOST = 206;
    String MSG_PERSON_LOST = "卡已挂失";//该卡已挂失user card lost
    int CODE_PERSON_MOENY_FU = 207;
    String MSG_PERSON_MOENY_FU = "余额不足";//用户现金或者补助余额小于0user money < 0
    int CODE_SUCCESS = 200;
    String MSG_SUCCESS = "success";//成功
    int CODE_PARAM_NULL = 205;
    String MSG_PARAM_NULL = "参数错误";//参数错误，参数不能为空param cant be null or empty
    int CODE_LOST_CARD = 301;
    String MSG_LOST_CARD = "挂失失败";//挂失卡
    int CODE_SIGN_WRONG = 302;
    String MSG_SIGN_WRONG = "注册失败";//挂失卡
}
