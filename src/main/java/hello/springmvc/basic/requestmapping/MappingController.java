package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping({"/hello-basic", "/hello-go"}) // 배열로 와도 두가지 모두 or 조건으로 mapping
    public String helloBasic() {
        log.info("hello basic"); // 2024-07-31T16:21:48.817+09:00  INFO 31236 --- [springmvc] [nio-8080-exec-5] h.s.b.requestmapping.MappingController   : hello basic
        return "ok";
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    /**
     * 편리한 축약 애노테이션 (코드보기)
     *
     * @GetMapping
     * @PostMapping
     * @PutMapping
     * @PatchMapping
     * @DeleteMapping
     */
    @GetMapping("/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }
    /**
     * PathVariable 사용
     *
     * -> @RequestMapping 은 URL 경로를 템플릿화 할 수 있는데,
     * @PathVariable 을 사용하면 매칭되는 부분을 편리하게 조회할 수 있다.
     *
     * @PathVariable 의 이름과 파라미터의 이름이 같으면 생략 가능
     * ex) @PathVariable("userId") String userId -> @PathVariable userId
     *
     * request : /mapping/userA -> userId=userA
     * */
//    @GetMapping("/mapping/{userId}")
//    public String mappingPath(@PathVariable("userId") String data) { //
//        log.info("mappingPath userId={}", data); // 2024-07-31T16:40:35.812+09:00  INFO 29452 --- [springmvc] [nio-8080-exec-2] h.s.b.requestmapping.MappingController   : mappingPath userId=userA
//        return "ok";
//    }
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId) { //
        log.info("mappingPath userId={}", userId); // 2024-07-31T16:40:35.812+09:00  INFO 29452 --- [springmvc] [nio-8080-exec-2] h.s.b.requestmapping.MappingController   : mappingPath userId=userA
        return "ok";
    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable String orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /**
     * 파라미터로 추가 매핑 (param 없으면 Bad Request)
     * params="mode"
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug"
     * params={"mode=debug", "data=good"}
     *
     * request : /mapping-param?mode=debug
     * */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }
    // /mapping-param?mode= , /mapping-param?mode=s, ...
    @GetMapping(value = "/mapping-param", params = "mode!=debug")
    public String mappingParamNotMode() {
        log.info("mappingParamNotMode");
        return "ok";
    }
    /**
     * request(raw) : /mapping-param?mode=debug&data=no good
     * request(encoded) : /mapping-param?mode=debug&data=no%20good
     * */
    @GetMapping(value = "/mapping-param", params = {"mode=debug", "data=no good"})
    public String mappingParams() {
        log.info("mappingParams");
        // 2024-07-31T16:52:44.898+09:00  INFO 11632 --- [springmvc] [nio-8080-exec-9] h.s.b.requestmapping.MappingController   : mappingParams
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * */

}
