package hello.springmvc.basic;

import lombok.Data;

/**
 * @Data : @Getter, @Setter, @ToString, @RequiredArgsConstructor, @EqualsAndHashcode 자동 적용
 */

@Data
public class HelloData {
    private String username;
    private int age;
}
