package cn.gson.crm.controller.system;

import cn.gson.crm.common.ApiResult;
import cn.gson.crm.common.ApiResultEnum;
import cn.gson.crm.model.domain.User;
import cn.gson.crm.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Resource
    private RoleService roleService;


    @PostMapping
    public ApiResult createRole(@RequestAttribute("role") String role) {
        roleService.createRole(role);
        return ApiResult.success();
    }

    @DeleteMapping
    public ApiResult deleteRole(@RequestAttribute("role") String role) {
        roleService.delete(role);
        return ApiResult.success();
    }

    @PostMapping("/add")
    public ApiResult addRole2User(@RequestAttribute("role") String role, @RequestAttribute("userId") String userId) {
        if (!roleService.exist(role)) {
            return ApiResult.failure(ApiResultEnum.ROLE_NOT_FOUND);
        }
        roleService.addRole2User(role, userId);
        return ApiResult.success();
    }


}
