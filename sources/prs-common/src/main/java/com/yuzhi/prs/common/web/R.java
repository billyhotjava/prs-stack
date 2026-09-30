package com.yuzhi.prs.common.web;

/**
 * 统一响应信封。业务码：200 成功；4xx 客户端错误；5xx 服务端错误。
 * HTTP 状态与 code 保持一致（GlobalExceptionHandler 负责映射）。
 */
public class R<T> {

  private int code;
  private String msg;
  private T data;

  public R() {
  }

  public R(int code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public static <T> R<T> ok(T data) {
    return new R<>(200, "success", data);
  }

  public static <T> R<T> ok() {
    return new R<>(200, "success", null);
  }

  public static <T> R<T> fail(int code, String msg) {
    return new R<>(code, msg, null);
  }

  public static <T> R<T> fail(String msg) {
    return fail(500, msg);
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
