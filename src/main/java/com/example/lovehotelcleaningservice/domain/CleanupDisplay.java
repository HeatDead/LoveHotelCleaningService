package com.example.lovehotelcleaningservice.domain;

public class CleanupDisplay {
    public String pend_date;
    public String cleanup_date;
    public int room_number;
    public String room_name;

    public CleanupDisplay(String pend_date, String cleanup_date, int room_number, String room_name) {
        this.pend_date = pend_date;
        this.cleanup_date = cleanup_date;
        this.room_number = room_number;
        this.room_name = room_name;
    }
}
