package com.example.imageobjectapi.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Entity
@Data
@Jacksonized
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name= "object")
public class ObjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    @EqualsAndHashCode.Exclude
    private long object_id;

    private String name;
}
