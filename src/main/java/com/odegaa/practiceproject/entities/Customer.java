package com.odegaa.practiceproject.entities;

import com.odegaa.practiceproject.entities.templates.Base;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class Customer extends Base {
    @Column(nullable = false, length = 30)
    private String firstName;
    @Column(nullable = false, length = 30)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Passport passport;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;

    private LocalDateTime registrationDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee createdBy;
}
