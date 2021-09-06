package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "bidlist")
public class BidList {
    @Id
    // On dit à Hybernate que id est généré par la Bd
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
    private Integer id;

    @Column(name = "account")
    private String account;

    @Column(name = "type")
    private String type;

    @Column(name = "bidQuantity")
    @NotBlank(message = "Ce champ ne peut être vide")
    private int bidQuantity;

    @Column(name = "askQuantity")
    @NotBlank(message = "Ce champ ne peut être vide")
    private int askQuantity;

    @Column(name = "bid")
    @NotBlank(message = "Ce champ ne peut être vide")
    private int bid;

    @Column(name = "ask")
    @NotBlank(message = "Ce champ ne peut être vide")
    private int ask;

    @Column(name = "benchmark")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String benchmark;

    @Column(name = "bidListDate")
    @NotBlank(message = "Ce champ ne peut être vide")
    private Date bidListDate;

    @Column(name = "commentary")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String commentary;

    @Column(name = "security")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String security;

    @Column(name = "status")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String status;

    @Column(name = "trader")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String trader;

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
