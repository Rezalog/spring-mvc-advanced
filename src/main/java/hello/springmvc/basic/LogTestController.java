package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Controller : return "View 이름"
 * @RestController : return "HttpMessageBody 내용" -> REST API 만들 때 핵심
 * 
 * */

@Slf4j
@RestController
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(LogTestController.class); -- @Slf4j 시 lombok 이 대신 해줌

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";
        String name2 = "hello";

        System.out.println("name = " + name);

        log.trace("trace log={}, {}", name, name2); // application.properties 에서 logging level 설정해야 보임
        log.debug("debug log={}, {}", name, name2); // application.properties 에서 logging level 설정해야 보임
        log.info("info log={}", name);
//        log.info("info log={}" + name); -- 연산으로하면 String 연산 -> 메서드호출이므로 이미 resource 손실, 파라미터로해야 메서드 호출이 안됨
        log.warn("warn log={}, {}", name, name2);
        log.error("error log={}, {}", name, name2);

        return "ok";
    }
}
/*
name = Spring
2024-07-30T17:27:33.806+09:00 DEBUG 24820 --- [springmvc] [nio-8080-exec-1] h.springmvc.basic.LogTestController      : debug log=Spring, hello
2024-07-30T17:27:33.807+09:00  INFO 24820 --- [springmvc] [nio-8080-exec-1] h.springmvc.basic.LogTestController      : info log=Spring
2024-07-30T17:27:33.807+09:00  WARN 24820 --- [springmvc] [nio-8080-exec-1] h.springmvc.basic.LogTestController      : warn log=Spring, hello
2024-07-30T17:27:33.807+09:00 ERROR 24820 --- [springmvc] [nio-8080-exec-1] h.springmvc.basic.LogTestController      : error log=Spring, hello
* */
