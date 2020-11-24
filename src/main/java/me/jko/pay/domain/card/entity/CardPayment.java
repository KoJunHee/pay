package me.jko.pay.domain.card.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardPayment {
    @Id
    @Column(length = 20)
    private String id;

    @Lob
    @Column(length = 450)
    private String result;
}
