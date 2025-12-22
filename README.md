Design Pattern Project
🚦 Smart Traffic Control & Violation Management System

A Java (Maven) desktop application developed to simulate traffic signal control and detect traffic violations within a smart intersection environment.
This project is mainly intended for practicing Software Design Patterns, featuring a clean and modern user interface built using FlatLaf (Dark / Light).

📌 Project Overview

The system simulates real-time traffic flow through a tick-based execution engine, controls traffic lights using interchangeable algorithms, and detects multiple types of traffic violations using a structured processing pipeline.

The project emphasizes the application of object-oriented design principles and demonstrates how common design patterns can be applied in a practical and realistic software system.

✨ Key Features

Traffic simulation engine based on tick-by-tick execution

Traffic violation detection pipeline, including:

Speed limit violations

Traffic signal violations (crossing the stop line)

Emergency vehicle handling

Auto Run mode (1 tick per second simulation)

Vehicle creation tool with:

Plate number

Vehicle speed

Stop-line crossing indicator

Emergency vehicle flag

User Interface enhancements

FlatLaf Dark & Light themes

Theme switching (Dark ↔ Light)

Toolbar icons

Table row coloring based on violation type

Display of the Last Detected Violation above the table

🧩 Design Patterns Used
1️⃣ Abstract Factory

Used to dynamically create different intersection configurations without coupling the system to concrete classes.

Main Classes:

IntersectionFactory

NormalIntersectionFactory

HighwayIntersectionFactory

SchoolZoneIntersectionFactory

2️⃣ Bridge

Decouples the TrafficLight abstraction from the control algorithm implementation, allowing both to evolve independently.

Structure:

Abstraction: TrafficLight, BasicTrafficLight

Implementor: ControlAlgorithm

Concrete Algorithms: FixedTimerAlgorithm, AdaptiveTrafficAlgorithm

3️⃣ Chain of Responsibility

Handles vehicle events through a sequence of processing steps until a final violation decision is reached.

Processing Handlers:

LoggingHandler

EmergencyVehicleHandler

SpeedCheckHandler

SignalViolationHandler

FineCalculationHandler

4️⃣ Singleton

Ensures a single shared instance responsible for managing and storing all detected violations.

Class:

TrafficControlCenter

5️⃣ Builder

Used to construct traffic simulation scenarios in a clear and readable manner.

Class:

TrafficScenario.Builder

▶️ How to Run the Project

Ensure Java JDK 17 or later is installed.

Open the project using IntelliJ IDEA or NetBeans.

Allow Maven to automatically download dependencies.

Run the main application from:

src/main/Main.java


Use the toolbar to:

Add vehicles

Enable Auto Run mode

Switch between Dark and Light themes

🛠️ Technologies Used

Java

Maven

Swing

FlatLaf

Object-Oriented Programming

Software Design Patterns

👥 Team Members
Full Name	Student ID	University Email
Osama Abdelhakim	192100032	192100032@ecu.edu.eg

Yousef Abdelhady	192100121	192100121@ecu.edu.eg

Ahmed Ashraf	192100128	192100128@ecu.edu.eg

Mohamed Elshazly	191900035	191900035@ecu.edu.eg

Ahmed Montaser	192100054	192100054@ecu.edu.eg

Ali Hazim	192100116	192100116@ecu.edu.eg
🎓 Academic Context

Course: Software Design Patterns

University: Egyptian Chinese University (ECU)

Academic Year: 2025 – 2026

🚀 Future Enhancements

Integration with real-time traffic sensors

Advanced AI-based traffic prediction

Exporting violation reports (PDF / CSV)

Multi-intersection traffic simulation

Cloud-based monitoring and analytics dashboard

📚 References

• Freeman, E., Robson, E., Bates, B., & Sierra, K. (2020). Head First Design Patterns (2nd ed.). O’Reilly Media.

• ChatGPT – Project idea generation, UI icon suggestions, and assistance in resolving implementation issues.

• FlatLaf – Flat Look and Feel for Java Swing Applications.
https://www.formdev.com/flatlaf/

• Course Lectures – Software Design Patterns lecture materials.
