package com.yuzhi.prs.common.security;

/** 请求级当前用户（ThreadLocal，Filter 装配、用完即清）。 */
public final class UserContextHolder {

  private static final ThreadLocal<CurrentUser> HOLDER = new ThreadLocal<>();

  private UserContextHolder() {
  }

  public static void set(CurrentUser user) {
    HOLDER.set(user);
  }

  public static CurrentUser get() {
    return HOLDER.get();
  }

  public static void clear() {
    HOLDER.remove();
  }
}
