package com.matuto.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.matuto.boot.common.constant.UserConstants;
import com.matuto.boot.common.exception.ServiceException;
import com.matuto.boot.system.entity.SysRole;
import com.matuto.boot.system.mapper.SysRoleMapper;
import com.matuto.boot.system.mapper.SysUserRoleMapper;
import com.matuto.boot.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 角色服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapper roleMapper;

    private final SysUserRoleMapper userRoleMapper;

    @Override
    public Page<SysRole> getRoleList(SysRole role, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(role.getName()), SysRole::getName, role.getName())
                .like(StringUtils.hasText(role.getCode()), SysRole::getCode, role.getCode())
                .eq(StringUtils.hasText(String.valueOf(role.getStatus())), SysRole::getStatus, role.getStatus());
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public List<SysRole> getAllRoles() {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getStatus, UserConstants.NORMAL);
        return list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(SysRole role) {
        // 检查角色名称是否唯一
        if (checkRoleNameUnique(role)) {
            throw new ServiceException("新增角色'" + role.getName() + "'失败，角色名称已存在");
        }
        // 检查角色权限是否唯一
        if (checkRoleCodeUnique(role)) {
            throw new ServiceException("新增角色'" + role.getName() + "'失败，角色权限已存在");
        }
        // 设置默认值
        role.setStatus(Integer.valueOf(UserConstants.NORMAL));
        // 保存角色
        save(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(SysRole role) {
        // 检查角色是否存在
        SysRole existRole = getById(role.getId());
        if (existRole == null) {
            throw new ServiceException("修改角色不存在");
        }
        // 检查角色名称是否唯一
        if (checkRoleNameUnique(role)) {
            throw new ServiceException("修改角色'" + role.getName() + "'失败，角色名称已存在");
        }
        // 检查角色权限是否唯一
        if (checkRoleCodeUnique(role)) {
            throw new ServiceException("修改角色'" + role.getName() + "'失败，角色权限已存在");
        }
        // 更新角色
        updateById(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long roleId) {
        // 检查角色是否存在
        SysRole role = getById(roleId);
        if (role == null) {
            throw new ServiceException("删除角色不存在");
        }
        // 检查是否为管理员角色
        if (UserConstants.ADMIN_ROLE_ID.equals(roleId)) {
            throw new ServiceException("不允许删除超级管理员角色");
        }
        // 删除角色
        removeById(roleId);
    }

    @Override
    public List<Long> getRoleMenus(Long roleId) {
        return roleMapper.selectRoleMenus(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleMenus(Long roleId, List<Long> menuIds) {
        // 检查角色是否存在
        SysRole role = getById(roleId);
        if (role == null) {
            throw new ServiceException("角色不存在");
        }
        // 更新角色菜单
        roleMapper.deleteRoleMenus(roleId);
        if (menuIds != null && !menuIds.isEmpty()) {
            roleMapper.insertRoleMenus(roleId, menuIds);
        }
    }

    @Override
    public boolean checkRoleNameUnique(SysRole role) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getName, role.getId())
                .ne(role.getId() != null, SysRole::getId, role.getId());
        return count(wrapper) > 0;
    }

    @Override
    public boolean checkRoleCodeUnique(SysRole role) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getCode, role.getCode())
                .ne(role.getId() != null, SysRole::getId, role.getId());
        return count(wrapper) > 0;
    }

    @Override
    public SysRole selectRoleById(Long roleId) {
        return baseMapper.selectById(roleId);
    }

    @Override
    public void checkRoleAllowed(SysRole role) {
        // TODO: 实现角色操作权限检查
    }

    @Override
    public int countUserRoleByRoleId(Long roleId) {
        // TODO: 实现角色使用数量统计
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRole(SysRole role) {
        return baseMapper.insert(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRoleStatus(SysRole role) {
        return baseMapper.updateById(role);
    }

    @Override
    public int authDataScope(SysRole role) {
        return baseMapper.updateById(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleById(Long roleId) {
        return baseMapper.deleteById(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleByIds(Long[] roleIds) {
        // TODO 实现角色删除
        return 0;
    }

    @Override
    public List<Long> selectRoleListByUserId(Long userId) {
        return userRoleMapper.selectRoleListByUserId(userId);
    }
} 