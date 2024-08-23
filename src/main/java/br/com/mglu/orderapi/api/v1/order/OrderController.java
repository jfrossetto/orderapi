package br.com.mglu.orderapi.api.v1.order;

import br.com.mglu.orderapi.exception.BusinessException;
import br.com.mglu.orderapi.exception.Error;
import br.com.mglu.orderapi.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collection;

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

    @GetMapping("orders")
    public Mono<Collection<OrderQueryResponse>> findAll(@RequestParam(name = "userId", required = false) Integer userId,
                                                        @RequestParam(name = "productId", required = false) Integer productId,
                                                        @RequestParam(name = "startOrderDate", required = false)LocalDate startOrderDate,
                                                        @RequestParam(name = "endOrderDate", required = false)LocalDate endOrderDate,
                                                        ServerWebExchange exchange) {

        return service.findByFilters(new OrderFilter(userId, productId, startOrderDate, endOrderDate))
                .map(orderQueries -> mapper.toResponse(orderQueries))
                .doOnSuccess(e -> exchange.getResponse().setStatusCode(HttpStatus.OK));
    }

}