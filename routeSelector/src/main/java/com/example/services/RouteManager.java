package com.example.services;

import com.example.modal.TripSchedule;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/*
    This class is responsible for calculation and management of the route and travelling details based
    on the schedule uploaded to the system
 */
public class RouteManager {

    ScheduleManager scheduleManager = new ScheduleManager();
    Map<String,TripSchedule> scheduledata = scheduleManager.getMyScheduleData();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
    String timeNow =dateFormat.format(new Date());


    public  ArrayList<TripSchedule> getSortedSchedule(){
       return sortedSchedule(getRawUploadedSchedule());
     }
    public  ArrayList<TripSchedule> getRawUploadedSchedule(){
        try {
           return new ArrayList<TripSchedule>(scheduledata.values());
           }
        catch (NullPointerException ex){
        System.out.println("There is Schedule uploaded to the system");
    }
    return new ArrayList<TripSchedule>();
    }

    public  ArrayList<TripSchedule> getPrioritizedSchedule(){
       return prioritizeSchedule(getRawUploadedSchedule());
    }

    public ReportManager getReport(){
        ArrayList<TripSchedule> bestOne = prioritizeSchedule(getRawUploadedSchedule());
        return generateReport(bestOne);
    }


    private ArrayList<TripSchedule> filterInvalidTime(ArrayList<TripSchedule> tripSchedules){
       System.out.println("time is "+dateFormat.format(new Date()));
        ArrayList<TripSchedule> resultSchedule = new ArrayList<TripSchedule>();
        for(TripSchedule key: tripSchedules){
            try {

                if(key.isComing(timeNow)>0){
                    resultSchedule.add(key);
//                    System.out.println(scheduledata.get(key).getDelay(timeNow)+"added to the list "+scheduledata.get(key).toString());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(resultSchedule.size()>0) return resultSchedule; else return  null;
    }

    private ArrayList<TripSchedule> sortedSchedule(ArrayList<TripSchedule> tripSchedules){
        ArrayList<TripSchedule> resutlList = tripSchedules;
        ScheduleSortor scheduleSortor = new ScheduleSortor();
        Collections.sort(resutlList,scheduleSortor);
        return  resutlList;
    }
    private ArrayList<TripSchedule> prioritizeSchedule(ArrayList<TripSchedule> tripSchedules){
        ArrayList<TripSchedule> validList = filterInvalidTime(tripSchedules);
        ArrayList<TripSchedule> resutlList = sortedSchedule(validList);
        TripCalculateManager tripCalculateManager = new TripCalculateManager();
        Collections.sort(resutlList,tripCalculateManager);
        return  resutlList;
    }

    private  void displayTripScheduleList(String heading,ArrayList<TripSchedule> originalList){
        System.out.println("-------------\n"+heading+"\n-------------");
        for(TripSchedule ts :originalList) {
            System.out.println(ts.toString());
        }
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
////            System.out.println(rm.generateReport(bestOne));
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
                TripSchedule finalResult = prioritizeList.get(prioritizeList.size() - 1);
                TripSchedule alternate = determineAlternate(prioritizeList);

                //If the trip length is more than 90 minutes then suggest user to come later.
                if (minuteCalculator(finalResult.isComing(timeNow)) >90) {
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
                            msg += "\n----------------------OR-----------------------\n" +
                                    " You can finish some work and catchup next bus "
                                    + alternate.getBusName() + ", departure time at "
                                    + alternate.getDepartFromC() + " and travel time will be "
                                    + minuteCalculator(alternate.getTripLength()) + " Minutes";
                            return new ReportManager(alternate,msg);
                        }

                    }
                    return new ReportManager(finalResult,msg);
                }
            }//end of if when there is item in the priority list
            else{
                msg="There is not bus trip available For today \n-------------Thank You------------------\n";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ReportManager(null,msg);
    }

    private TripSchedule determineAlternate(
            ArrayList<TripSchedule> filterList){
         if(filterList.size()>1){
            for(int i=filterList.size()-2; i>=0; i--) {
                try {
                TripSchedule tripSchedule = filterList.get(i);
                 if(minuteCalculator(tripSchedule.getTripLength())<90) return tripSchedule;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            return filterList.get(filterList.size()-1); //return only one object
        }
        return filterList.get(filterList.size()-1); //return the best object
    }
}
