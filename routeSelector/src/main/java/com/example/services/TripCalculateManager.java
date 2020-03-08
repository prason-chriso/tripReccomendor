package com.example.services;

import com.example.modal.TripSchedule;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;


public class TripCalculateManager implements Comparator<TripSchedule>{

    Logger logger = Logger.getLogger(TripCalculateManager.class);
    //calculate server time
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
    String timeNow =dateFormat.format(new Date());
    public int compare(TripSchedule o1, TripSchedule o2) {
        logger.debug("COMPARING FOR SCHEDULE PRIORITY "+o1.toString()+" against "+o2.toString());
        TripSchedule finalSelection;
        TripSchedule tempselection1;
        TripSchedule tempselection2;
        boolean CHECK_FURTHER = true;
        try {
      //      System.out.println("comparision for "+o1.toString()+ " and "+o2.toString());

            if((o1.getDelay(timeNow) < o2.getDelay(timeNow))) finalSelection=o1; else finalSelection=o2;
          //  System.out.println(CHECK_FURTHER+"<- FLAG  and, point 1 selection is :"+finalSelection.toString());
            //if they both start at the same time then priority is given to the bus B AND then abort
            if((o1.getDelay(timeNow)==o2.getDelay(timeNow))&& o1.getTripLength()==o2.getTripLength()){ if(o2.getBusName().equals("B")) finalSelection=o2 ; else finalSelection=o1; CHECK_FURTHER=false;}

        //    System.out.println(CHECK_FURTHER+"<- FLAG  and, point 2 selection is :"+finalSelection.toString());
            //check id delay of first() is > than second()

        //    System.out.println(CHECK_FURTHER+"<- FLAG  and, point 3 selection is :"+finalSelection.toString());
            if(CHECK_FURTHER && (o1.getDelay(timeNow)>o2.getDelay(timeNow))) tempselection1 =o2; else tempselection1=o1;
            //check if transit of first() is greater than second()
//            System.out.println(CHECK_FURTHER+"<- FLAG  and, Temp 1 selection is :"+tempselection1.toString());
//            System.out.println(CHECK_FURTHER+"<- FLAG  and, point 4 selection is :"+finalSelection.toString());
            if(CHECK_FURTHER &&(o1.getTripLength()>o2.getTripLength())) tempselection2=o2; else tempselection2=o1;
             //if both of above step returns same object then make it final selection and abort
//            System.out.println(CHECK_FURTHER+"<- FLAG  and, Temp 2 selection is :"+tempselection2.toString());
//            System.out.println(CHECK_FURTHER+"<- FLAG  and, point 5 selection is :"+finalSelection.toString());
            if(CHECK_FURTHER &&(tempselection1.equals(tempselection2))){ CHECK_FURTHER= false; finalSelection=tempselection2; }
//            if the delay of second()  is already less than delay of first() return whatever object in hand

    //        System.out.println(CHECK_FURTHER+"<- FLAG  and, point 6 selection is :"+finalSelection.toString());
            if(CHECK_FURTHER && (o2.getDelay(timeNow)<o1.getDelay(timeNow))&&(o2.getTripLength()<o1.getTripLength())) CHECK_FURTHER= false;// if (finalSelection.equals(o1)) finalSelection=o1; else finalSelection=o2;// return 1; else return -1;
            //check for the buffer time of 20 min if the trip length is shorter in the next ride

         //   System.out.println(CHECK_FURTHER+"<- FLAG  and, point 7 selection is :"+finalSelection.toString());
            if(CHECK_FURTHER && (o2.getDelay(timeNow)<o1.getDelay(timeNow)+20*60*1000) && (o2.getTripLength()<o1.getTripLength())) finalSelection=o2; // else{ if(CHECK_FURTHER) finalSelection=o1;}


        //    System.out.println("final selection is :"+finalSelection.toString());
             //finally we return the positive if object 1 has high precedence over object b
            //this will create the sorted list with highest precedence object at index 0 of the list
            if (finalSelection.equals(o1)) return -1; else return 1;

        } catch (ParseException e) {
            logger.error("ERROR DURING PARSING THE DATE DATA "+e.getMessage());
        }
        return  0;
    }
}
