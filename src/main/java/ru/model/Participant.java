package ru.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Nullable
    private String name;
    private Long promoId;

    public Participant(@Nullable String name) {
        this.name = name;
    }

    public Participant() {
    }

    public long getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public Long getPromoId() {
        return promoId;
    }

    public void setPromoId(Long promoId) {
        this.promoId = promoId;
    }
}
