# Design-pattern-Project
# 🚦 Smart Traffic Control & Violation Management System

A **Java (Maven) desktop application** that simulates traffic signal control and detects traffic violations in a smart intersection environment.  
The project is designed mainly for **Software Design Patterns** practice, with a clean and modern UI built using **FlatLaf (Dark / Light)**.

---

## 📌 Project Overview

The system simulates real-time traffic flow using a **tick-based engine**, manages traffic lights using interchangeable control algorithms, and detects different types of traffic violations through a structured processing pipeline.

The project focuses on applying **object-oriented design principles** and implementing multiple **design patterns** in a practical and understandable way.

---

## ✨ Key Features

- **Traffic simulation engine** (tick-based execution)
- **Violation detection pipeline**
  - Speeding violations
  - Signal violations (crossing stop line)
  - Emergency vehicle handling
- **Auto Run mode** (1 tick per second)
- **Add Vehicle tool**
  - Plate number
  - Speed
  - Stop-line crossing flag
  - Emergency vehicle flag
- **User Interface Enhancements**
  - FlatLaf **Dark & Light themes**
  - **Theme toggle** (Dark ↔ Light)
  - Toolbar **icons**
  - Table row coloring based on violation type
  - **Last Violation** badge displayed above the table

---

## 🧩 Design Patterns Used

### 1️⃣ Abstract Factory  
Used to create different intersection configurations dynamically.

**Classes:**
- `IntersectionFactory`
- `NormalIntersectionFactory`
- `HighwayIntersectionFactory`
- `SchoolZoneIntersectionFactory`

---

### 2️⃣ Bridge  
Separates the **TrafficLight abstraction** from the **control algorithm implementation**, allowing algorithms to change independently.

**Structure:**
- Abstraction: `TrafficLight`, `BasicTrafficLight`
- Implementation: `ControlAlgorithm`
- Algorithms: `FixedTimerAlgorithm`, `AdaptiveTrafficAlgorithm`

---

### 3️⃣ Chain of Responsibility  
Processes vehicle events step-by-step until a final violation decision is produced.

**Pipeline Components:**
- `LoggingHandler`
- `EmergencyVehicleHandler`
- `SpeedCheckHandler`
- `SignalViolationHandler`
- `FineCalculationHandler`

---

### 4️⃣ Singleton  
Provides a single global access point for managing and storing violations.

**Class:**
- `TrafficControlCenter`

---

### 5️⃣ Builder
Used to construct traffic scenarios in a clean and readable way.

**Class:**
- `TrafficScenario.Builder`

---

## 🧱 Project Structure

```
src/
 ├── control/
 │   ├── algorithms/
 │   ├── bridge/
 │   └── trafficlight/
 ├── factory/
 │   └── intersections/
 ├── handlers/
 │   └── chain/
 ├── model/
 ├── scenario/
 ├── ui/
 │   ├── components/
 │   └── themes/
 └── main/
     └── Main.java
```

---

## ▶️ How to Run the Project

1. Make sure **Java JDK 17+** is installed.
2. Open the project using **IntelliJ IDEA** or **NetBeans**.
3. Ensure **Maven** dependencies are loaded automatically.
4. Run the main class:
   ```
   src/main/Main.java
   ```
5. Use the toolbar to:
   - Add vehicles
   - Start Auto Run mode
   - Switch themes (Dark / Light)

---

## 🛠️ Technologies Used

- **Java**
- **Maven**
- **Swing**
- **FlatLaf**
- **Object-Oriented Design**
- **Design Patterns**

---

## 👥 Team Members

| Full Name | Student ID | University Email |
|---------|------------|------------------|
| Osama Abdelhakim | 192100032 | 192100032@ecu.edu.eg |
| Yousef Abdelhady | 192100121 | 192100121@ecu.edu.eg |
| Ahmed Ashraf | 192100128 | 192100128@ecu.edu.eg |
| Mohamed Elshazly | 191900035 | 191900035@ecu.edu.eg |
| Ahmed Montaser | 192100054 | 192100054@ecu.edu.eg |
| Ali Hazim | 192100116 | 192100116@ecu.edu.eg |

---

## 🎓 Academic Context

- **Course:** Software Design Patterns  
- **University:** Egyptian Chinese University (ECU)  
- **Academic Year:** 2025 – 2026

---

## 🚀 Future Enhancements

- Integration with real-time traffic sensors
- Advanced AI-based traffic prediction
- Exporting violation reports (PDF / CSV)
- Multi-intersection simulation
- Cloud-based monitoring dashboard

---

## 📄 License

This project is developed for **educational purposes only**.
