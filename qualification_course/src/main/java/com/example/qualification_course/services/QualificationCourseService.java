package com.example.qualification_course.services;

import com.example.qualification_course.models.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class QualificationCourseService {

    @Transactional
    public void levelUpTrainer(UUID id) {
        String URL = "http://localhost:8080";
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> headersEntity = new HttpEntity<>(headers);

        ResponseEntity<Trainer> response2 = restTemplate
                .exchange(URL + "/trainer/" + id, HttpMethod.GET, headersEntity, Trainer.class);

        Trainer trainer = response2.getBody();

        trainer.setSkillLevel(trainer
                .getSkillLevel() + 1);
    }
}
