package com.example.controller;


import com.example.modal.TripSchedule;
import com.example.services.ReportManager;
import com.example.services.RouteManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;


@Controller
public class HomeController {
    Logger logger  = Logger.getLogger(HomeController.class);

    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }


    @GetMapping("/schedule")
    @ResponseBody
    public ArrayList<TripSchedule> getTodaySchedule() {
        logger.info("Requesting access for uploaded Schedule");
        RouteManager routeManager = new RouteManager();
        return routeManager.getRawUploadedSchedule();
    }

    @GetMapping("/sortedSchedule")
    @ResponseBody
    public ArrayList<TripSchedule> loadSortedSchedule() {
        logger.info("Requesting access for sorted Schedule");
        RouteManager routeManager = new RouteManager();
        return routeManager.getSortedSchedule();
    }

    @GetMapping("/prioritySchedule")
    @ResponseBody
    public ArrayList<TripSchedule> loadPrioritySchedule() {
        logger.info("Requesting access for priority Schedule");
        RouteManager routeManager = new RouteManager();
        return routeManager.getPrioritizedSchedule();
    }

    @GetMapping("/showResult")
    @ResponseBody
    public ReportManager getAdviceFromServer() {
        logger.info("Requesting access for trip recommendation  Report");
        RouteManager routeManager = new RouteManager();
        return routeManager.getReport();
    }

}
