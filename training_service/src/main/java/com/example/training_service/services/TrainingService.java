package com.example.training_service.services;

import com.example.training_service.models.FootballClub;
import com.example.training_service.models.FootballPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class TrainingService {

    @Transactional
    public void train(UUID clubId) {
        String URL = "http://localhost:8080";
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> headersEntity = new HttpEntity<>(headers);

        ResponseEntity<FootballClub> response2 = restTemplate
                .exchange(URL + "/footballClub/" + clubId, HttpMethod.GET, headersEntity, FootballClub.class);

        FootballClub footballClub = response2.getBody();

        for (FootballPlayer footballPlayer
                :footballClub.getFootballTeam()) {
            double newRatingScore = footballPlayer.getRatingScore()
                    + (30-footballPlayer.getAge())*0.1
                    * footballClub.getTrainer().getSkillLevel();

            footballPlayer.setRatingScore((int)newRatingScore);
        }


    }
}
