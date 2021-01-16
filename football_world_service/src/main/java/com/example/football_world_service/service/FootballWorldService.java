package com.example.football_world_service.service;

import com.example.football_world_service.models.*;
import com.example.football_world_service.models.DTO.TransferMarketDTO;
import com.example.football_world_service.repository.FootballClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Random;
import java.util.UUID;

@Service
public class FootballWorldService {
    final private FootballClubRepository footballClubRepository;

    @Autowired
    public FootballWorldService(FootballClubRepository footballClubRepository) {
        this.footballClubRepository = footballClubRepository;
    }

    @Transactional
    public void buyPlayer(UUID ftClubId) {
        String URL = "http://localhost:8001";
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> headersEntity = new HttpEntity<>(headers);

        ResponseEntity<TransferMarketDTO> response2 = restTemplate
                .exchange(URL + "/transferMarket", HttpMethod.GET, headersEntity, TransferMarketDTO.class);

        TransferMarket transferMarket = new TransferMarket(response2.getBody().getFootballPlayerList());

        int countPlayersOnTransfer = transferMarket
                .getPlayersOnTransfer().size();

        Random random = new Random();

        int indexRandomPlayer = random.nextInt(countPlayersOnTransfer) - 1;

        FootballPlayer footballPlayer =  transferMarket
                .getPlayersOnTransfer().get(indexRandomPlayer);

        Deal deal = new Deal(footballPlayer, TypeDeal.Buying);

        if(footballClubRepository.findById(ftClubId).get().getBudget() > deal.getCostDeal()) {
            if(deal.makeDeal(transferMarket,
                    footballClubRepository.findById(ftClubId).get())) {
                footballClubRepository.findById(ftClubId).get().setBudget(
                        footballClubRepository.findById(ftClubId).get().getBudget()
                                - deal.getCostDeal());
            }
        }
    }

    @Transactional
    public FootballClub getFootballClub(UUID clubId) {
        return footballClubRepository.findById(clubId).get();
    }

    @Transactional
    public void sellPlayer(UUID ftClubId) {
        String URL = "http://localhost:8001";
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> headersEntity = new HttpEntity<>(headers);

        ResponseEntity<TransferMarketDTO> response2 = restTemplate
                .exchange(URL + "/transferMarket", HttpMethod.GET, headersEntity, TransferMarketDTO.class);

        TransferMarket transferMarket = new TransferMarket(response2.getBody().getFootballPlayerList());

        if (footballClubRepository.findById(ftClubId).get().getFootballTeam().size() > 4) {
            Random random = new Random();

            int indexRandomPlayer = random.nextInt(footballClubRepository.findById(ftClubId).get()
                    .getFootballTeam().size());
            FootballPlayer footballPlayer = footballClubRepository.findById(ftClubId).get()
                    .getFootballTeam().get(indexRandomPlayer);

            Deal deal = new Deal(footballPlayer, TypeDeal.Selling);
            deal.makeDeal(transferMarket,
                    footballClubRepository.findById(ftClubId).get());

            if(deal.makeDeal(transferMarket,
                    footballClubRepository.findById(ftClubId).get())) {
                footballClubRepository.findById(ftClubId).get().setBudget(
                        footballClubRepository.findById(ftClubId).get().getBudget()
                                + deal.getCostDeal());
            }
        }
    }

    @Transactional
    public void createClub(FootballClub footballClub) {
        boolean exist = false;
        for(FootballClub footballClub1: footballClubRepository.findAll()) {
            if(footballClub.getClubName().equals(footballClub1.getClubName())) {
                exist = true;
                break;
            }
        }

        if(!exist) {
            footballClubRepository.save(footballClub);
        }
    }

    private void playGame(FootballClub participantOne, FootballClub participantTwo) {
        final Random random = new Random();
        int diffrenceFootballClub = participantOne.calculateStrongTeam()
                - participantTwo.calculateStrongTeam();

        int resultGame = diffrenceFootballClub + random.nextInt(30) - random.nextInt(30);

        if (resultGame > 10) {
            participantOne.setScore(participantOne.getScore() + 3);
        }
        else if (resultGame < -10) {
            participantTwo.setScore(participantTwo.getScore() + 3);
        }
        else {
            participantOne.setScore(participantOne.getScore() + 1);
            participantTwo.setScore(participantTwo.getScore() + 1);
        }
    }

    @Transactional
    public Trainer getTrainer(UUID uuid) {
        return footballClubRepository.findById(uuid).get().getTrainer();
    }


    @Transactional
    public String results() {
        String result = "";
        for(FootballClub footballClub:footballClubRepository.findAll()) {
            result += footballClub.getClubName() +" " + footballClub.getScore() + "\n";
        }

        return result;
    }

    @Transactional
    public void playRound() {
        for(int i = 0; i < footballClubRepository.findAll().size(); ++i) {
            for(int j = i + 1; j < footballClubRepository.findAll().size(); ++j) {
                playGame(footballClubRepository.findAll().get(i), footballClubRepository.findAll().get(j));
            }
        }
    }

}