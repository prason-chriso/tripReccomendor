package com.example.services;

import com.example.modal.TripSchedule;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/*
    This class is responsible for calculation and management of the route and travelling details based
    on the schedule uploaded to the system
 */
public class RouteManager {

    Logger logger = Logger.getLogger(RouteManager.class);
    ScheduleManager scheduleManager = new ScheduleManager();
    Map<String,TripSchedule> scheduledata = scheduleManager.getMyScheduleData();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
    String timeNow =dateFormat.format(new Date());


    public  ArrayList<TripSchedule> getSortedSchedule(){
       logger.debug("Calculating Sorted Schedule");
        return sortedSchedule(getRawUploadedSchedule());
     }
    public  ArrayList<TripSchedule> getRawUploadedSchedule(){
        try {
            logger.debug("Calculating Sorted Schedule");
           return new ArrayList<>(scheduledata.values());
           }
        catch (NullPointerException ex){
            logger.error("No Any schedule found upload to the system for processing , Please check the resource directory");
           }
    return new ArrayList<>();
    }

    public  ArrayList<TripSchedule> getPrioritizedSchedule(){
       return prioritizeSchedule(getRawUploadedSchedule());
    }

    public ReportManager getReport(){
        logger.debug("Generating the Reports for best Trip");
        ArrayList<TripSchedule> bestOne = prioritizeSchedule(getRawUploadedSchedule());
        return generateReport(bestOne);
    }


    private ArrayList<TripSchedule> filterInvalidTime(ArrayList<TripSchedule> tripSchedules){
      logger.info("Processing with the system time at: "+dateFormat.format(new Date()));
        ArrayList<TripSchedule> resultSchedule = new ArrayList<>();
        for(TripSchedule key: tripSchedules){
            try {

                if(key.isComing(timeNow)>0){
                    resultSchedule.add(key);
               }
            } catch (ParseException e) {
                logger.error("Error while parsing on Date Data "+timeNow);
            }
        }
        if(resultSchedule.size()>0) return resultSchedule; else {logger.info("No Valid Schedule time found for Travel"); return  new ArrayList<>();}
    }

    private ArrayList<TripSchedule> sortedSchedule(ArrayList<TripSchedule> tripSchedules){
        logger.info("Generating the Sorted Schedule");
        try {
            ArrayList<TripSchedule> resutlList = tripSchedules;
            ScheduleSortor scheduleSortor = new ScheduleSortor();
            Collections.sort(resutlList, scheduleSortor);
            return resutlList;
        }catch (NullPointerException ex){
            logger.error("No data Found while sorting the Schedule");
            return  new ArrayList<>();
        }
    }
    private ArrayList<TripSchedule> prioritizeSchedule(ArrayList<TripSchedule> tripSchedules){
       try {
           ArrayList<TripSchedule> validList = filterInvalidTime(tripSchedules);
           ArrayList<TripSchedule> resutlList = sortedSchedule(validList);
           TripCalculateManager tripCalculateManager = new TripCalculateManager();
           Collections.sort(resutlList, tripCalculateManager);
           displayTripScheduleList("THIS IS BEFORE SPECIAL REORDER ", resutlList);
           if(resutlList.size()>0) return checkSpecialCase(resutlList);
       }catch (NullPointerException ex ){
          logger.error("NO LIST FOUND TO PRIORITIZE DURING PROCESSING");
       }catch (Exception exc){
           logger.error("NO LIST FOUND TO PRIORITIZE DURING PROCESSING"+exc.getMessage());
       }
        return  new ArrayList<>();
    }

    private  void displayTripScheduleList(String heading,ArrayList<TripSchedule> originalList){
        logger.debug("-------------\n"+heading+"\n-------------");
        for(TripSchedule ts :originalList) {
            logger.debug(ts.toString());
        }
    }

    /*
    * This block is created later because of special case of contradiction between sorting algorithm of comparator
    * and business requirement. this will re arrange the result set in two possible cases arisen
     *
     * CASE 1: if more than 2 schedule has same start and end time but the later schedule trip has low  transit time
    * for eg :
    *           A : 1:14 to 1:45
    *           B : 1:14 to 1:45
    *           A : 1:25 to 1:50
    *
    * CASE 2: if total transit time of first trip is  more than later schedule trip has low  transit but the later trip consist of 2 more  schedules
    * for eg :
    *           A : 1:14 to 1:45
    *           B : 1:25 to 1:25
    *           A : 1:25 to 1:50
    * */

