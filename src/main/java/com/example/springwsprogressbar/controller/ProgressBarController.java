package com.example.springwsprogressbar.controller;

import java.security.Principal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/progressbar")
public class ProgressBarController {
    private final Logger LOG = LoggerFactory.getLogger(ProgressBarController.class);
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ProgressBarController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendByPostMapping(@RequestBody MessageBody body, Principal principal) throws InterruptedException {
        LOG.info(body.toString());
        for (int progress = 1; progress <= 100; progress++) {
            Thread.sleep(100); // simulate time progress
            LOG.info("Progress by PostMapping: " + progress + "%");
            simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue/progressbar", Map.of("progress", progress));
        } 
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/send")
    public ResponseEntity<Void> sendByMessageMapping(@Payload MessageBody body, Principal principal) throws InterruptedException {
        LOG.info(body.toString());
        for (int progress = 1; progress <= 100; progress++) {
            Thread.sleep(100); // simulate time progress
            LOG.info("Progress by MessageMapping: " + progress + "%");
            simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue/progressbar", Map.of("progress", progress));
        }
        return ResponseEntity.ok().build();
    }
    
}

class MessageBody {
    private String message;
    
    public String getMessage() {    
        return message;    
    }
    
    public void setMessage(String message) {    
        this.message = message;    
    }

    @Override
    public String toString() {
        return "MessageBody { message: " + this.message + " }";
    }
}
