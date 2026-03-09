package com.odegaa.practiceproject.entities;

import com.odegaa.practiceproject.entities.templates.Base;
import com.odegaa.practiceproject.entities.templates.Department;
import com.odegaa.practiceproject.entities.templates.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "employee")
public class Employee extends Base implements UserDetails {

    @NotNull
    @Column(length = 20, nullable = false)
    private String name;

    @NotNull
    @Column(length = 20, nullable = false)
    private String firstName;

    @NotNull
    @Column(length = 20, nullable = false)
    private String lastName;

    @NotNull
    @Column(length = 20, nullable = false, unique = true)
    private String username;

    @NotNull
    private String password;

    private Integer age;

    private Double salary;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "passport_id", unique = true)
    private Passport passport;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Department department;

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER, targetClass = Roles.class)
    @CollectionTable(name = "employee_roles", joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private Set<Roles> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
