package br.com.mglu.orderapi.order;

import io.r2dbc.spi.Readable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;

@Component
public class OrderQueryRowMapper implements Function<Readable, OrderQuery> {

    @Override
    public OrderQuery apply(Readable row) {
        return OrderQuery.builder()
                .userId(row.get("user_id", Integer.class))
                .name(row.get("name", String.class))
                .orderId(row.get("order_id", Integer.class))
                .orderDate(row.get("order_date", LocalDate.class))
                .productId(row.get("product_id", Integer.class))
                .productValue(row.get("product_value", BigDecimal.class))
                .build();

    }
}
