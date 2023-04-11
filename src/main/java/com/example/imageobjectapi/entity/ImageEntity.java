package com.example.imageobjectapi.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.Cascade;

import javax.annotation.Nullable;
import java.util.Set;

@Entity
@Data
@Jacksonized
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Table(name= "image")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long image_id;

    @Column(name= "label")
    private String label;

    @ManyToMany(
            cascade =
                    {
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH,
                            CascadeType.PERSIST
                    }
    )
    @JoinTable(name = "image_object",
            joinColumns = @JoinColumn(
                    name = "image_id", referencedColumnName = "image_id"
            ),
            inverseJoinColumns =
            @JoinColumn(name = "object_id", referencedColumnName = "object_id"
            )
    )
    private Set<ObjectEntity> objects;

    @Column(name= "metadata", columnDefinition="TEXT")
    private String metadata;
    @Column(name= "image", columnDefinition="BYTEA")
    private byte[] image;
    @Column(name="image_url", columnDefinition = "TEXT")
    private String imageUrl;
}
