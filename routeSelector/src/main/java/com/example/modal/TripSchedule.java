package com.example.modal;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TripSchedule implements Comparable<TripSchedule> {
    Logger logger = Logger.getLogger(TripSchedule.class);
    String busName="";
    String todayIs="";
    String departFromC="";
    String arrivalAtD="";
    int tripDays=1;
    SimpleDateFormat dateOnly = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd h:mm a");
    public TripSchedule(String busName,String departTime, String arrivalTime,int tripDays) {
            this.busName = busName;
            this.tripDays=tripDays;
            this.departFromC = getDateFrom(departTime,1);
            this.arrivalAtD = getDateFrom(arrivalTime,tripDays);
            this.todayIs = dateOnly.format(Calendar.getInstance().getTime());

    }

    private String getDateFrom(String arrivalTime,int daysOfTrip){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,daysOfTrip-1);
        String todayIs = dateOnly.format(calendar.getTime());
        return todayIs+" "+arrivalTime;
    }

    public long getTripLength() throws ParseException {
     return (dateFormat.parse(this.getArrivalAtD()).getTime())-(dateFormat.parse(this.getDepartFromC()).getTime());
    }

    public  long getDelay(String nowTime) throws  ParseException{
        return (dateFormat.parse(this.getArrivalAtD()).getTime())-(dateFormat.parse(nowTime).getTime());
    }

    public  long isComing(String nowTime) throws  ParseException{
        return (dateFormat.parse(this.getDepartFromC()).getTime())-(dateFormat.parse(nowTime).getTime());
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getTodayIs(){
        return this.todayIs;
    }

    public void setTodayIs(){
        this.todayIs =  new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }

    public String getDepartFromC() {
        return departFromC;
    }

    public void setDepartFromC(String departFromC){
        this.departFromC = getDateFrom(departFromC,this.tripDays);
    }

    public String getArrivalAtD() {
        return arrivalAtD;
    }

    public void setArrivalAtD(String arrivalAtD){
        this.arrivalAtD = getDateFrom(arrivalAtD,this.tripDays);
    }
    @Override
    public String toString() {
        return "TripSchedule{" +
                "busName='" + busName + '\'' +
                ", todayIs=" + todayIs +
                ", departFromC=" + departFromC +
                ", arrivalAtD=" + arrivalAtD +
                '}';
    }

    @Override
    public int compareTo(TripSchedule o) {
        logger.debug("Comparision request"+this.toString()+" against "+o.toString());
        try {
            return (int) (this.getTripLength()-o.getTripLength())/1000;
        } catch (ParseException e) {
            logger.error("Error while parsing on Date Data "+e.getMessage());
        }
        return 0;
    }
}
