package br.com.mglu.orderapi.order;

import br.com.mglu.orderapi.api.v1.order.OrderFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderCustomRepository customRepository;
    public Mono<List<OrderQuery>> findById(Integer id) {
        return repository.findById(id).collectList();
    }

    public Mono<List<OrderQuery>> findByFilters(OrderFilter orderFilter) {
        return customRepository.findByFilters(orderFilter)
                .collectList();
    }

}
