package br.com.mglu.orderapi.order;

import br.com.mglu.orderapi.Fixture;
import br.com.mglu.orderapi.api.v1.order.OrderFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderQueryCustomRepositoryImplTest {

    @Mock
    private DatabaseClient.GenericExecuteSpec mockGenericExecuteSpec;
    @Mock
    private DatabaseClient mockDatabaseClient;
    @Mock
    private RowsFetchSpec mockRowsFetchSpec;
    @Mock
    private R2dbcEntityTemplate r2dbcTemplate;
    @Mock
    private OrderQueryRowMapper rowMapper;

    @InjectMocks
    private OrderQueryCustomRepositoryImpl customRepository;

    @Test
    void givenValidFilterWithUserId_whenCallFindByFilters_shouldDoQueryWithUserIdCondition() {
        OrderFilter filter = new OrderFilter(1, null, null, null);
        OrderQuery orderQuery = Fixture.make(OrderQuery.builder().build());

        when(r2dbcTemplate.getDatabaseClient()).thenReturn(mockDatabaseClient);
        when(mockDatabaseClient.sql(any(String.class))).thenReturn(mockGenericExecuteSpec);
        when(mockGenericExecuteSpec.map(any(Function.class))).thenReturn(mockRowsFetchSpec);
        when(mockGenericExecuteSpec.bind(any(String.class), any(Object.class))).thenReturn(mockGenericExecuteSpec);
        when(mockRowsFetchSpec.all()).thenReturn(Flux.just(orderQuery));

        StepVerifier.create(customRepository.findByFilters(filter))
                .consumeNextWith(result -> {
                    ArgumentCaptor<String> captorSql = ArgumentCaptor.forClass(String.class);
                    verify(mockDatabaseClient).sql(captorSql.capture());
                    assertNotNull(result);
                    String query = captorSql.getValue();
                    assertTrue(query.contains("o.user_id = :userId"));
                    assertFalse(query.contains("op.product_id = :productId"));
                }).verifyComplete();
    }

    @Test
    void givenValidFilterWithProductAndDates_whenCallFindByFilters_shouldDoQueryWithProductAndDateConditions() {
        OrderFilter filter = new OrderFilter(null, 2, LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 31));
        OrderQuery orderQuery = Fixture.make(OrderQuery.builder().build());

        when(r2dbcTemplate.getDatabaseClient()).thenReturn(mockDatabaseClient);
        when(mockDatabaseClient.sql(any(String.class))).thenReturn(mockGenericExecuteSpec);
        when(mockGenericExecuteSpec.map(any(Function.class))).thenReturn(mockRowsFetchSpec);
        when(mockGenericExecuteSpec.bind(any(String.class), any(Object.class))).thenReturn(mockGenericExecuteSpec);
        when(mockRowsFetchSpec.all()).thenReturn(Flux.just(orderQuery));

        StepVerifier.create(customRepository.findByFilters(filter))
                .consumeNextWith(result -> {
                    ArgumentCaptor<String> captorSql = ArgumentCaptor.forClass(String.class);
                    verify(mockDatabaseClient).sql(captorSql.capture());
                    assertNotNull(result);
                    String query = captorSql.getValue();
                    assertFalse(query.contains("o.user_id = :userId"));
                    assertTrue(query.contains("op.product_id = :productId"));
                }).verifyComplete();
    }

}

