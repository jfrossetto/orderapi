package br.com.mglu.orderapi.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderQuery {

    private Integer userId;
    private String name;
    private Integer orderId;
    private LocalDate orderDate;
    private Integer productId;
    private BigDecimal productValue;

}
