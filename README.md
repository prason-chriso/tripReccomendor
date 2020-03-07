# tripReccomendor
this is test project created that will suggest out the best trip time for the use base on the time they are accessing the servers
# TripReccomendor
this is test project created that will suggest out the best trip time for the use base on the time they are accessing the servers


============================================================================
#PROBLEM STATEMENT
============================================================================

There are two bus companies A and B which offer services from city C to D (one way only). Your task is to suggest the best bus company that can make you reach your destination faster.
Design a website that will take a file which has the bus timetable as input and display a table with the bus timings sorted according to departure time and you also have to suggest the user the next best bus available to travel to D depending upon the current system time.

Conditions:
•	Travel shouldn’t exceed more than 90 mins. If more ignore the bus
•	Always prefer B over A if the busses start at the same time
•	Pick the fastest bus company to reach the destination
•	Prefer the bus that reach the destination with less journey time if they start with a difference of 15-20 mins
•	Matching the above conditions chose the timings of the bus and print out its timings and the company name in the console
•	Current system time should be your current time.
•	Website should be attractive and responsive.  You are allowed to use third party libraries.


===========================================================================
#Front-end
===========================================================================
The frontend themes and design used is the modified template, Source is:
 
      Theme Name: Vlava
      Theme URL: https://bootstrapmade.com/vlava-free-bootstrap-one-page-template/
      Author: BootstrapMade.com
      Author URL: https://bootstrapmade.com



============================================================================
#ALGORITHM to determine Trip Priority level.
============================================================================

  Step1: Filter out the Trip schedule whose time has already passed
  Step2: Check if some Trip schedule have same start and end time if so then priority if given to the Trip that has bus name ="B".
  Step3: Check delayTimeFirst() > delayTimeSecond() if so then select Second Trip otherwise select First Trip
  Step4: Check transitTimeFirst() > TransitTimeSecond() if so then select Second Trip otherwise select First Trip
  Step5: if same trip is selected from step3 and step4 then no need to check further, return that trip
  Step6: if step 5 is not satisfied
          step 6.1 : if DelayTimeSecond() is already less than delayTimeFirst() then return whichever obect is choosen as best so far                         from step 1-5 and abort
          step 6.2 : if delayTimeSecond() < delayTimeFirst() + 20 min buffer then select second Trip otherwise select first Trip.


=======================================
#Process to upload Schedule
========================================

Example Time table file(can be a text or csv) delimeted by '|' should be uploaded on the resource folder of the project without changing the file name, Bus name must be A and B and time must be provided in the mentioned format with am and pm.

for eg: 
syntax => Company|Departure from C	|Arrival at D

++++++++++++++++++++++++++++++++++++++++++
+       A|	12:35 pm |	1:35 pm
+       B	|2:00 am|	2:45 pm
+       A|	2:00 am	|3:00 pm
+       B	|8:00 am|	9:45 am
+       B	|9:10 am |	10:15 am
+       A	|9:20 am|	10:10 am
+       B	|5:00 pm| 	6:00 pm
+++++++++++++++++++++++++++++++++++++++++

=========================================
#PUBLIC-API
=========================================

/ : this will load the dashboard of the System, default landing page 
1.  /schedule: This will generate the JSON data sequentially as is uploaded to the system
2.  /sortedSchedule : this will generate the JSON data for sorted schedule uploaded to the system
3.  /prioritySchedule: This will generate the prioritized JSON data of the trip and traveling based on the condition mentioned in the          problem Statement.
4.  /showResult: This will generate the JSON of the best Trip Daata and Message for the User using the System.