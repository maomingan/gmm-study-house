package com.gmm.npe;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * 如何优雅的避免空指针异常
 * @author Gmm
 * @date 2024/7/30
 */
public class AvoidNpe {

    public static void main(String[] args) {
        User user = new User(1L, "username", null);
//        System.out.println(problem(user));
//        System.out.println(normal(user));
//        System.out.println(optional(user));
        System.out.println(asset(user));
    }

    /**
     * 可能会npe异常
     */
    private static String problem(User user){
        return user.getDepartment().getDeptName();
    }

    /**
     * 普通方式：嵌套判断太多，不美观
     */
    private static String normal(User user){
        if (user != null) {
            final Department department = user.getDepartment();
            if (department != null) {
                final String deptName = department.getDeptName();
                if (StringUtils.hasLength(deptName)) {
                    return deptName;
                }
            }
        }
        return null;
    }

    /**
     * 推荐使用optional：会先执行orElse作为默认值，再一次去判断执行
     */
    private static String optional(User user){
        return Optional.ofNullable(user)
                .map(User::getDepartment)
                .map(Department::getDeptName)
                .map(String::trim)
                .orElse(null);
    }

    /**
     * 也推荐使用断言，会判断并抛出异常，使代码看起来没有那么多嵌套
     * @param user
     * @return
     */
    private static String asset(User user){
        Assert.notNull(user, "user is null");
        final Department department = user.getDepartment();
        Assert.notNull(department, "department is null");
        return department.getDeptName();
    }

}
