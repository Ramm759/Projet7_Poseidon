package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "trade")
public class Trade {
    @Id
    // On dit à Hybernate que id est généré par la Bd
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tradeId")
    private Integer tradeId;

    @Column(name = "account")
    private String account;

    @Column(name = "type")
    private String type;

    @Column(name = "buyQuantity")
    @NotBlank(message = "Ce champ ne peut être vide")
    private int buyQuantity;

    @Column(name = "sellQuantity")
    @NotBlank(message = "Ce champ ne peut être vide")
    private int sellQuantity;

    @Column(name = "buyPrice")
    @NotBlank(message = "Ce champ ne peut être vide")
    private BigDecimal buyPrice;

    @Column(name = "sellPrice")
    @NotBlank(message = "Ce champ ne peut être vide")
    private BigDecimal sellPrice;

    @Column(name = "tradeDate")
    @NotBlank(message = "Ce champ ne peut être vide")
    private Date tradeDate;

    @Column(name = "security")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String security;

    @Column(name = "status")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String status;

    @Column(name = "trader")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String trader;

    @Column(name = "benchmark")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String benchmark;

    @Column(name = "book")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String book;

    @Column(name = "creationName")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String creationName;

    @Column(name = "creationDate")
    @NotBlank(message = "Ce champ ne peut être vide")
    private Date creationDate;

    @Column(name = "revisionName")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String revisionName;

    @Column(name = "revisionDate")
    @NotBlank(message = "Ce champ ne peut être vide")
    private Date revisionDate;

    @Column(name = "dealName")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String dealName;

    @Column(name = "dealType")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String dealType;

    @Column(name = "sourceListId")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String sourceListId;

    @Column(name = "side")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String side;
}
