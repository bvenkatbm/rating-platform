package com.daytodayhealth.rating.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name = "rating")
public class RatingEntity {

    @Id
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    @Column(columnDefinition = "uuid")
    @JsonIgnore
    private UUID id;

    //todo: check column size
    private String message;

    @JsonIgnore
    @Column(name = "rated_by")
    private UUID ratedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "uuid", name = "product_id")
    @JsonIgnore
    private ProductEntity productId;

    private Date postedAt;

    private Integer rating;

    @Transient
    private String postedByName;
}
