package com.yuzhi.prs.shadow;

import com.yuzhi.prs.common.security.CurrentUser;
import com.yuzhi.prs.common.security.UserContextHolder;
import com.yuzhi.prs.common.web.BizException;
import com.yuzhi.prs.common.web.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 身份回显：有 X-DTS-* 头返回身份，无头 401（T03 负例）。 */
@RestController
@RequestMapping("/api")
public class EchoController {

  @GetMapping("/echo")
  public R<CurrentUser> echo() {
    CurrentUser user = UserContextHolder.get();
    if (user == null) {
      throw new BizException(401, "未登录");
    }
    return R.ok(user);
  }
}
