package com.ashen.petcommon.utils;

import com.ashen.petcommon.context.UserContext;
import com.ashen.petcommon.model.LoginUser;
import com.ashen.petcommon.model.enums.RoleEnum;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {
    public static LoginUser getCurrentUser(){
        return UserContext.get();
    }
    public static boolean checkIfAdmin(){
        LoginUser user = getCurrentUser();
        return user.getRoles().contains(RoleEnum.ADMIN.getCode());
    }
    public static void checkIfAdminOrThrow(){
        if(!checkIfAdmin()){
            throw new SecurityException("当前用户无管理员权限");
        }
    }

    public static Long getCurrentUserId(){
        return getCurrentUser().getUserId();
    }

    public static List<String> getCurrentUserRoles(){
        return new ArrayList<>(getCurrentUser().getRoles());
    }
}
