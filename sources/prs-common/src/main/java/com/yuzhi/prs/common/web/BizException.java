package com.yuzhi.prs.common.web;

/** 业务异常：code 决定 HTTP 状态（400~599 照搬，其余按 500）。 */
public class BizException extends RuntimeException {

  private final int code;

  public BizException(int code, String message) {
    super(message);
    this.code = code;
  }

  public BizException(String message) {
    this(500, message);
  }

  public int getCode() {
    return code;
  }
}
