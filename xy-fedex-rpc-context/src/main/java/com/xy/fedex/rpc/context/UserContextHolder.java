package com.xy.fedex.rpc.context;

/**
 * @author tengfei
 */
public class UserContextHolder {

    private final static ThreadLocal<UserContext> CURRENT_USER_CONTEXT = new ThreadLocal<>();

    public static void initUserContext(UserContext userContext) {
        CURRENT_USER_CONTEXT.set(userContext);
    }

    public static UserContext getCurrentUser() {
        return CURRENT_USER_CONTEXT.get();
    }
}
