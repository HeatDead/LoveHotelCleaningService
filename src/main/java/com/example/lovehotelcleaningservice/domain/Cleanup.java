package com.example.lovehotelcleaningservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Cleanup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long user_id;
    private Long room_id;

    private LocalDateTime pend_date;
    private LocalDateTime clean_date;
}