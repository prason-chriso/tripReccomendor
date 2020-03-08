package com.example.services;

import com.example.modal.TripSchedule;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleManager {
    static Map<String, TripSchedule> myScheduleData = new HashMap<>();
    Logger logger = Logger.getLogger(ScheduleManager.class);
    public void readScheduleData(){
        try {
            logger.info("Schedule file searching directory is :"+this.getClass().getClassLoader().getResource("schedule").toURI());
            System.out.println("file searching path is "+this.getClass().getClassLoader().getResource("schedule").toURI());
        } catch (URISyntaxException e) {
            logger.error("INVALID PATH DETECTED OR NO RESOURCE FOUND ON THAT PATH"+e.getMessage());
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(new File(this.getClass().getClassLoader().getResource("schedule").toURI())))) {

           String recordLine = "";
            while ((recordLine = reader.readLine())!=null){
                try {
                    TripSchedule tripSchedule;
                    System.out.println(recordLine);
                    String [] record = recordLine.split(Pattern.quote("|"));
                    String busName = record[0].trim();
                    String initTime = record[1].trim();
                    String endTime = record[2].trim();
                    int tripTime = Integer.parseInt(record[3].trim());
                    String key = busName + initTime.replaceAll(Pattern.quote(" "), Matcher.quoteReplacement("_"))+ endTime.replaceAll(Pattern.quote(" "), Matcher.quoteReplacement("_"));
                        tripSchedule = new TripSchedule(busName, initTime, endTime, tripTime);
                        myScheduleData.put(key, tripSchedule);
                    }catch (ArrayIndexOutOfBoundsException ex){
                        logger.error("INSUFFICIENT DATA SUPPLIED IN THE SCHEDULE RECORD, IT MUST HAVE 3 PIPE '|' SEPERATORS ");
                }
           }
        } catch (URISyntaxException e) {
            logger.error("INVALID PATH DETECTED OR NO RESOURCE FOUND ON THAT PATH"+e.getMessage());

        } catch (FileNotFoundException e) {
            logger.error("NO FILE FOUND ON THE PATH "+e.getMessage());

        } catch (IOException e) {
            logger.error("CANNOT READ DATA FROM THE FILE "+e.getMessage());

        }catch (NullPointerException ex){
            logger.error("NULL DATA FOUND WHILE FETCHING SCHEDULE DATA");
        }
        logger.info("SCHEDULE DATA IS ALL VALID AND SUCCESSFULLY LOADED TO THE SYSTEM");
    }

    public Map<String, TripSchedule> getMyScheduleData() {
        readScheduleData();
        return (myScheduleData.size()>0)?myScheduleData:null;

    }

}
