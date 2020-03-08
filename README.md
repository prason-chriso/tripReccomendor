# tripReccomendor
this is test project created that will suggest out the best trip time for the use base on the time they are accessing the servers
# TripReccomendor
this is test project created that will suggest out the best trip time for the use base on the time they are accessing the servers

=======================================
#Instruction for Deployment
========================================

1.This system takes on the schedule data which is pipe seperated only.
2.The schedule data should n't indule any date value i.e only time information is provided.
3.If the trip is started and completed on different dates then it should be mentioned in the schedule file,
	otherwise default 1 is to be mentioned explicitly
4.If there is any invalid character detected in the time format then that particular record is skipped.
5.If the schedule file is not 100% correct then the schedule order may be affected however, the other valid data wont be affected.
6.Upload the data to the schedule file manually otherwise there is nothing visible at the front end.
7.This project is compatible and tested on tomcat version 7.0.9 and compiled with java version 1.8. and build with maven version 3.6.1

=======================================
#Process to upload Schedule
========================================

Example Time table file(can be a text or csv) delimeted by '|' should be uploaded on the resource folder of the project without changing the file name, Bus name must be A and B and time must be provided in the mentioned format with am and pm.

for eg: 
syntax => Company|Departure from C	|Arrival at D|TripCoversHowManyDays(dates)

++++++++++++++++++++++++++++++++++++++++++
+       A|	12:35 pm |	1:35 pm|1
+       B	|2:00 am|	2:45 pm|1
+       A|	2:00 am	|3:00 pm|1
+       B	|8:00 am|	9:45 am|1
+       B	|5:00 pm| 	6:00 am|2
+       B	|9:10 am |	10:15 am|1
+       A	|9:20 am|	10:10 am|1
+       B	|5:00 pm| 	6:00 pm|1
+       B	|11:50 pm| 	12:05 am|2

+++++++++++++++++++++++++++++++++++++++++


