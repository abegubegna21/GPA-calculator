# CGPA Calculator - Lab Exercise 01

A modular JavaFX desktop application and OOP solution designed according to the **OOP and GUI Review (Lab Exercise - 01)** requirements.

---

## 📌 Project Overview

### 1. Functional Requirements
- **Data Capture**: For each course:
  - Course Title (e.g. `Object Oriented Programming`)
  - Course Code (e.g. `CoSc2022`)
  - Credit Hours (e.g. `5`)
  - Letter Grade (e.g. `A`, `A-`, `B+`, `B`, `B-`, `C+`, `C`, `C-`, `D`, `F`)
- **Grading Logic (DBU Scale)**:
  - $A+ = 4.0$, $A = 4.0$, $A- = 3.75$
  - $B+ = 3.5$, $B = 3.0$, $B- = 2.75$
  - $C+ = 2.5$, $C = 2.0$, $C- = 1.5$
  - $D = 1.0$, $F = 0.0$
- **Calculations**:
  - $\text{Weighted Grade Point} = \text{Credit} \times \text{Grade Point}$
  - $\text{Total Credit Hours} = \sum \text{Credits}$
  - $\text{Total Grade Point} = \sum \text{Weighted Grade Points}$
  - $\text{CGPA} = \frac{\text{Total Grade Point}}{\text{Total Credit Hours}}$
- **Academic Status Determination**:
  - $\text{CGPA} \ge 2.00$ $\rightarrow$ **Pass**
  - $\text{CGPA} \ge 1.75$ $\rightarrow$ **Warning**
  - $\text{CGPA} \ge 1.50$ $\rightarrow$ **Readmission**
  - $\text{CGPA} < 1.50$ $\rightarrow$ **Academic Dismissal**

---

## 🏗️ Project Architecture & OOP Structure

```
javaLAB/
├── pom.xml                                      # Maven configuration (JavaFX + JUnit 5)
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/cgpacalculator/
│   │           ├── Main.java                    # Application launcher
│   │           ├── model/
│   │           │   ├── Course.java              # OOP Course entity (encapsulation)
│   │           │   └── GradeScale.java          # Letter grade to grade point mapping
│   │           ├── service/
│   │           │   └── CGPACalculator.java      # Calculation and academic status logic
│   │           └── ui/
│   │               └── CGPACalculatorApp.java   # JavaFX GUI matching the wireframe
│   └── test/
│       └── java/
│           └── com/cgpacalculator/
│               └── service/
│                   └── CGPACalculatorTest.java  # JUnit 5 unit tests
└── README.md
```

---

## 🚀 How to Run

### 1. Run Unit Tests
```bash
mvn test
```

### 2. Launch JavaFX Desktop Application
```bash
mvn javafx:run
```

Alternatively:
```bash
mvn clean compile exec:java -Dexec.mainClass="com.cgpacalculator.Main"
```

---

## 🧪 Unit Test Coverage
Includes 7 automated unit tests covering:
1. GradeScale mapping for all letter grades (A through F).
2. Course-level weighted grade point calculation ($Credit \times GP$).
3. Total credit summation ($5 + 4 + 5 + 5 = 19$).
4. Total weighted grade point summation.
5. CGPA computation ($Total\ Grade\ Point \div Total\ Credit$).
6. Academic status boundary thresholds (`Pass`, `Warning`, `Readmission`, `Academic Dismissal`).
7. Edge cases (empty lists, 0 credits).
