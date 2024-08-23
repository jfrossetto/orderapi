package br.com.mglu.orderapi.order;

import br.com.mglu.orderapi.api.v1.order.OrderFilter;
import reactor.core.publisher.Flux;

public interface OrderCustomRepository {

    Flux<OrderQuery> findByFilters(OrderFilter filter);

}
