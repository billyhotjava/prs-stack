package com.yuzhi.prs.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/** 全局异常 → R 信封，HTTP 状态与 code 一致；未知异常记 warn（带堆栈）。 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(BizException.class)
  public ResponseEntity<R<?>> handleBiz(BizException e) {
    int status = (e.getCode() >= 400 && e.getCode() < 600) ? e.getCode() : 500;
    return ResponseEntity.status(status).body(R.fail(e.getCode(), e.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<R<?>> handleValidation(MethodArgumentNotValidException e) {
    String msg = e.getBindingResult().getFieldErrors().stream()
        .findFirst()
        .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
        .orElse("invalid argument");
    return ResponseEntity.status(400).body(R.fail(400, msg));
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<R<?>> handleNotFound(NoResourceFoundException e) {
    return ResponseEntity.status(404).body(R.fail(404, "not found"));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<R<?>> handleUnknown(Exception e) {
    log.warn("unhandled exception", e);
    return ResponseEntity.status(500).body(R.fail(500, "internal error"));
  }
}
