package br.com.mglu.orderapi.order;

import io.r2dbc.spi.Row;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderQueryRowMapperTest {

    @Mock
    Row row;

    @InjectMocks
    private OrderQueryRowMapper rowMapper;

    @Test
    void givenValidRow_whenApply_shouldReturnOrderQuery() {
        when(row.get("user_id", Integer.class)).thenReturn(123);
        OrderQuery result  = rowMapper.apply(row);
        assertEquals(123, result.getUserId());
    }
}