    public ArrayList<TripSchedule> checkSpecialCase(ArrayList<TripSchedule> tripSchedules){
        logger.info("PROCESSING FOR SPECIAL CHECKS ON THE PRORITY LIST");
        //Now there arises 2 scenario where either first and second.. equal but its immidiate next one has low trip length , lets call it MULTI_HIGH_DELAY
        //second scenario is after the first one then second and third.. untill nth has the lower trip lenght than first , lets call it MULTI_LOW_TRANSIT
        boolean MULTI_HIGH_DELAY=false;
        boolean MULTI_LOW_TRANSIT=false;
        try {
            TripSchedule first = null, second = null, third = null, temp = null;
            tripSchedules.add(tripSchedules.get(0));
            for (int i = 0; i < tripSchedules.size(); i++) {
                if (i == 0) {
                    first = tripSchedules.get(i);
                    continue;
                }
                if (i == 1) {
                    second = tripSchedules.get(i);
                    continue;
                }
                if (i == 2) {
                    third = tripSchedules.get(i);
                    continue;
                }
                try {
                    //Now there arises 2 scenario where either first and second.. equal but its immidiate next one has low trip length
                    if (!MULTI_LOW_TRANSIT && (first.getDelay(timeNow) == second.getDelay(timeNow))) {
                        if (first.getDepartFromC().equals(second.getDepartFromC()) && first.getArrivalAtD().equals(second.getArrivalAtD())) {
                            if (!MULTI_HIGH_DELAY) {
                                temp = first;
                            }
                            if (third.getDelay(timeNow) < (second.getDelay(timeNow)) + 20 * 60 * 1000 && (third.getTripLength() < second.getTripLength())) {
                                MULTI_HIGH_DELAY = true;
                            } //also considering the buffer time before setting SWAP_FLAG  to on
                            if (second.getDelay(timeNow) == third.getDelay(timeNow)) {
                                first = second;
                                second = third;
                                third = tripSchedules.get(i);
                            }
                        }
                    } else if (!MULTI_HIGH_DELAY && (second.getDelay(timeNow) == third.getDelay(timeNow))) {
                        if (second.getDepartFromC().equals(third.getDepartFromC()) && second.getArrivalAtD().equals(third.getArrivalAtD())) {
                            if (!MULTI_LOW_TRANSIT) {
                                temp = first;
                            }
                            if (second.getDelay(timeNow) < (first.getDelay(timeNow) + 20 * 60 * 1000) && (second.getTripLength() < first.getTripLength())) {
                                MULTI_LOW_TRANSIT = true;
                            } //also considering the buffer time before setting SWAP_FLAG to on
                            first = second;
                            second = third;
                            third = tripSchedules.get(i);
                        }
                    } else {
                        break; //if neither of the scenario arises  then no further calculation needed
                    }
                } catch (ParseException e) {
                    logger.error("Error while parsing the date"+e.getMessage());
                }
            } //end of the for loop

            logger.debug("RESULT FOR MULTI_HIGH_DELAY  IS :" + MULTI_HIGH_DELAY);
            logger.debug("RESULT FOR MULTI_LOW_TRANSIT  IS :" + MULTI_LOW_TRANSIT);
            //now we have the list that has the index indication of the repeated items
            if (MULTI_HIGH_DELAY) {
                System.out.println("case of MULTI_HIGH_DELAY");
                //then we have to swap this current result to the first one and shift all other remaining by one step
                int repeatScheduleIndexStartAfter = tripSchedules.indexOf(temp);
                int repeatScheduleIndexEndAt = tripSchedules.indexOf(third);
                temp = third;
                for (int j = repeatScheduleIndexEndAt; j > repeatScheduleIndexStartAfter; j--) {
                    tripSchedules.set(j, tripSchedules.get(j - 1));
                }
                tripSchedules.set(repeatScheduleIndexStartAfter, temp);

            } else if (MULTI_LOW_TRANSIT) {
                System.out.println("case of MULTI_LOW_TRANSIT");
                int repeatScheduleIndexStartAfter = tripSchedules.indexOf(temp);
                int repeatScheduleIndexEndAt = tripSchedules.indexOf(third);
                for (int j = repeatScheduleIndexStartAfter; j < repeatScheduleIndexEndAt; j++) {
                    tripSchedules.set(j, tripSchedules.get(j + 1));
                }
                tripSchedules.set(repeatScheduleIndexEndAt - 1, temp);
            }

            tripSchedules.remove(tripSchedules.size() - 1);
            logger.debug("PROCESSING FOR SPECIAL CASE RE-ORDERING IS COMPLETED");
        }catch (NullPointerException ex){
            logger.error("PROCESSING FOR SPECIAL CASE RE-ORDERING IS COMPLETED WITH EMPTY LIST ");
        }
        return tripSchedules;

    }
//    public static void main(String[] args) {
//      //  new  RouteManager().calculateBestTime();
//        try {
//            RouteManager rm = new RouteManager();
////            ArrayList<TripSchedule> originalList = new ArrayList<TripSchedule>(rm.scheduledata.values());
////            //for original data
////            rm.displayTripScheduleList("This is original list",originalList);
////            ArrayList<TripSchedule> sortedList = rm.sortedSchedule(originalList);
////            rm.displayTripScheduleList("This is the sorted schedule",sortedList);
//            ArrayList<TripSchedule> bestOne = rm.prioritizeSchedule(rm.getRawUploadedSchedule());
//           rm.displayTripScheduleList("This is the prioritize schedule",bestOne);
//            System.out.println(rm.generateReport(bestOne));
//        }
//        catch (NullPointerException ex){
//            System.out.println("There is Schedule uploaded to the system");
//        }
//      }

