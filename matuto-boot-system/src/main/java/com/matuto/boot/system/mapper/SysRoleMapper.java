package com.matuto.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.matuto.boot.system.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色Mapper接口
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 查询角色菜单
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<Long> selectRoleMenus(@Param("roleId") Long roleId);

    /**
     * 删除角色菜单关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteRoleMenus(@Param("roleId") Long roleId);

    /**
     * 新增角色菜单关联
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     * @return 结果
     */
    int insertRoleMenus(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolesByUserId(Long userId);
}