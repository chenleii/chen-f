package com.chen.f.admin.configuration;


import com.chen.f.admin.web.controller.OnlineController;
import com.chen.f.admin.web.controller.SysApiController;
import com.chen.f.admin.web.controller.SysDictionaryController;
import com.chen.f.admin.web.controller.SysDictionaryItemController;
import com.chen.f.admin.web.controller.SysMenuController;
import com.chen.f.admin.web.controller.SysOrganizationController;
import com.chen.f.admin.web.controller.SysParameterController;
import com.chen.f.admin.web.controller.SysPermissionController;
import com.chen.f.admin.web.controller.SysRoleController;
import com.chen.f.admin.web.controller.SysTimedTaskController;
import com.chen.f.admin.web.controller.SysTimedTaskLogController;
import com.chen.f.admin.web.controller.SysUserController;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author chen
 * @since 2019/1/14 22:55.
 */
public class EnableChenFAdminConfigurationImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                
                OnlineController.class.getName(),
                SysApiController.class.getName(),
                SysDictionaryController.class.getName(),
                SysDictionaryItemController.class.getName(),
                SysMenuController.class.getName(),
                SysOrganizationController.class.getName(),
                SysParameterController.class.getName(),
                SysPermissionController.class.getName(),
                SysRoleController.class.getName(),
                SysTimedTaskController.class.getName(),
                SysTimedTaskLogController.class.getName(),
                SysUserController.class.getName(),

        };
    }
}
