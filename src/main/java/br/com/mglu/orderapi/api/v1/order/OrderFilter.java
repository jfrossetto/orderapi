package br.com.mglu.orderapi.api.v1.order;

import java.time.LocalDate;

public record OrderFilter(Integer userId,
                          Integer productId,
                          LocalDate startOrderDate,
                          LocalDate endOrderDate) {

}
