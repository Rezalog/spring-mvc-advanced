package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

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

    // 동일하게 작동하지만, required=false 로 적용되고, 명확하게 @RequestParam 을 쓰는 것을 권장
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
            @RequestParam(required = true) String username, // GET /request-param-required?username= 으로 request 시 OK, null 이 아닌 ""
            @RequestParam(required = false) Integer age) { // null 이 들어갈 수 없어 Integer 로(500 err) 하거나, defaultValue 설정
//        log.info("username={}, age={}", username, age);
        return "ok";
    }

    // default 사용 시 required 가 필요 없음
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(defaultValue = "guest") String username,
            @RequestParam(defaultValue = "-1") int age) {
//        log.info("username={}, age={}", username, age);
        return "ok";
    }


    // GET /request-param-multi-value-map?username=hello&age=20&username=hello2
    // username=hello, age=20
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(
            @RequestParam Map<String, Object> paramMap) {

        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    // GET /request-param-multi-value-map?username=hello&age=20&username=hello2
    // multi : username=[hello, hello2], age=[20]
    @ResponseBody
    @RequestMapping("/request-param-multi-value-map")
    public String requestParamMultiValueMap(
            @RequestParam MultiValueMap<String, Object> paramMap) {

        log.info("multi : username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        // multi : username=[hello, hello2], age=[20]
        return "ok";
    }

    // -------------------
    // @ModelAttribute

    @ResponseBody
    @RequestMapping("/model-attribute-v0")
    public String modelAttributeV0(@RequestParam String username,
                                   @RequestParam(defaultValue = "-1") int age) {
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);

        log.info("no modelAttribute helloData : username={}, age={}", username, age);
        log.info("no modelAttribute helloData.toString : helloData={}",helloData);
        /*
        * helloData : username=hello, age=20
          helloData.toString : helloData=HelloData(username=hello, age=20)
        * */
        return "ok";
    }

    /**
     * @ModelAttribute 사용 시 HelloData 의 setXXX 을 호출하여 바인딩한다.(xxx: 프로퍼티)
     * 따라서 별도의 set method 를 실행하지 않아도 됨.
     *
     * ※ 프로퍼티 : 객체에 setUsername(), getUsername() 메서드가 있으면 이 객체는
     * */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("helloData.toString : helloData={}",helloData);
        /*
        * helloData : username=hello, age=20
          helloData.toString : helloData=HelloData(username=hello, age=20)
        * */
        return "ok";
    }

    /**
     * @ModelAttribute 는 생략할 수 있다.
     * 그런데 @RequestParam 도 생략할 수 있으니 혼란이 발생할 수 있다.
     *
     * 스프링은 해당 생략시 다음과 같은 규칙을 적용한다.
     * String , int , Integer 같은 단순 타입 = @RequestParam
     * 나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외에)
     * */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("helloData : helloData={}", helloData);
        return "ok";
    }
}
