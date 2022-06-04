package com.wx.employment.DAO.impl;

import com.wx.employment.PO.User;
import com.wx.employment.mapper.UserMapper;
import com.wx.employment.DAO.UserDao;
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
public class UserDaoImpl extends ServiceImpl<UserMapper, User> implements UserDao {

}
