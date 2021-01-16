package com.example.demo;

import com.example.demo.clients.RestApplication;
import com.example.demo.models.DTO.FootballClubDTO;
import com.example.demo.models.TransferMarket;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.example.demo.models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.demo.clients.grpcApplication;


import java.util.*;

@SpringBootApplication
public class ClientPartApplication {

    public static void main(String[] args) {
        RestApplication.run();
    }

}
