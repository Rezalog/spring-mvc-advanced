package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * HttpServletRequest
 * HttpServletResponse
 * HttpMethod : HTTP 메서드를 조회한다. org.springframework.http.HttpMethod
 * Locale : Locale 정보를 조회한다.
 * @RequestHeader MultiValueMap<String, String> headerMap
 * 모든 HTTP 헤더를 MultiValueMap 형식으로 조회한다.
 * @RequestHeader("host") String host
 * 특정 HTTP 헤더를 조회한다.
 * 속성
 * 필수 값 여부: required
 * 기본 값 속성: defaultValue
 * @CookieValue(value = "myCookie", required = false) String cookie
 * 특정 쿠키를 조회한다.
 * 속성
 * 필수 값 여부: required
 * 기본 값: defaultValue
 * MultiValueMap
 * MAP과 유사한데, 하나의 키에 여러 값을 받을 수 있다.
 * HTTP header, HTTP 쿼리 파라미터와 같이 하나의 키에 여러 값을 받을 때 사용한다.
 * keyA=value1&keyA=value2
 * */

@Slf4j
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @RequestHeader MultiValueMap<String, String> headerMap, // 헤더 정보
                          @RequestHeader("host") String host, // 필수 헤더 정보 host
                          @CookieValue(value = "myCookie", required = false) String cookie) {

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);

        return "ok";
    }
}
/*
* [nio-8080-exec-1] h.s.b.request.RequestHeaderController    : request=org.apache.catalina.connector.RequestFacade@2308195e
[nio-8080-exec-1] h.s.b.request.RequestHeaderController    : response=org.springframework.web.context.request.async.StandardServletAsyncWebRequest$LifecycleHttpServletResponse@353a5058
[nio-8080-exec-1] h.s.b.request.RequestHeaderController    : httpMethod=GET
[nio-8080-exec-1] h.s.b.request.RequestHeaderController    : locale=ko
[nio-8080-exec-1] h.s.b.request.RequestHeaderController    : headerMap={host=[localhost:8080], connection=[keep-alive], sec-ch-ua=["Not)A;Brand";v="99", "Microsoft Edge";v="127", "Chromium";v="127"], sec-ch-ua-mobile=[?0], sec-ch-ua-platform=["Windows"], upgrade-insecure-requests=[1], user-agent=[Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36 Edg/127.0.0.0], accept=[text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,\*\/\*;q=0.8,application/signed-exchange;v=b3;q=0.7], sec-fetch-site=[same-origin], sec-fetch-mode=[navigate], sec-fetch-user=[?1], sec-fetch-dest=[document], referer=[http://localhost:8080/], accept-encoding=[gzip, deflate, br, zstd], accept-language=[ko,en;q=0.9,en-US;q=0.8]}
[nio-8080-exec-1] h.s.b.request.RequestHeaderController    : header host=localhost:8080
[nio-8080-exec-1] h.s.b.request.RequestHeaderController    : myCookie=null
* */