package com.df.xdkconsume.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.df.xdkconsume.entity.*;
import com.df.xdkconsume.mapper.CheckRecordMapper;
import com.df.xdkconsume.mapper.LeaveRecordMapper;
import com.df.xdkconsume.mapper.RoutineTimeMapper;
import com.df.xdkconsume.service.CheckRecordService;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * 用户考勤服务实现类
 * </p>
 *
 * @author df
 * @since 2018-10-19
 */
@Service
public class CheckRecordServiceImpl extends ServiceImpl<CheckRecordMapper, CheckRecord> implements CheckRecordService {
    @Autowired
    CheckRecordMapper ckMapper;

    @Autowired
    RoutineTimeMapper rtMapper;

    @Autowired
    LeaveRecordMapper lrMapper;

    @Autowired
    PersonDossierServiceImpl pdservice;

    /**
     * 用户考勤，生成记录，返回记录
     *
     * @param rqparam
     * @param user
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public CheckRecord checkRecode(CheckRecodParam rqparam, PersonDossier user) throws Throwable {
        CheckRecord record = null;
        RoutineTime sqrt = new RoutineTime();
        sqrt.setClientid(user.getClientid());
        sqrt.setRtPdstatus(user.getPdStatus());
        RoutineTime routineTime = rtMapper.selectOne(sqrt);
        record = new CheckRecord();
        record.setClientid(user.getClientid());
        record.setCrCheckmac(rqparam.getCheckmac());
        record.setCrChecktype(Integer.valueOf(rqparam.getChecktype()));
        record.setCrDate(rqparam.getDate());
        record.setCrTime(rqparam.getTime());
        record.setCrDepartment(user.getPdDepartment());
        record.setCrMactype(Integer.valueOf(rqparam.getMactype()));
        record.setCrStyle(Integer.valueOf(rqparam.getStyle()));
        record.setCrPdname(user.getPdName());
        record.setCrPdid(user.getPdId());
        record.setCrPdaccountid(user.getPdAccountid());
        if (routineTime != null) {
            String daywhat = dayForWeek(rqparam.getDate());
            switch (daywhat) {
                case "1":
                    if (routineTime.getRtWeek1() == 1) {
                        record = whereCheck(record, routineTime, user, rqparam);
                    } else {
                        record = ensureCheckLV(record, routineTime, user, rqparam);
                    }
                    break;
                case "2":
                    if (routineTime.getRtWeek2() == 1) {
                        record = whereCheck(record, routineTime, user, rqparam);
                    } else {
                        record = ensureCheckLV(record, routineTime, user, rqparam);
                    }
                    break;
                case "3":
                    if (routineTime.getRtWeek3() == 1) {
                        record = whereCheck(record, routineTime, user, rqparam);
                    } else {
                        record = ensureCheckLV(record, routineTime, user, rqparam);
                    }
                    break;
                case "4":
                    if (routineTime.getRtWeek4() == 1) {
                        record = whereCheck(record, routineTime, user, rqparam);
                    } else {
                        record = ensureCheckLV(record, routineTime, user, rqparam);
                    }
                    break;
                case "5":
                    if (routineTime.getRtWeek5() == 1) {
                        record = whereCheck(record, routineTime, user, rqparam);
                    } else {
                        record = ensureCheckLV(record, routineTime, user, rqparam);
                    }
                    break;
                case "6":
                    if (routineTime.getRtWeek6() == 1) {
                        record = whereCheck(record, routineTime, user, rqparam);
                    } else {
                        record = ensureCheckLV(record, routineTime, user, rqparam);
                    }
                    break;
                case "7":
                    if (routineTime.getRtWeek7() == 1) {
                        record = whereCheck(record, routineTime, user, rqparam);
                    } else {
                        record = ensureCheckLV(record, routineTime, user, rqparam);
                    }
                    break;
            }
        }else{
            return  null;
        }
        if (ckMapper.insert(record) > 0) {
            switch (rqparam.getChecktype()) {
                case "0":
                    if (rqparam.getStyle().equals("1")) {
                        pdservice.updateUserSchool(user, "在校", rqparam.getDate() + " " + rqparam.getTime());
                    } else {
                        pdservice.updateUserSchool(user, "离校", rqparam.getDate() + " " + rqparam.getTime());
                    }
                    break;
                case "1":
                    if (rqparam.getStyle().equals("1")) {
                        pdservice.updateUserApartment(user, "已归寝", rqparam.getDate() + " " + rqparam.getTime());
                    } else {
                        pdservice.updateUserApartment(user, "未归寝", rqparam.getDate() + " " + rqparam.getTime());
                    }
                    break;
            }
            return record;
        } else {
            return null;
        }
    }

    /**
     * 判断是什么类型的，学校，班级，公寓
     *
     * @return
     */
    public CheckRecord whereCheck(CheckRecord record, RoutineTime routineTime, PersonDossier user, CheckRecodParam rqparam) throws ParseException {
        switch (rqparam.getChecktype()) {
            case "0":
                if (routineTime.getRtSchool() == 1) {
                    record = ensureCheck(record, routineTime, user, rqparam);
                } else {
                    record = ensureCheckLV(record, routineTime, user, rqparam);
                }
                break;
            case "1":
                if (routineTime.getRtApartment() == 1) {
                    record = ensureCheck(record, routineTime, user, rqparam);
                } else {
                    record = ensureCheckLV(record, routineTime, user, rqparam);
                }
                break;
            case "2":
                if (routineTime.getRtClass() == 1) {
                    record = ensureCheck(record, routineTime, user, rqparam);
                } else {
                    record = ensureCheckLV(record, routineTime, user, rqparam);
                }
                break;
        }
        return record;
    }

