package com.example.services;

import com.example.modal.TripSchedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;


public class TripCalculateManager implements Comparator<TripSchedule>{

    //calculate server time
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
    String timeNow =dateFormat.format(new Date());
    public int compare(TripSchedule o1, TripSchedule o2) {
        TripSchedule finalSelection;
        TripSchedule tempselection1;
        TripSchedule tempselection2;
        boolean CHECK_FURTHER = true;
        try {
            if((o1.getDelay(timeNow) < o2.getDelay(timeNow))) finalSelection=o1; else finalSelection=o2;
            //if they both start at the same time then priority is given to the bus B
            if((o1.getDelay(timeNow)==o2.getDelay(timeNow))&& o1.getTripLength()==o2.getTripLength()){ if(o2.getBusName().equals("B")) finalSelection=o2 ; else finalSelection=o1;}
            //check id delay of first() is > than second()
            if(o1.getDelay(timeNow)>o2.getDelay(timeNow)) tempselection1 =o2; else tempselection1=o1;
            //check if transit of first() is greater than second()
            if(o1.getTripLength()>o2.getTripLength()) tempselection2=o2; else tempselection2=o1;
             //if both of above step returns same object then make it final selection and abort
            if(tempselection1.equals(tempselection2)){ CHECK_FURTHER= false; finalSelection=tempselection2; }
            //if the delay of second()  is already less than delay of first() return whatever object in hand
            if(CHECK_FURTHER && (o2.getDelay(timeNow)<o1.getDelay(timeNow))) CHECK_FURTHER= false;// if (finalSelection.equals(o1)) finalSelection=o1; else finalSelection=o2;// return 1; else return -1;
            //check for the buffer time of 20 min if the trip length is shorter in the next ride
            if(CHECK_FURTHER && (o2.getDelay(timeNow)<o1.getDelay(timeNow)+20*60*1000)) finalSelection=o2; else{ if(CHECK_FURTHER) finalSelection=o1;}

             //finally we return the positive if object 1 has high precedence over object b
            if (finalSelection.equals(o1)) return 1; else return -1;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  0;
    }
}
