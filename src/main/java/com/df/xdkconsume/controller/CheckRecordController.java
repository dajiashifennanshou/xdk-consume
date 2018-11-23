package com.df.xdkconsume.controller;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;

import javax.validation.Valid;

import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.df.xdkconsume.entity.AccountidParam;
import com.df.xdkconsume.entity.CheckRecodParam;
import com.df.xdkconsume.entity.CheckRecord;
import com.df.xdkconsume.entity.PersonDossier;
import com.df.xdkconsume.entity.ResultData;
import com.df.xdkconsume.service.impl.CheckRecordServiceImpl;
import com.df.xdkconsume.service.impl.PersonDossierServiceImpl;
import com.df.xdkconsume.utils.Constant;
import com.df.xdkconsume.utils.FtpUtil;

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
        String img = getImageStr("d:\\headimg\\"+clientid+accountid+".jpg");
        if(img == null||StringUtils.isEmpty(img)){
           boolean re =  FtpUtil.downloadFtpFile("121.46.26.158","czn_yktfile","YktFile*2018/0801",21,"\\Image\\Person\\","d:\\headimg\\",clientid+accountid+".jpg");
           if(re){
               img = getImageStr("d:\\headimg\\"+clientid+accountid+".jpg");
               if(img == null||StringUtils.isEmpty(img)){
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
    * 图片转字符串
    * @param imgStr
    * @param filename
    * @return
    */
   public boolean generateImage(String imgStr, String filename) {
       if (imgStr == null) {
           return false;
       }
       Base64.Decoder decoder = Base64.getDecoder();
       try {
           // 解密
           byte[] b = decoder.decode(imgStr);
           // 处理数据
           for(int i = 0; i < b.length; ++i) {
               if (b[i] < 0) {
                   b[i] += 256;
               }
           }
           OutputStream out = new FileOutputStream("D:/Systems/"+filename);
           out.write(b);
           out.flush();
           out.close();
           return true;
       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       return false;
       
   }
   
   /**
    * 图片转字符串
    * @param filePath    --->文件路径
    * @return
    */
   public String getImageStr(String filePath) {
       InputStream inputStream = null;
       byte[] data = null;
       try {
           inputStream = new FileInputStream(filePath);
           data = new byte[inputStream.available()];
           inputStream.read(data);
           inputStream.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
       // 加密
       Base64.Encoder encoder = Base64.getEncoder();
       if(data == null){
    	   return null;
       }
       return encoder.encodeToString(data);
   }
   
}

