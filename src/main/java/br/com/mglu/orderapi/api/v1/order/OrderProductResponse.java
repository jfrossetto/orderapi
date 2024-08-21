package br.com.mglu.orderapi.api.v1.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class OrderProductResponse {
    @JsonProperty("product_id")
    private Integer productId;
    @JsonProperty("value")
    private BigDecimal productValue;
}
