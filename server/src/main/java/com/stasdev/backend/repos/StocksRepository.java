package com.stasdev.backend.repos;

import com.stasdev.backend.entitys.ApplicationUser;
import com.stasdev.backend.entitys.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StocksRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAllByUsersContains(ApplicationUser user);
    Optional<Stock> findBySymbol(String symbol);
}
