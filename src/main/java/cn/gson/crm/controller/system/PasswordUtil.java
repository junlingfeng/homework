package cn.gson.crm.controller.system;

import org.apache.tomcat.util.security.MD5Encoder;

public class PasswordUtil {

    private PasswordUtil(){}
    public static final String SALT = "2234werwerqer32142341";

    /**
     * encode password with salt second times
     * @param password
     * @return
     */
    public static String encode(String password) {
        return MD5Encoder.encode(MD5Encoder.encode((password + SALT).getBytes()).getBytes());
    }
}
