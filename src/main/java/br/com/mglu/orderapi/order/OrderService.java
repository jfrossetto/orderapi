package br.com.mglu.orderapi.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    public Mono<List<OrderQuery>> findById(Integer id) {
        return repository.findById(id).collectList();
    }
}
