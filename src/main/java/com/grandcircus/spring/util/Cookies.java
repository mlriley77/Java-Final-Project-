package com.grandcircus.spring.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
<<<<<<< Updated upstream
 * Created by uComp1337me on 5/30/2017.
=======
 * Class description
 *
 * @author Sarah Guarino
 * @version 1.0
>>>>>>> Stashed changes
 */
public class Cookies {
    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie userId = new Cookie("userId", "null");
        userId.setPath("/");
        userId.setMaxAge(0);
        response.addCookie(userId);

        Cookie userGroup = new Cookie("userGroup", "null");
        userId.setPath("/");
        userId.setMaxAge(0);
        response.addCookie(userGroup);
    }

    public static void createUserCookie(String userIdString,
                                        String userGroupString,
                                        HttpServletResponse response) {
        Cookie userId = new Cookie("userId", userIdString);
        userId.setPath("/");
        userId.setMaxAge(-1);
        response.addCookie(userId);

        System.out.println();
        Cookie userGroup = new Cookie("userGroup", userGroupString);
        userId.setPath("/");
        userId.setMaxAge(-1);
        response.addCookie(userGroup);
    }
}
