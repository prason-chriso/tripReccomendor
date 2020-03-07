package com.example.services;

import com.example.modal.TripSchedule;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleManager {
    static Map<String, TripSchedule> myScheduleData = new HashMap<String, TripSchedule>();

    public void readScheduleData(){
        try(BufferedReader reader = new BufferedReader(new FileReader(new File(this.getClass().getClassLoader().getResource("schedule").toURI())))) {
            String recordLine = "";
            while ((recordLine = reader.readLine())!=null){

                   System.out.println(recordLine);
                   String record[] = recordLine.split(Pattern.quote("|"));
                   String busName = record[0].trim();
                   String initTime = record[1].trim();
                   String endTime = record[2].trim();
                   String key = busName + initTime.replaceAll(Pattern.quote(" "), Matcher.quoteReplacement("_"));
                   //  myScheduleData.put(record[0].trim()+record[1].trim().replaceAll(": ",_));
                   myScheduleData.put(key, new TripSchedule(busName, initTime, endTime));

            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ArrayIndexOutOfBoundsException ex){
            System.out.println("no record found here continuing on next loop");
        }
    }

    public Map<String, TripSchedule> getMyScheduleData() {
        readScheduleData();
        return (myScheduleData.size()>0)?myScheduleData:null;

    }

}
