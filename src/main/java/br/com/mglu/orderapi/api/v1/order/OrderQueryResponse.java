package br.com.mglu.orderapi.api.v1.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.Map;

@Builder
public class OrderQueryResponse {

    @Getter
    @JsonProperty("user_id")
    private Integer userId;
    @Getter
    @JsonProperty("name")
    private String name;
    private Map<Integer, OrderResponse> orders;

    @JsonProperty("orders")
    public Collection<OrderResponse> getOrders() {
        return orders.values();
    }

    @JsonIgnore
    public Map<Integer, OrderResponse> getOrdersMap() {
        return orders;
    }

}
