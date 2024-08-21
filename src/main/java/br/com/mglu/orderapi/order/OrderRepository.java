package br.com.mglu.orderapi.order;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderRepository extends ReactiveSortingRepository<Order, Integer> {

    @Query(" select u.user_id, " +
           "        u.name, " +
           "        o.order_id, " +
           "        o.order_date, " +
           "        op.product_id," +
           "        op.product_value " +
           "   from orders o " +
           "  inner join users u on u.user_id = o.user_id " +
           "  inner join order_products op on op.order_id = o.order_id " +
           "  where o.order_id = :id " +
           "  order by o.order_date ")
    Flux<OrderQuery> findById(@Param("id") Integer id);

}
