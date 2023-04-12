package co.wadcorp.waiting.data.domain.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ShopCustomerId implements Serializable {

    @Column(name = "customer_seq")
    private Long customerSeq;

    @Column(name = "shop_id")
    private String shopId;
}
