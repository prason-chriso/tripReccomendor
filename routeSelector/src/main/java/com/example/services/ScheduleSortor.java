package com.example.services;

import com.example.modal.TripSchedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class ScheduleSortor implements Comparator<TripSchedule> {

    //calculate server time
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
    String timeNow =dateFormat.format(new Date());
    public int compare(TripSchedule o1, TripSchedule o2) {
         try {
           if(o1.getDelay(timeNow)>o2.getDelay(timeNow))return 1 ; else return -1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
