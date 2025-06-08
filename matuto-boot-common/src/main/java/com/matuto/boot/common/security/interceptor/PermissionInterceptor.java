package com.matuto.boot.common.security.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.aliyun.oss.ServiceException;
import com.matuto.boot.common.security.annotation.Logical;
import com.matuto.boot.common.security.annotation.RequiresPermissions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

/**
 * 权限拦截器
 * @author Matuto
 */
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequiresPermissions requiresPermissions = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
        if (requiresPermissions == null) {
            return true;
        }

        String[] permissions = requiresPermissions.value();
        if (permissions.length == 0) {
            return true;
        }

        List<String> permissionList = Arrays.asList(permissions);
        if (requiresPermissions.logical() == Logical.AND) {
            // 需要所有权限
            for (String permission : permissionList) {
                if (!StpUtil.hasPermission(permission)) {
                    throw new ServiceException("没有权限：" + permission);
                }
            }
        } else {
            // 需要任意一个权限
            boolean hasPermission = false;
            for (String permission : permissionList) {
                if (StpUtil.hasPermission(permission)) {
                    hasPermission = true;
                    break;
                }
            }
            if (!hasPermission) {
                throw new ServiceException("没有权限：" + String.join(",", permissionList));
            }
        }

        return true;
    }
} 