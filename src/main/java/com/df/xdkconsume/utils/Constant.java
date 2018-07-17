package com.df.xdkconsume.utils;

public interface Constant {
	String SIGN_KEY = "cinzn2055";
    int CODE_PERSON_NO_USER = 201;
    String MSG_PERSON_NO_MONEY = "user have no money";
    int CODE_PERSON_NO_MOENY = 202;
    String MSG_PERSON_NO_USER = "user not find by cardid";
    int CODE_COMSUME_INSERT_WRONG = 204;
    String MSG_COMSUME_INSERT_WRONG = "comsume insert fail";
    int CODE_PERSON_UPDATE_WRONG = 203;
    String MSG_PERSON_UPDATE_WRONG = "user update money wrong";
    int CODE_PERSON_LOST = 206;
    String MSG_PERSON_LOST = "user card lost";
    int CODE_PERSON_MOENY_FU = 207;
    String MSG_PERSON_MOENY_FU = "user money < 0";
    int CODE_SUCCESS = 200;
    String MSG_SUCCESS = "success";
    int CODE_PARAM_NULL = 205;
    String MSG_PARAM_NULL = "param cant be null or empty";
}
