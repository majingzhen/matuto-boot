package com.matuto.boot.system.service.impl;

import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.matuto.boot.common.utils.PasswordUtils;
import com.matuto.boot.common.utils.SecurityUtils;
import com.matuto.boot.system.entity.SysUser;
import com.matuto.boot.system.mapper.SysUserMapper;
import com.matuto.boot.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Override
    public Page<SysUser> getUserList(SysUser user, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(user.getUserName()), SysUser::getUserName, user.getUserName())
                .like(StringUtils.hasText(user.getNickName()), SysUser::getNickName, user.getNickName())
                .like(StringUtils.hasText(user.getPhone()), SysUser::getPhone, user.getPhone())
                .eq(StringUtils.hasText(user.getStatus()), SysUser::getStatus, user.getStatus())
                .eq(StringUtils.hasText(user.getUserType()), SysUser::getUserType, user.getUserType())
                .eq(SysUser::getDelFlag, "0")
                .orderByDesc(SysUser::getCreateTime);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(SysUser user) {
        // 检查用户名是否唯一
        if (checkUserNameUnique(user)) {
            throw new ServiceException("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        // 检查手机号是否唯一
        if (checkPhoneUnique(user)) {
            throw new ServiceException("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        // 检查邮箱是否唯一
        if (checkEmailUnique(user)) {
            throw new ServiceException("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        // 生成盐值
        String salt = PasswordUtils.generateSalt();
        user.setSalt(salt);
        // 加密密码
        user.setPassword(PasswordUtils.encrypt(user.getPassword(), salt));
        // 设置默认值
        user.setDelFlag("0");
        user.setStatus("0");
        // 保存用户
        save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SysUser user) {
        // 检查用户是否存在
        SysUser existUser = getById(user.getId());
        if (existUser == null) {
            throw new ServiceException("修改用户不存在");
        }
        // 检查用户名是否唯一
        if (checkUserNameUnique(user)) {
            throw new ServiceException("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        // 检查手机号是否唯一
        if (checkPhoneUnique(user)) {
            throw new ServiceException("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        // 检查邮箱是否唯一
        if (checkEmailUnique(user)) {
            throw new ServiceException("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        // 如果修改了密码，需要重新加密
        if (StringUtils.hasText(user.getPassword())) {
            String salt = PasswordUtils.generateSalt();
            user.setSalt(salt);
            user.setPassword(PasswordUtils.encrypt(user.getPassword(), salt));
        }
        // 更新用户
        updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId) {
        // 检查用户是否存在
        SysUser user = getById(userId);
        if (user == null) {
            throw new ServiceException("删除用户不存在");
        }
        // 检查是否为管理员
        if (SecurityUtils.isAdmin(userId)) {
            throw new ServiceException("不允许删除超级管理员用户");
        }
        // 删除用户
        removeById(userId);
    }

    @Override
    public List<Long> getUserRoles(Long userId) {
        return baseMapper.selectUserRoles(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserRoles(Long userId, List<Long> roleIds) {
        // 检查用户是否存在
        SysUser user = getById(userId);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        // 更新用户角色
        baseMapper.deleteUserRoles(userId);
        if (roleIds != null && !roleIds.isEmpty()) {
            baseMapper.insertUserRoles(userId, roleIds);
        }
    }

    @Override
    public SysUser selectUserByUserName(String userName) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName, userName)
                .eq(SysUser::getDelFlag, "0");
        return getOne(wrapper);
    }

    /**
     * 检查用户名是否唯一
     */
    private boolean checkUserNameUnique(SysUser user) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName, user.getUserName())
                .ne(user.getId() != null, SysUser::getId, user.getId());
        return count(wrapper) > 0;
    }

    /**
     * 检查手机号是否唯一
     */
    private boolean checkPhoneUnique(SysUser user) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getPhone, user.getPhone())
                .ne(user.getId() != null, SysUser::getId, user.getId());
        return count(wrapper) > 0;
    }

    /**
     * 检查邮箱是否唯一
     */
    private boolean checkEmailUnique(SysUser user) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getEmail, user.getEmail())
                .ne(user.getId() != null, SysUser::getId, user.getId());
        return count(wrapper) > 0;
    }
} 