package com.inspire12.homepage.auth.kakao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoProfile {
    private Long id;
    private KakaoProfileProperties properties;
    @Getter
    @Setter
    @ToString
    private static class KakaoProfileProperties {
        private String nickname;
        private String thumbnail_image;
        private String profile_image;
    }
}
