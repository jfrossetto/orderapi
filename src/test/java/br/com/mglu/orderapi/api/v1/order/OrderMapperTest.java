package br.com.mglu.orderapi.api.v1.order;

import br.com.mglu.orderapi.Fixture;
import br.com.mglu.orderapi.order.OrderQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {

    @InjectMocks
    private OrderMapper mapper;

    @Test
    void givenValidOrderQuery_whenCalltoResponse_shouldReturnOrderQueryResponse() {
        OrderQuery payload = Fixture.make(OrderQuery.builder().build());
        Collection<OrderQueryResponse> result = mapper.toResponse(List.of(payload));
        assertNotNull(result);
        assertEquals(payload.getUserId(),
                     result.stream().findFirst().map(OrderQueryResponse::getUserId).orElse(null));
        assertEquals(payload.getOrderId(),
                result.stream()
                        .flatMap(orderQueryResponse -> orderQueryResponse.getOrders().stream())
                        .findFirst()
                        .map(OrderResponse::getOrderId)
                        .orElse(null));
        assertEquals(payload.getProductId(),
                result.stream()
                        .flatMap(orderQueryResponse -> orderQueryResponse.getOrders().stream())
                        .flatMap(orderQueryResponse -> orderQueryResponse.getProducts().stream())
                        .findFirst()
                        .map(OrderProductResponse::getProductId)
                        .orElse(null));
    }

}
