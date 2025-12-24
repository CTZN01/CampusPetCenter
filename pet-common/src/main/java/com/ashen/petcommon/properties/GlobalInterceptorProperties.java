package com.ashen.petcommon.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@Component
@ConfigurationProperties(prefix = "global.interceptor")
public class GlobalInterceptorProperties {
    // 白名单
    private Set<String> clientWhitelist;
    // 无需校验客户端ID的接口路径
    private Set<String> excludePaths = Set.of("/login","/health","error");
}
