package br.com.mglu.orderapi.order;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface OrderRepository extends ReactiveSortingRepository<Order, Integer> {

    @Query("select * from orders where order_id = :id")
    Mono<Order> findById(@Param("id") Integer id);

}
