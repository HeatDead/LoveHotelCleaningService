package com.example.lovehotelcleaningservice.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int number;
    private String name;

    private RoomType type;
    private Boolean clean_pend;

    private LocalDateTime pend_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isClean_pend() {
        return clean_pend;
    }

    public void setClean_pend(boolean clean_pend) {
        this.clean_pend = clean_pend;
    }

    public Boolean getClean_pend() {
        return clean_pend;
    }

    public void setClean_pend(Boolean clean_pend) {
        this.clean_pend = clean_pend;
    }

    public LocalDateTime getPend_date() {
        return pend_date;
    }

    public void setPend_date(LocalDateTime pend_date) {
        this.pend_date = pend_date;
    }
}
