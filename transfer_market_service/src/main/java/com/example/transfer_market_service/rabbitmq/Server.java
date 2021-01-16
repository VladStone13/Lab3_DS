package com.example.transfer_market_service.rabbitmq;

import com.example.transfer_market_service.models.DTO.TransferMarketDTO;
import com.example.transfer_market_service.models.FootballPlayer;
import com.example.transfer_market_service.services.TransferMarketService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@AllArgsConstructor
public class Server {
    private final TransferMarketService transferMarketService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void create(String deliverJson) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        TransferMarketDTO footballPlayersDto = gson.fromJson(deliverJson, TransferMarketDTO.class);
        List<FootballPlayer> footballPlayers = footballPlayersDto.getFootballPlayerList();

        transferMarketService.create(footballPlayers);

    }
}