package hello.springmvc.basic.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
@Slf4j
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("helloData={}", helloData);

        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws JsonProcessingException {

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("v2 helloData={}", helloData);

        return "ok";
    }

    /**
     * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type:
    application/json)
     *
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) {
        log.info("v3 helloData={}",helloData);
        return "ok";
    }

    /**
     * @RequestBody 객체 파라미터
     * @RequestBody HelloData data
     * @RequestBody 에 직접 만든 객체를 지정할 수 있다.
     * HttpEntity , @RequestBody 를 사용하면 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가 원하는 문
     * 자나 객체 등으로 변환해준다.
     * HTTP 메시지 컨버터는 문자 뿐만 아니라 JSON도 객체로 변환해주는데, 우리가 방금 V2에서 했던 작업을 대신 처리
     * 해준다.
     * 자세한 내용은 뒤에 HTTP 메시지 컨버터에서 다룬다
     * */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public HttpEntity<String> requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {

        HelloData body = httpEntity.getBody();
        log.info("v4 helloData={}", body);

        return new HttpEntity<>("ok");
    }

    /**
     * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type:
    application/json)
     *
     * @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용(Accept:
    application/json)
     *
     * @ResponseBody
     * 응답의 경우에도 @ResponseBody 를 사용하면 해당 객체를 HTTP 메시지 바디에 직접 넣어줄 수 있다.
     * 물론 이 경우에도 HttpEntity 를 사용해도 된다.
     *
     * - @RequestBody 요청
     * : JSON 요청 ->  HTTP 메시지 컨버터 -> 객체
     * - @ResponseBody 응답
     * : 객체 ->  HTTP 메시지 컨버터 -> JSON 응답
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) {
        log.info("v5 helloData={}",helloData);
        return helloData;
    }
}
