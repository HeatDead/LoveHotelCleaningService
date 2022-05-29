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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public LocalDateTime getPend_date() {
        return pend_date;
    }

    public void setPend_date(LocalDateTime pend_date) {
        this.pend_date = pend_date;
    }

    public LocalDateTime getClean_date() {
        return clean_date;
    }

    public void setClean_date(LocalDateTime clean_date) {
        this.clean_date = clean_date;
    }
}