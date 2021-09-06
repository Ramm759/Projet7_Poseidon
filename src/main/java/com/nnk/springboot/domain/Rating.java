package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    // On dit à Hybernate que id est généré par la Bd
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "modysRating")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String modysRating;

    @Column(name = "sandPRating")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String sandPRating;

    @Column(name = "fitchRating")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String fitchRating;

    @Column(name = "orderNumber")
    @NotBlank(message = "Ce champ ne peut être vide")
    private int orderNumber;


}
