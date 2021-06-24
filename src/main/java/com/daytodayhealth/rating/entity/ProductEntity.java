package com.daytodayhealth.rating.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name = "product")
public class ProductEntity {

    @Id
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String name;

    @Column(name = "average_rating")
    private Float averageRating;

    @Column(name = "rating_count")
    private Long ratingCount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Transient
    @JsonIgnore
    //@JoinColumn(name = "productId")
    private List<RatingEntity> ratings;

    public ProductEntity(String product, float rating, long count) {
        this.name = product;
        this.averageRating = rating;
        this.ratingCount = count;
        this.ratings = new ArrayList<>();
    }
}
