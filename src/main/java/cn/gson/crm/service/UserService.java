package cn.gson.crm.service;

import cn.gson.crm.controller.system.PasswordUtil;
import cn.gson.crm.model.domain.User;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private Map<String, User> data = new ConcurrentHashMap<>();

    public boolean createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        data.put(user.getId(), user);
        return true;
    }

    public User findByUserName(String user) {
        for (Map.Entry<String, User> item : data.entrySet()) {
            if (item.getValue().getName().equals(user)) {
                return item.getValue();
            }
        }
        return null;
    }

    public User findById(String id) {
        return data.getOrDefault(id, null);
    }


    public User findByUserNameAndPwd(String user, String pwd) {
        for (Map.Entry<String, User> item : data.entrySet()) {
            if (item.getValue().getName().equals(user) && item.getValue().getPassword().equals(pwd)) {
                return item.getValue();
            }
        }
        return null;
    }


    public boolean deleteUser(String userId) {
        if (!data.containsKey(userId)) {
            return false;
        }
        data.remove(userId);
        return true;
    }

}
