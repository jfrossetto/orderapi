package br.com.mglu.orderapi.order;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderQuery {

    private Integer userId;
    private String name;
    private Integer orderId;
    private LocalDate orderDate;
    private Integer productId;
    private BigDecimal productValue;

}
