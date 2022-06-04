package com.wx.employment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wx.employment.DAO.DeliveredResumeDao;
import com.wx.employment.PO.DeliveredResume;
import com.wx.employment.PO.Job;
import com.wx.employment.PO.Resume;
import com.wx.employment.PO.User;
import com.wx.employment.VO.param.ResumeParam;
import com.wx.employment.common.Result;
import com.wx.employment.service.ResumeDeliveredService;
import com.wx.employment.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wbl
 */
@Slf4j
@Service
public class ResumeDeliveredServiceImpl implements ResumeDeliveredService {

    @Autowired
    DeliveredResumeDao deliveredResumeDao;

    @Override
    public Result<DeliveredResume> getDeliveredResumeByDrid(int drid, HttpServletRequest request) {
        try {
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            DeliveredResume deliveredResume = deliveredResumeDao.getById(drid);
            return Result.getSuccess(deliveredResume);
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.getFailure();
        }
    }

    @Override
    public Integer addResume(Resume resume, HttpServletRequest request) {
        try{
            DeliveredResume deliveredResume = new DeliveredResume();
            BeanUtils.copyProperties(resume, deliveredResume);
            deliveredResumeDao.save(deliveredResume);
            return deliveredResume.getDrid();
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return 0;
        }
    }
}
