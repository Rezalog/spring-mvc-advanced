package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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
     * <p>
     * -> @RequestMapping 은 URL 경로를 템플릿화 할 수 있는데,
     *
     * @PathVariable 을 사용하면 매칭되는 부분을 편리하게 조회할 수 있다.
     * @PathVariable 의 이름과 파라미터의 이름이 같으면 생략 가능
     * ex) @PathVariable("userId") String userId -> @PathVariable userId
     * <p>
     * request : /mapping/userA -> userId=userA
     */
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
     * <p>
     * request : /mapping-param?mode=debug
     */
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
     */
    @GetMapping(value = "/mapping-param", params = {"mode=debug", "data=no good"})
    public String mappingParams() {
        log.info("mappingParams");
        // 2024-07-31T16:52:44.898+09:00  INFO 11632 --- [springmvc] [nio-8080-exec-9] h.s.b.requestmapping.MappingController   : mappingParams
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * request : postman 으로 헤더에 mode 프로퍼티에 debug value 를 넣고 request
     * <p>
     * params="mode"
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug"
     * params={"mode=debug", "data=good"}
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * HTTP 요청의 Content-Type 헤더를 기반으로 미디어 타입으로 매핑한다.
     * 만약 맞지 않으면 HTTP 415 상태코드(Unsupported Media Type)을 반환한다
     *
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*
     * MediaType.APPLICATION_JSON_VALUE
     *
     * 요청 헤더의 ContentType 에 따라 다른 메서드를 호출하고 싶을 때 사용하기도 한다.
     * 직접 headers 에 접근하기엔 spring mvc 특성상 부가적인 처리때문에 consumes 사용
     *
     * 서버의 Controller 입장에서는 request 의 contentType을 소비하는 입장이므로 consumes 라는 명명
     * 즉, consumes는 클라이언트가 서버에게 보내는 데이터 타입을 명시한다.(이 타입으로 보내주세요)
     *
     * request : postman 으로 헤더에 Content-Type 프로퍼티에 application/json 를 넣고 request
     * */
    @PostMapping(value = "/mapping-consumes", consumes = "application/json")
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }
    /**
     * 미디어 타입 조건 매핑 - HTTP 요청 Accept, produce
     * HTTP 요청의 Accept 헤더를 기반으로 미디어 타입으로 매핑한다.
     * 만약 맞지 않으면 HTTP 406 상태코드(Not Acceptable)을 반환한다
     * produces는 서버가 클라이언트에게 반환하는 데이터 타입을 명시한다.(이 타입으로 반환할게)
     *
     * produces = "text/plain"
     * produces = {"text/plain", "application/*"}
     * produces = MediaType.TEXT_PLAIN_VALUE
     * produces = "text/plain;charset=UTF-8"
     * */
    @PostMapping(value = "/mapping-produces", produces = {"text/html", "text/plain"})
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
