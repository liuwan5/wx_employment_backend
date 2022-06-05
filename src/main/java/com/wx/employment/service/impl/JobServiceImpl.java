package com.wx.employment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.employment.DAO.JobDao;
import com.wx.employment.PO.Job;
import com.wx.employment.PO.User;
import com.wx.employment.VO.param.JobParam;
import com.wx.employment.VO.param.JobsConditionParam;
import com.wx.employment.VO.param.PageParam;
import com.wx.employment.common.Result;
import com.wx.employment.service.JobService;
import com.wx.employment.util.DistanceUtil;
import com.wx.employment.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author wbl
 */
@Slf4j
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    JobDao jobDao;

    @Override
    public Result<IPage<Job>> getJobsPageByCondition(JobsConditionParam param, HttpServletRequest request) {
        try {
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            LambdaQueryWrapper<Job> jobQuery = new LambdaQueryWrapper<>();
            // 工作名字关键字查找
            if (param.getKeyword() != null) {
                jobQuery.like(Job::getName, "%"+param.getKeyword()+"%");
            }
            if (param.getSalaryType() != -1) {
                jobQuery.eq(Job::getSalaryType, param.getSalaryType());
                jobQuery.ge(Job::getSalary, param.getSalaryStart());
                jobQuery.le(Job::getSalary, param.getSalaryEnd());
            }
            if (param.getDurationType() != -1) {
                jobQuery.eq(Job::getDurationType, param.getDurationType());
                jobQuery.ge(Job::getDuration, param.getDurationStart());
                jobQuery.le(Job::getDuration, param.getDurationEnd());
            }
            if (param.getLongitude() != null && param.getLatitude() != null) {
                double[] range = DistanceUtil.getRange(param.getDistance(), param.getLongitude().doubleValue(), param.getLatitude().doubleValue());
                if(range == null){
                    return Result.executeFailure("地址异常");
                }
                jobQuery.ge(Job::getLongitude, range[0]);
                jobQuery.le(Job::getLongitude, range[1]);
                jobQuery.ge(Job::getLatitude, range[2]);
                jobQuery.le(Job::getLatitude, range[3]);
            }
            // 不查询自己发布的job
            jobQuery.ne(Job::getCreatedUserId, nowUser.getUid());

            Page<Job> producePage = new Page<>(param.getCurrentPage(), param.getPageSize());
            // 根据条件查询
            IPage<Job> page = jobDao.page(producePage, jobQuery);
            return Result.getSuccess(page);
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.getFailure();
        }
    }

    @Override
    public Result<IPage<Job>> getJobsByUid(PageParam param, HttpServletRequest request) {
        try {
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            LambdaQueryWrapper<Job> jobQuery = new LambdaQueryWrapper<>();
            jobQuery.eq(Job::getCreatedUserId, nowUser.getUid());
            Page<Job> producePage = new Page<>(param.getCurrentPage(), param.getPageSize());
            IPage<Job> page = jobDao.page(producePage, jobQuery);
            return Result.getSuccess(page);
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.getFailure();
        }
    }

    @Override
    public Result<Job> getJobsByJid(int jid, HttpServletRequest request) {
        try {
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            Job job = jobDao.getById(jid);
            return Result.getSuccess(job);
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.getFailure();
        }
    }

    @Override
    public Result addJob(JobParam jobParam, HttpServletRequest request){
        try {
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            Job job = new Job();
            job.setName(jobParam.getName())
                    .setHeadcount(jobParam.getHeadcount())
                    .setDuration(jobParam.getDuration())
                    .setDurationType(jobParam.getDurationType())
                    .setSalary(jobParam.getSalary())
                    .setSalaryType(jobParam.getSalaryType())
                    .setLocation(jobParam.getLocation())
                    .setLongitude(jobParam.getLongitude())
                    .setLatitude(jobParam.getLatitude())
                    .setDescription(jobParam.getDescription())
                    .setCreatedTime(LocalDateTime.now())
                    .setCreatedUserId(nowUser.getUid())
                    .setDeliveredResumeCount(0);
            jobDao.save(job);
            return Result.insertSuccess();
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.insertFailure();
        }
    }

    @Override
    public Result delJob(int jid, HttpServletRequest request) {
        try {
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            jobDao.remove(new QueryWrapper<Job>().lambda().eq(Job::getJid, jid));
            return Result.delSuccess();
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.delFailure();
        }
    }

    @Override
    public Result updJob(Job job, HttpServletRequest request) {
        try {
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            LambdaUpdateWrapper<Job> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Job::getJid, job.getJid());
            jobDao.update(job, lambdaUpdateWrapper);
            return Result.updSuccess();
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.updFailure();
        }
    }
}
