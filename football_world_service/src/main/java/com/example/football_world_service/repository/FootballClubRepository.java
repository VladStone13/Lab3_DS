package com.example.football_world_service.repository;

import com.example.football_world_service.models.FootballClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


public interface FootballClubRepository extends JpaRepository<FootballClub, UUID> {
}
