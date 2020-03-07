package com.example.controller;


import com.example.modal.TripSchedule;
import com.example.services.ReportManager;
import com.example.services.RouteManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;


@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }


    @GetMapping("/schedule")
    @ResponseBody
    public ArrayList<TripSchedule> getTodaySchedule() {
        RouteManager routeManager = new RouteManager();
        return routeManager.getRawUploadedSchedule();
    }

    @GetMapping("/sortedSchedule")
    @ResponseBody
    public ArrayList<TripSchedule> loadSortedSchedule() {
        RouteManager routeManager = new RouteManager();
        return routeManager.getSortedSchedule();
    }

    @GetMapping("/prioritySchedule")
    @ResponseBody
    public ArrayList<TripSchedule> loadPrioritySchedule() {
        RouteManager routeManager = new RouteManager();
        return routeManager.getPrioritizedSchedule();
    }

    @GetMapping("/showResult")
    @ResponseBody
    public ReportManager getAdviceFromServer() {
        RouteManager routeManager = new RouteManager();
        return routeManager.getReport();
    }

}
