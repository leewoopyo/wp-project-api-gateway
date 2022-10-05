package com.woopi.project.wpprojectapigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config>{

    private Logger log = LoggerFactory.getLogger(CustomFilter.class);

	// 설정 정보를 제공하는 클래스
	public static class Config {	
		// 설정 정보가 필요한 경우 명시
	}

    public CustomFilter() {
        super(Config.class);
    }

	// 필터의 동작을 정의한 메서드
	@Override
	public GatewayFilter apply(Config config) {
		// custom pre filter 
		return (exchange, chain) -> {
			// 요청이 전달되었을 때 요청 아이디를 로그로 출력
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();
			log.debug("Custom PRE FILTER: request id = {}", request.getId());
			
			// custom post filter
			// 응답의 처리상태코드를 로그로 출력
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.debug("Custom POST FILTER: response code = {}", response.getStatusCode());
			}));
		};
	}
    
}