    private  int minuteCalculator(long miliseconds){
        return (int)miliseconds/60000;
    }

    private ReportManager generateReport(ArrayList<TripSchedule> prioritizeList){
       String msg = "";
        //now whichever is listed at the last of the List has the highest priority
        try {
            //if the list is already empty then we can say customer to visit tomorrow
            if(prioritizeList.size()>=1) {
                TripSchedule finalResult = prioritizeList.get(0);
                TripSchedule alternate = determineAlternate(prioritizeList);
                logger.info("Some of the possible travel List found");
                //If the trip length is more than 90 minutes then suggest user to come later.
                if (minuteCalculator(finalResult.isComing(timeNow)) >90) {
                    logger.info("TRAVEL TIME IS AFTER 90 MINUTES");
                    msg = "Next bus '" + finalResult.getBusName() + "', Trip Beginning time is more than 90 minutes, You can finish some of your work and arrive sometime before " + finalResult.getDepartFromC();
                    return new ReportManager(finalResult,msg);
                }
              else {

                    msg = "Next best Trip for you will be by Bus '" + finalResult.getBusName() + "', " +
                            "departure time " + finalResult.getDepartFromC() + ". \n You will reach destination after "
                            + minuteCalculator(finalResult.getDelay(timeNow)) + " minute(s).";
                    if (minuteCalculator(finalResult.getTripLength()) >90) {
                         msg = msg + "Transit time is " + minuteCalculator(finalResult.getTripLength())+" Minute(s),\n";
                        System.out.println("printing result "+alternate.equals(finalResult));
                        if (!alternate.equals(finalResult)) {
                            logger.info("ALTERNATE CHOICE IS BETTER ACCORDING TO THE SYSTEM");
                            msg += "\n----------------------OR-----------------------\n" +
                                    " You can finish some work and catchup next bus "
                                    + alternate.getBusName() + ", departure time at "
                                    + alternate.getDepartFromC() + " and travel time will be "
                                    + minuteCalculator(alternate.getTripLength()) + " Minutes";
                            return new ReportManager(alternate,msg);
                        }

                    }
                    logger.info("REPORT SUCCESSFULLY GENERATED");
                    return new ReportManager(finalResult,msg);
                }
            }//end of if when there is item in the priority list
            else{
                logger.info("NO TRIP AVAILABLE FOR TODAY");
                msg="There is not bus trip available For today \n\n\n-------------Thank You------------------\n";
            }
        } catch (ParseException e) {
            logger.error("ERROR WHILE PARSING ON THE DATA "+e.getMessage());
        }
        logger.debug("UNABLE TO GENERATE THE REPORT WITH THE TRIP DETAILS");
        return new ReportManager(null,msg);
    }

    private TripSchedule determineAlternate(
            ArrayList<TripSchedule> filterList){
         if(filterList.size()>1){
            for(int i=1; i<filterList.size(); i++) {
                try {
                TripSchedule tripSchedule = filterList.get(i);
                 if(minuteCalculator(tripSchedule.getTripLength())<90) return tripSchedule;
                } catch (ParseException e) {
                    logger.error("Error while parsing the date data "+e.getMessage());
                }
            }
        }
        else{
            return filterList.get(0); //return only one object
        }
        return filterList.get(1); //return the best object
    }
}
