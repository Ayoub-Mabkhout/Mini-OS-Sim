# Mini-OS-Sim
This is repository for a 2-person term project in our CSC3351 Operating Systems Course.
It consists of a program simulating part of an operating system that, upon receiving procedurally generated processes, performs the tasks of scheduling said processes and managing memory frames following configurable scheduling and memory management schemes.

## Demonstrated Learning Outcomes
This projects is intended to demonstrate good understanding of multiple subtopic studied in class, with the main ones being:
* CPU Process Scheduling
  * First-Come First-Served(FCFS)
  * Round Robin (RR)
  * Shortest Process Next (SPN)
  * Shortest Remaining Time (SRT)
* OS-Level Memory Management (Page Replacement Algorithms)
  * First-In First-Out (FIFO)
  * Least Frequently Used (LFU)
  * Least Recently Used (LRU)
  * Clock
* Managing Concurrency & Multithreaded Programming
  * Thread Synchronization to lock resources.
  * Producer-Consumer Problem
* Measuring OS Performance
  * From the OS perspective
  * From the User perspective

## How to Run
This program requires that Java be installed on the user's computer. JRE is a must, as well as JDK 8 or higher. There are 2 ways to run the project.
- Run from an IDE: the project uses Java with Ant build settings;
- Run the executable jar file located in "./dist/Operating_System.jar" using OpenJDK Platform binary.

## Project Overview
The program consists of a Producer-Consumer problem where a process generator and a scheduler play the roles of producer and consumer respectively. The process generator proceduraly generates random processes, effectively simulating end-user activity in an OS setting. These processes are then enqueued in a global process queue from which the scheduler dequeues the next process to execute following one of 4 Scheduling Schemes. Some of the Scheduling algorithms allow for preemption of processes before they finish executing in order to ensure a reasonable response time for all processes. During the execution of a process, a Memory Manager takes care of replacing pages in a limited number of frames which serve to simulate pages being moved in and out of main memory. The Memory Manager also uses one of 4 different Page Replacement Algorithms, each with varying results in terms of a set of quantified performance metrics.

# Graphical User Interface
The program comes with a Java Swing graphical user interface in order to visualize the program flow and control a few configurable settings. Here is a list of the configurable settings:
* Scheduling Scheme: FCFS, RR, SPN, SRT;
* Memory Management Scheme: FIFO, LFU, LRU, Clock;
* Time Quantum, which is the time a process can run before being preempted in case the scheduling scheme is either RR or SRT.
* Time Unit (tu) conversion into Milliseconds: The flow of the whole program is dependent on a time unit, itself defined in terms of milliseconds. Double the length of a time unit and the entire program will run twice as slow.

Here is a screenshot of what the GUI looks like during execution:

![screenshot](https://user-images.githubusercontent.com/79266754/175012006-f7e9732d-1ef6-4f66-b0f4-133ed5825f87.PNG)

## Program Structure
The structure of the program is detailed in the project report PDF file. Checkout "./CSC3351-ProjectReport-Mabkhout-Moussa.pdf".

## A project by
#### Ayoub Mabkhout && Hanane Nour Moussa
