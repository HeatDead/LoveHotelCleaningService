package com.example.lovehotelcleaningservice.domain;

import javax.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int number;
    private String name;

    private RoomType type;
    private Boolean clean_pend;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User responsible;

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

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public boolean isClean_pend() {
        return clean_pend;
    }

    public void setClean_pend(boolean clean_pend) {
        this.clean_pend = clean_pend;
    }
}
