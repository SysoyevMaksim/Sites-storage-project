package com.sitesstorageproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_generator")
    @SequenceGenerator(name = "auth_generator", sequenceName = "auth_seq", allocationSize = 0)
    @JsonIgnore
    private Long id;

    private String name;

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Authority && Objects.equals(this.id, ((Authority) obj).getId());
    }
}
