package com.wx.employment.DAO.impl;

import com.wx.employment.PO.Job;
import com.wx.employment.mapper.JobMapper;
import com.wx.employment.DAO.JobDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wbl
 * @since 2022-06-03
 */
@Service
public class JobDaoImpl extends ServiceImpl<JobMapper, Job> implements JobDao {

}