    /**
     * 判断是无权限卡还是正常进出
     *
     * @return
     */
    public CheckRecord ensureCheck(CheckRecord record, RoutineTime routineTime, PersonDossier user, CheckRecodParam rqparam) throws ParseException {
        String format = "yyyy-MM-dd HH:mm:ss";
        Date nowTime = new SimpleDateFormat(format).parse(rqparam.getDate() + " " + rqparam.getTime());//当前时间
        Date startTime = new SimpleDateFormat(format).parse(routineTime.getRtStartdate() + " " + routineTime.getRtStarttime() + ":00");//开始时间
        Date endTime = new SimpleDateFormat(format).parse(routineTime.getRtEnddate() + " " + routineTime.getRtEndtime()+ ":00");//结束时间
        if (isEffectiveDate(nowTime, startTime, endTime)) {
            record.setCrEnabled("作息时段");
            record.setCrRoutine(routineTime.getRtName() + " " + routineTime.getRtStarttime() + "至" + routineTime.getRtEndtime());
            record.setCrLeave("");
        } else {
            record = ensureCheckLV(record, routineTime, user, rqparam);
        }
        return record;
    }

    /**
     * 判断是不是请假
     *
     * @return
     */
    public CheckRecord ensureCheckLV(CheckRecord record, RoutineTime routineTime, PersonDossier user, CheckRecodParam rqparam) throws ParseException {
        LeaveRecord lr = lrMapper.getLeaveRecodeIntime(user.getClientid(), user.getPdAccountid(), rqparam.getDate(), rqparam.getTime());
        if (lr == null || TextUtils.isBlank(lr.getLrLeavetype())) {
            record.setCrEnabled("无权限卡");
            record.setCrRoutine("");
            record.setCrLeave("");
        } else {
            record.setCrEnabled("请假时段");
            record.setCrRoutine("");
            record.setCrLeave(lrMapper.getLeaveType(user.getClientid(), lr.getLrLeavetype()) + " " + lr.getLrStartdate() + lr.getLrStarttime() + "至" + lr.getLrEnddate() + lr.getLrEndtime());
        }
        return record;
    }

    /**
     * 判断星期几
     *
     * @param pTime
     * @return
     * @throws Throwable
     */
    public String dayForWeek(String pTime) throws Throwable {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date tmpDate = format.parse(pTime);
        Calendar cal = Calendar.getInstance();
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        try {
            cal.setTime(tmpDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     *      * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *      * 
     *      * @param nowTime 当前时间
     *      * @param startTime 开始时间
     *      * @param endTime 结束时间
     *      * @return
     *      * @author jqlin
     *      
     */
    public boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}
