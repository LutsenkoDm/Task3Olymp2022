package ru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.model.Promo;

import java.util.List;

@Repository
public interface PromoRepository extends JpaRepository<Promo, Long> {
    @Query("select id, name, description from Promo")
    List<Object[]> promos();
}
