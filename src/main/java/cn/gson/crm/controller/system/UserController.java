package cn.gson.crm.controller.system;

import cn.gson.crm.common.ApiResult;
import cn.gson.crm.common.ApiResultEnum;
import cn.gson.crm.model.domain.User;
import cn.gson.crm.service.RoleService;
import cn.gson.crm.service.UserService;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 用户管理控制器
 *
 * @author gson
 */
@RequestMapping("/api/user")
@RestController
public class UserController {

    public static final String TOKEN = "token";
    public static final String USER = "user";
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @PostMapping("/login")
    @RequestMapping
    public ApiResult login(String userName, String password, HttpSession session) {
        User user = userService.findByUserName(userName);
        logger.info("find user: {}", user);
        if (user == null) {
            return ApiResult.failure("401", "user is not found");
        }
        String shaPwd = PasswordUtil.encode(password);
        if (shaPwd != user.getPassword()) {
            logger.info("password is wrong");
            return ApiResult.failure("401", "password is wrong");
        }
        String token = String.join("@", Long.toString(System.currentTimeMillis()), UUID.randomUUID().toString());
        session.setAttribute(TOKEN, token);
        session.setAttribute(USER, user);
        return ApiResult.success(token);
    }

    @PostMapping
    public ApiResult createUser(@RequestBody User user) {
        userService.createUser(user);
        return ApiResult.success();
    }

    @DeleteMapping("/{userId}")
    public ApiResult delete(@PathVariable("userId") String userId) {
        logger.info("delete user {}", userId);
        User user = userService.findById(userId);
        if (user == null) {
            return ApiResult.failure("401", "user is not found");
        }
        userService.deleteUser(userId);
        return ApiResult.success();
    }


    @PostMapping("/invalidate")
    public ApiResult invalidate(String token, HttpSession session) {
        //处理好非法入参
        session.removeAttribute(TOKEN);
        session.invalidate();
        return ApiResult.success();
    }

    @GetMapping("/check/role")
    public ApiResult checkRole(String role, HttpSession session) {
        User user = (User) session.getAttribute(USER);
        if (!roleService.exist(role)) {
            return ApiResult.failure(ApiResultEnum.ROLE_NOT_FOUND);
        }
        roleService.addRole2User(role, user.getId());
        return ApiResult.success();
    }

    @GetMapping("/roles")
    public ApiResult getAllRole(HttpSession session) {
        User user = (User) session.getAttribute(USER);
        return ApiResult.success(roleService.getAll(user.getId()));
    }


}
