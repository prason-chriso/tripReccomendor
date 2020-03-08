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
  Step2: Check if some Trip schedule have same start and end time also their trip lenght is same . if so then priority if given to the Trip that has bus name ="B".
  Step3: Check delayTimeFirst() > delayTimeSecond() if so then select Second Trip otherwise select First Trip
  Step4: Check transitTimeFirst() > TransitTimeSecond() if so then select Second Trip otherwise select First Trip
  Step5: if same trip is selected from step3 and step4 then no need to check further, return that trip
  Step6: if step 5 is not satisfied
          step 6.1 : if DelayTimeSecond() is already less than delayTimeFirst() then return whichever obect is choosen as best so far                         from step 1-5 and abort
          step 6.2 : if delayTimeSecond() < delayTimeFirst() + 20 min buffer then select second Trip otherwise select first Trip.


============================================================================
#ALGORITHM Special Scenario
============================================================================
Above algorithm is designed to determined the priority level by comparing 1-1 but , due to the conflicting user requirment of

	1: if both start at same time priority to B and if the other has less trip time then priority to third one.
	2: if later trip has shorter trip lenght then priority to later trip but if later trip has multiple trip count of same start/end time 
		and the bus is also to be choosen among A&B
		
These two cases  consist of comparision of one -to- Many or Many-to-one object at same time so couldn't be handle by 
default working of USER-DEFINED ordering algorithm, which is "PRIORITY ALGORITHM" in our case.

Such scenarios are handled by new module that determines the highest priority.

=========================================
#EXECUTED-TEST CASES
=========================================
	1. If there is no data in schedule then empty dashboard should be displayed.
	2. if there is the time which has all passed at the time of accessing the system,
		it should suggest user for next day visit with appropriate message.
	3. Trip that is immediate to begin should be suggest to the user.
	
	4. All the condition of the problem statement must be satisfied by the system reccomendation.
	
	5.if two or more trip start at same time priority based on start/end time and earliest time,
		but the immidiate next has less trip time compared to all previous trip then priority should be shifted
		to the later one..
	6. if first trip has highest priority based on start/end time and earliest time among all other but,
		group of the trip has short trip time compared to first trip then best among the given group only should be 
		recommended by the system.
	7. User can wait for 20 min interval at max to catch up the next trip with less travel time.
	
	9. more than 90 min travel is automatically set to invalid and is rejected, if not /
		Otherwise, option of alternative trip is to be suggested to the user by the system.

=========================================
#PUBLIC-API
=========================================

/ : this will load the dashboard of the System, default landing page 
1.  /schedule: This will generate the JSON data sequentially as is uploaded to the system
2.  /sortedSchedule : this will generate the JSON data for sorted schedule uploaded to the system
3.  /prioritySchedule: This will generate the prioritized JSON data of the trip and traveling based on the condition mentioned in the          problem Statement.
4.  /showResult: This will generate the JSON of the best Trip Daata and Message for the User using the System.