package com.matuto.boot.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.matuto.boot.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import static com.baomidou.mybatisplus.extension.toolkit.Db.list;

@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    default List<Long> selectRoleListByUserId(Long userId) {
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(SysUserRole::getRoleId);
        wrapper.eq(SysUserRole::getUserId, userId);
        return list(wrapper).stream().map(SysUserRole::getRoleId).toList();
    }
}