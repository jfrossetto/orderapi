package br.com.mglu.orderapi.api.v1.order;

import br.com.mglu.orderapi.order.OrderQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class OrderMapper {

    public Collection<OrderQueryResponse> toResponse(List<OrderQuery> orderQueries) {
        Map<Integer, OrderQueryResponse> response = new HashMap<>();
        for (OrderQuery orderQuery : orderQueries) {
            response.computeIfAbsent(orderQuery.getUserId(), k -> toOrderQueryResponse(orderQuery));
            OrderQueryResponse orderQueryResponse = response.get(orderQuery.getUserId());
            orderQueryResponse.getOrdersMap().computeIfAbsent(orderQuery.getOrderId(), k -> toOrderResponse(orderQuery));
            OrderResponse orderResponse = orderQueryResponse.getOrdersMap().get(orderQuery.getOrderId());
            orderResponse.getProducts().add(toProductResponse(orderQuery));
        }
        return response.values();
    }

    private OrderQueryResponse toOrderQueryResponse(OrderQuery orderQuery) {
        return OrderQueryResponse.builder()
                .userId(orderQuery.getUserId())
                .name(orderQuery.getName())
                .orders(new HashMap<>())
                .build();
    }


    private OrderResponse toOrderResponse(OrderQuery orderQuery) {
        return OrderResponse.builder()
                .orderId(orderQuery.getOrderId())
                .orderDate(orderQuery.getOrderDate())
                .products(new ArrayList<>())
                .build();
    }

    private OrderProductResponse toProductResponse(OrderQuery orderQuery) {
        return OrderProductResponse.builder()
                .productId(orderQuery.getProductId())
                .productValue(orderQuery.getProductValue())
                .build();
    }

}
