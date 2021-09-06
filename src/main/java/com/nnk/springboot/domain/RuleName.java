package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    // On dit à Hybernate que id est généré par la Bd
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String description;

    @Column(name = "json")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String json;

    @Column(name = "template")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String template;

    @Column(name = "sqlStr")
    @NotBlank(message = "Ce champ ne peut être vide")
    private String sqlStr;

    @Column(name = "sqlPart")
    private String sqlPart;
}
