package com.matuto.boot.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.matuto.boot.system.entity.SysUser;

import java.util.List;

/**
 * 用户服务接口
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 获取用户列表
     *
     * @param user     用户信息
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 用户列表
     */
    Page<SysUser> getUserList(SysUser user, Integer pageNum, Integer pageSize);

    /**
     * 新增用户
     *
     * @param user 用户信息
     */
    void addUser(SysUser user);

    /**
     * 修改用户
     *
     * @param user 用户信息
     */
    void updateUser(SysUser user);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    void deleteUser(Long userId);

    /**
     * 获取用户角色
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> getUserRoles(Long userId);

    /**
     * 更新用户角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     */
    void updateUserRoles(Long userId, List<Long> roleIds);

    /**
     * 根据用户名查询用户
     *
     * @param userName 用户名
     * @return 用户信息
     */
    SysUser selectUserByUserName(String userName);
} 