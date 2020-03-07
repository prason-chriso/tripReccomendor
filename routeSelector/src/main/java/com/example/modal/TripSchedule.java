package com.example.modal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TripSchedule{
    String busName="";
    String todayIs="";
    String departFromC="";
    String arrivalAtD="";
    SimpleDateFormat dateOnly = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd h:mm a");


    public TripSchedule(String busName,String departTime, String arrivalTime) {
        this.busName = busName;

            this.departFromC = getDateFrom(departTime);
            this.arrivalAtD = getDateFrom(arrivalTime);
            this.todayIs = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

    }

    private String getDateFrom(String arrivalTime){
        String todayIs = dateOnly.format(new Date());
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
        this.departFromC = getDateFrom(departFromC);
    }

    public String getArrivalAtD() {
        return arrivalAtD;
    }

    public void setArrivalAtD(String arrivalAtD){
        this.arrivalAtD = getDateFrom(arrivalAtD);
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

}
