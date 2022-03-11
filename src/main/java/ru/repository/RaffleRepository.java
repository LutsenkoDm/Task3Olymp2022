package ru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.model.Raffle;

@Repository
public interface RaffleRepository extends JpaRepository<Raffle, Long> {
}
