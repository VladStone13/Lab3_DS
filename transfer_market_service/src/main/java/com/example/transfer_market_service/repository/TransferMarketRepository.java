package com.example.transfer_market_service.repository;

import com.example.transfer_market_service.models.TransferMarket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransferMarketRepository extends JpaRepository<TransferMarket, UUID> {
}
