package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    // On dit à Hybernate que id est généré par la Bd
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "curveId")
    @NotBlank(message = "Ce champ ne peut être vide")
    private int curveId;

    @Column(name = "asOfDate")
    @NotBlank(message = "Ce champ ne peut être vide")
    private Date asOfDate;

    @Column(name = "term")
    @NotBlank(message = "Ce champ ne peut être vide")
    private int term;

    @Column(name = "value")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String value;

    @Column(name = "creationDate")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String creationDate;
}
