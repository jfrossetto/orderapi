package br.com.mglu.orderapi.api.v1.order;

import br.com.mglu.orderapi.order.Order;
import br.com.mglu.orderapi.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public Mono<Order> findById(@PathVariable Integer id,
                                ServerWebExchange exchange) {
        return service.findById(id)
                .doOnSuccess(e -> exchange.getResponse().setStatusCode(HttpStatus.OK));
    }

}