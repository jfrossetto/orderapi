package br.com.mglu.orderapi.api.v1.health;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/health")
@RequiredArgsConstructor
public class HealthController {

  @GetMapping
  public Mono<String> health(ServerWebExchange exchange) {
    return Mono.just("working")
        .doOnSuccess(e -> exchange.getResponse().setStatusCode(HttpStatus.OK));
  }

}
