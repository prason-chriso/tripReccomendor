package com.example.services;

import com.example.modal.TripSchedule;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class ScheduleSortor implements Comparator<TripSchedule> {

    Logger logger = Logger.getLogger(ScheduleSortor.class);
    //calculate server time
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
    String timeNow =dateFormat.format(new Date());
    public int compare(TripSchedule o1, TripSchedule o2) {
        logger.debug("COMPARING FOR DELAY "+o1.toString()+" against "+o2.toString());
         try {
           if(o1.isComing(timeNow)>o2.isComing(timeNow))return 1 ; else if(o1.isComing(timeNow)<o2.isComing(timeNow)) return -1;
        } catch (ParseException e) {

             logger.error("ERROR DURING PARSING THE DATE DATA "+e.getMessage());
        }
        if(o1.getBusName().equals(o2.getBusName())) return 0; else {if(o1.getBusName().equals("B")) return 1; else return -1; }
    }
}
