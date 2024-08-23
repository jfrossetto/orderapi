package br.com.mglu.orderapi.order;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderRepository extends ReactiveSortingRepository<Order, Integer> {

    @Query("""
            select u.user_id, \n
                   u.name, \n
                   o.order_id, \n 
                   o.order_date, \n
                   op.product_id, \n
                   op.product_value \n
              from orders o \n
             inner join users u on u.user_id = o.user_id \n
             inner join order_products op on op.order_id = o.order_id \n
             where o.order_id = :id \n
             order by o.order_date \n
            """)
    Flux<OrderQuery> findById(@Param("id") Integer id);

}
