package br.com.mglu.orderapi.api.v1.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class OrderResponse {

    @JsonProperty("order_id")
    private Integer orderId;
    @JsonProperty("date")
    private LocalDate orderDate;
    @JsonProperty("products")
    private List<OrderProductResponse> products;

    @JsonProperty("total")
    public BigDecimal getTotal() {
        return products.stream()
                .map(OrderProductResponse::getProductValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
