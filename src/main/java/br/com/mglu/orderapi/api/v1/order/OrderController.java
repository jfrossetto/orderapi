package br.com.mglu.orderapi.api.v1.order;

import br.com.mglu.orderapi.exception.BusinessException;
import br.com.mglu.orderapi.exception.Error;
import br.com.mglu.orderapi.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;
    private final OrderMapper mapper;

    @GetMapping("/{id}")
    public Mono<OrderQueryResponse> findById(@PathVariable Integer id,
                                           ServerWebExchange exchange) {
        return service.findById(id)
                .filter(orderQueries -> !CollectionUtils.isEmpty(orderQueries))
                .switchIfEmpty(Mono.error(() -> new BusinessException(Error.builder()
                        .code("404").message(String.format("order %s not found", id))
                        .build())))
                .map(orderQueries -> mapper.toResponse(orderQueries).stream()
                        .findFirst().orElseGet(() ->OrderQueryResponse.builder().build()))
                .doOnSuccess(e -> exchange.getResponse().setStatusCode(HttpStatus.OK));
    }

}