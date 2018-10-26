package com.df.xdkconsume.controller;


import com.df.xdkconsume.entity.*;
import com.df.xdkconsume.service.impl.CheckRecordServiceImpl;
import com.df.xdkconsume.service.impl.PersonDossierServiceImpl;
import com.df.xdkconsume.utils.Constant;
import com.df.xdkconsume.utils.FtpUtil;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 用户考勤机
 * </p>
 *
 * @author df
 * @since 2018-10-19
 */
@RestController
@RequestMapping("/user")
public class CheckRecordController {

    @Autowired
    CheckRecordServiceImpl crService;

    @Autowired
    PersonDossierServiceImpl pdService;

    @RequestMapping(path = "/checkRecode", method = RequestMethod.POST)
    public ResultData userCheckRecode(@RequestBody @Valid CheckRecodParam rqparam, BindingResult result) throws Throwable {
        ResultData data = new ResultData();
        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                sb.append(error.getDefaultMessage() + ";");
            }
            data.setCode(209);
            data.setMsg(sb.toString());
            return data;
        }
        String clientid = rqparam.getClientid();
        String caridid = rqparam.getCardid();
        PersonDossier user = pdService.getUserByCardId(clientid, caridid);
        if (user == null || TextUtils.isEmpty(user.getPdAccountid())) {
            data.setCode(Constant.CODE_PERSON_NO_USER);
            data.setMsg(Constant.MSG_PERSON_NO_USER);
            return data;
        }
        //考勤结果
        CheckRecord record = crService.checkRecode(rqparam,user);
        if (record == null) {
            data.setCode(311);
            data.setMsg("考勤失败");
            return data;
        }
        data.setCode(Constant.CODE_SUCCESS);
        data.setMsg(Constant.MSG_SUCCESS);
        data.setData(record);
        return data;
    }

    /**
     * 用户头像
     * @return
     */
    @RequestMapping(path = "/head/portrait", method = RequestMethod.POST)
    public ResultData getUserHeaPortrait(@RequestBody AccountidParam param){
        String clientid = param.getClientid();
        String accountid = param.getAccountid();
        ResultData data = new ResultData();
        if(StringUtils.isEmpty(clientid)||StringUtils.isEmpty(accountid)){
            data.setCode(Constant.CODE_PARAM_NULL);
            data.setMsg(Constant.MSG_PARAM_NULL);
            return data;
        }
        if(accountid.length() != 10){
            data.setCode(300);
            data.setMsg("账号必须为十位");
            return data;
        }
        byte[] img = getImageByte("d:\\headimg\\"+clientid+accountid+".jpg");
        if(img == null||img.length == 0){
           boolean re =  FtpUtil.downloadFtpFile("121.46.26.158","czn_yktfile","YktFile*2018/0801",21,"\\Image\\Person\\","d:\\headimg\\",clientid+accountid+".jpg");
           if(re){
               img = getImageByte("d:\\headimg\\"+clientid+accountid+".jpg");
               if(img == null||img.length == 0){
                   data.setCode(302);
                   data.setMsg("无此用户头像");
                   data.setData(img);
               }else{
                   data.setCode(Constant.CODE_SUCCESS);
                   data.setMsg(Constant.MSG_SUCCESS);
                   data.setData(img);
               }
           }else{
               data.setCode(302);
               data.setMsg("无此用户头像");
               data.setData(img);
           }
        }else{
            data.setCode(Constant.CODE_SUCCESS);
            data.setMsg(Constant.MSG_SUCCESS);
            data.setData(img);
        }
        return  data;
    }
    /**
     * 将图片文件转为byte数字
     * @param imgFile
     * @return
     */
    public static byte[] getImageByte(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        //String imgFile = "d:\\111.jpg";// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {

        }
        // 返回Base64编码过的字节数组字符串
        return data;
    }
}

