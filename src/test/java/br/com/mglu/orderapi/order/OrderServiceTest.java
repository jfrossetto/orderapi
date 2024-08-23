package br.com.mglu.orderapi.order;

import br.com.mglu.orderapi.Fixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private OrderCustomRepository customRepository;

    @InjectMocks
    private OrderService service;

    @Test
    void should_findById() {
        OrderQuery orderQuery = Fixture.make(OrderQuery.builder().build());
        when(repository.findById(1)).thenReturn(Flux.just(orderQuery));
        StepVerifier.create(service.findById(1))
                .consumeNextWith(result -> {
                    assertNotNull(result);
                })
                .verifyComplete();
    }

}
