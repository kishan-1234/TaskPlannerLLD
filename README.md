TaskPlanner LLD

Requirements:
User should be able to create tasks, tasks can be different types: Feature, Story, Bug.
A task must have some basic details like: Title, Creator, Asignee, Status, DueDate. Task can have more details depending upon its type:
a. Feature must have Impact (Low, Medium, High), Summary
b. Bug must have severity (P0, P1, P2).
c. Story must have a summary & a story can have multiple subtracks. Subtracks are similar to tasks but they only have 2 details: Title, Status. Subtrack can only be part of a story
Tasks can have different status's depending upon type:
a. Feature - Open, In progress, Testing, Deployed
b. Bug - Open, In progress, Fixed, 
c. Story - Open, In progress, Compeleted (same for subtrack)
Based on task type, status transition might be different:
a. Feature: 
 Open -> In progress, 
 In progress -> Testing, 
 Testing -> Deployed, 
 In progress -> Deployed
b. Bug:
 Open -> Fixed,
 In progress -> Fixed
c. Story:
 Open -> In progress
 In progress -> Completed
Tasks can be added/removed from a sprint at any time. 
Asigne for a task can be changed irrespective wether task is part of sprint or not
User should be able to see sprint details - All tasks & their status, timeline (On track, delayed)
Tasks asigned to a aprticular user should be listed & categoried by their type
