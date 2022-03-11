package ru.model;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Prize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Nullable
    private String description;
    @Nullable
    private Long promoId;

    public Prize(@Nullable String description) {
        this.description = description;
    }

    public Prize() {
    }

    public long getId() {
        return id;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    public Long getPromoId() {
        return promoId;
    }

    public void setPromoId(@Nullable Long promoId) {
        this.promoId = promoId;
    }
}
