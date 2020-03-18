package mops.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.HttpStatus;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
  @RequestMapping("/error")
  public String handleError(HttpServletRequest request) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    Integer statusCode = Integer.valueOf(status.toString());
    if (statusCode == HttpStatus.SC_FORBIDDEN) {
      return "403";
    }
    return "error";
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
