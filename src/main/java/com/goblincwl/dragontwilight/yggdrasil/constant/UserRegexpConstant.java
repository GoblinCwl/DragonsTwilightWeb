package com.goblincwl.dragontwilight.yggdrasil.constant;

public class UserRegexpConstant {
    // 用户名即用户邮箱，应该是一个标准的邮箱阵列
    public final static String username = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    // 密码应当包含数字,英文,字符中的两种以上，长度8-20
    public final static String password = "^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)])+$).{8,20}$";
}
