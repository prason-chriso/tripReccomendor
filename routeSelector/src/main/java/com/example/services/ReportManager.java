package com.example.services;

import com.example.modal.TripSchedule;
import org.apache.log4j.Logger;

public class ReportManager {
    TripSchedule tripSchedule;
    String msgToUser="" ;
    Logger logger = Logger.getLogger(ReportManager.class);

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

    @Override
    public String toString() {
        logger.debug("Accessing report message for trip "+tripSchedule.toString());
        return this.msgToUser;
    }
}
