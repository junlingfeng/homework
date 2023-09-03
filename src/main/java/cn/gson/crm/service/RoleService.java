package cn.gson.crm.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoleService {

    private final static Map<String, String> data = new ConcurrentHashMap<>();
    /**
     * userId -> role list
     */
    private final static Map<String, Set<String>> USER_ROLES = new ConcurrentHashMap<>();


    public boolean createRole(String role) {
        data.put(role, role);
        return true;
    }

    public boolean delete(String role) {
        synchronized (this) {
            data.remove(role);
            for (Map.Entry<String, Set<String>> entry : USER_ROLES.entrySet()) {
                entry.getValue().remove(role);
            }
        }
        return true;
    }

    public boolean exist(String role) {
        return data.containsKey(role);
    }


    /**
     * get all roles of one user
     *
     * @param userId
     * @return
     */
    public List<String> getAll(String userId) {
        return new ArrayList<>(USER_ROLES.getOrDefault(userId, new HashSet<String>()));
    }

    public List<String> getAll() {
        return new ArrayList<>(data.values());
    }

    public boolean addRole2User(String role, String userId) {
        synchronized (this) {
            Set<String> list = USER_ROLES.getOrDefault(userId, new HashSet<>());
            list.add(role);
            USER_ROLES.put(userId, list);
        }
        return true;
    }


}
