# REST and RESTful Web Services  (as project from a university course (task 7))
## Task


REST-based Meeting Scheduling Server (simple doodle clone) - Programming Task

The goal of this programming task is a simplified meeting scheduling tool used to internally schedule meetings with friends or other students. Here, you have the chance to study concepts of REST and HATEOAS. See the lecture slides as well as additional resources, e.g. regarding API-Design. 

First, you will design a simple RESTful HTTP API which enables a user to propose several meeting times, remove and add times and publish a link of this doodle-like list encoded in JSON-format. 

Users shall be able to choose zero or more preferred meeting times from this list. 

Second, build a RESTful server based on Spring Boot which prototypically implements the meeting scheduling server API. Bonus: Use HATEOAS to navigate to related resources, i.e. meeting schedules. 

Third, test the meeting scheduling-server with a rest-client or plugin. No UI is required.