package com.example.services;

import com.example.modal.TripSchedule;

public class ReportManager {
    TripSchedule tripSchedule;
    String msgToUser="" ;

    public ReportManager(TripSchedule tripSchedule, String msgToUser) {
        this.tripSchedule = tripSchedule;
        this.msgToUser = msgToUser;
    }

    public TripSchedule getTripSchedule() {
        return tripSchedule;
    }

    public void setTripSchedule(TripSchedule tripSchedule) {
        this.tripSchedule = tripSchedule;
    }

    public String getMsgToUser() {
        return msgToUser;
    }

    public void setMsgToUser(String msgToUser) {
        this.msgToUser = msgToUser;
    }
}
