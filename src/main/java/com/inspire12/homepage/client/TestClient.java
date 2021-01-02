package com.inspire12.homepage.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// https://woowabros.github.io/experience/2019/05/29/feign.html
@FeignClient(value = "example", url = "${external-api.http-bin}")
public interface TestClient {

    @GetMapping("/status/{status}")
    void status(@PathVariable("status") int status);
}
