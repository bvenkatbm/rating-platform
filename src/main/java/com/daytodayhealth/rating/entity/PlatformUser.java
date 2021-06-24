package com.daytodayhealth.rating.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class PlatformUser{

    @Id
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String name;

    private String password;

    private String role;

    private String username;

}
