package com.ggz.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="TestEntity",schema="root")
@Getter
@Setter
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = true,length = 20)
    private String name;

    @Column(name = "agee", nullable = true, length = 4)
    private int age;
}
