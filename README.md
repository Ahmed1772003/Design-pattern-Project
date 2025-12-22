# 🚦 Smart Traffic Control & Violation Management System
**Design Patterns Project (Java / Maven / Swing)**

A Java desktop application that simulates traffic signal control and detects traffic violations in a smart intersection environment.  
Built mainly for **Software Design Patterns** practice, with a modern UI using **FlatLaf (Dark / Light)**.

---

## 📌 Project Overview
This system simulates real-time traffic flow using a **tick-based engine**, manages traffic lights through interchangeable control algorithms, and detects traffic violations using a structured processing pipeline.

The project focuses on applying **object-oriented principles** and showcasing multiple **design patterns** in a realistic and understandable way.

---

## ✨ Key Features
- **Tick-based traffic simulation engine**
- **Violation detection pipeline**
  - Speeding violations
  - Signal violations (crossing the stop line)
  - Emergency vehicle handling
- **Auto Run mode** (1 tick per second)
- **Add Vehicle tool**
  - Plate number
  - Speed
  - Stop-line crossing flag
  - Emergency vehicle flag
- **UI Enhancements**
  - FlatLaf **Dark & Light themes**
  - Theme toggle (Dark ↔ Light)
  - Toolbar icons
  - Table row coloring based on violation type
  - **Last Violation** badge displayed above the table

---

## 🧩 Design Patterns Used

### 1) Abstract Factory
Used to create different intersection configurations dynamically without coupling the system to specific implementations.

**Classes**
- `IntersectionFactory`
- `NormalIntersectionFactory`
- `HighwayIntersectionFactory`
- `SchoolZoneIntersectionFactory`

---

### 2) Bridge
Separates the **TrafficLight abstraction** from the **control algorithm implementation**, allowing algorithms to change independently.

**Structure**
- Abstraction: `TrafficLight`, `BasicTrafficLight`
- Implementor: `ControlAlgorithm`
- Algorithms: `FixedTimerAlgorithm`, `AdaptiveTrafficAlgorithm`

---

### 3) Chain of Responsibility
Processes vehicle events step-by-step until a final violation decision is produced.

**Pipeline Handlers**
- `LoggingHandler`
- `EmergencyVehicleHandler`
- `SpeedCheckHandler`
- `SignalViolationHandler`
- `FineCalculationHandler`

---

### 4) Singleton
Provides a single global access point for managing and storing detected violations.

**Class**
- `TrafficControlCenter`

---

### 5) Builder
Used to construct traffic scenarios in a clean and readable way.

**Class**
- `TrafficScenario.Builder`

---

## ▶️ How to Run

### ✅ Requirements
- **Java JDK 17+**
- **Maven**
- IDE: IntelliJ IDEA or NetBeans (recommended)

2. استنى Maven يحمّل الـ dependencies تلقائيًا.
3. شغّل الـ main class:
