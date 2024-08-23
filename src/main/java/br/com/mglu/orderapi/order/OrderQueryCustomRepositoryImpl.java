package br.com.mglu.orderapi.order;

import br.com.mglu.orderapi.api.v1.order.OrderFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class OrderQueryCustomRepositoryImpl implements OrderCustomRepository {

    private final R2dbcEntityTemplate r2dbcTemplate;
    private final OrderQueryRowMapper rowMapper;

    public Flux<OrderQuery> findByFilters(OrderFilter filter) {

        StringBuilder query = new StringBuilder("""
                select u.user_id,
                       u.name,
                       o.order_id,
                       o.order_date,
                       op.product_id,
                       op.product_value
                  from orders o
            inner join users u on u.user_id = o.user_id
            inner join order_products op on op.order_id = o.order_id
            """);

        Map<String, Object> params = new HashMap<>();
        List<String> conditions = new ArrayList<>();

        if (Objects.nonNull(filter.userId())) {
            conditions.add("o.user_id = :userId");
            params.put("userId", filter.userId());
        }

        if (Objects.nonNull(filter.productId())) {
            conditions.add("op.product_id = :productId");
            params.put("productId", filter.productId());
        }

        if (Objects.nonNull(filter.startOrderDate())) {
            conditions.add("o.order_date >= :startDate");
            params.put("startDate", filter.startOrderDate());
        }

        if (Objects.nonNull(filter.endOrderDate())) {
            conditions.add("o.order_date <= :endDate");
            params.put("endDate", filter.endOrderDate());
        }

        query.append(!conditions.isEmpty() ? "where 1=1" : "");
        conditions.forEach(condition -> query
                .append(" and (")
                .append(condition)
                .append(") \n")
        );

        query.append(" \n order by o.order_date ");

        log.info("findAll {}", query);
        var queryExecute = r2dbcTemplate
                .getDatabaseClient()
                .sql(query.toString());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            queryExecute = queryExecute.bind(entry.getKey(), entry.getValue());
        }
        return queryExecute
                .map(rowMapper)
                .all();

    }
}
