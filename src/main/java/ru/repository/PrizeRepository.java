package ru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.model.Prize;

import java.util.Optional;

@Repository
public interface PrizeRepository extends JpaRepository<Prize, Long> {
    Optional<Prize> findByDescription(String description);
}
