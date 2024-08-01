package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * hello-form.html 에서 입력값 입력 후 post로 요청
 */

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
//        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    // GET /request-param-v2?username=hello&age=20
    @ResponseBody
    // @Controller 를 쓰면 return String 에서 viewName 을 찾는데, @ResponseBody 로 직접 HTTP Message body 에 return 값 입력
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge
    ) {
//        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3( // @RequestParam 의 파라미터(value) 생략
            @RequestParam String username,
            @RequestParam int age
    ) {
//        log.info("username={}, age={}", username, age);
        return "ok";
    }

    // 동일하게 작동하지만, 명시적으로 @RequestParam 을 쓰는 것을 권장
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(
            String username,
            int age
    ) {
//        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) { // int 로 선언 시, null 이 들어갈 수 없어 Integer 로(500 err)
        log.info("username={}, age={}", username, age);
        return "ok";
    }
}
