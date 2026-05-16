# 🚗 ITOS – Intelligent Transportation Optimization System

**Pakistan Edition v3.0** | *Applications of Calculus in Software Engineering*

[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://www.oracle.com/java/)
[![Swing](https://img.shields.io/badge/GUI-Swing-orange)](https://docs.oracle.com/javase/tutorial/uiswing/)
![License](https://img.shields.io/badge/license-MIT-green)

ITOS is a desktop application that demonstrates **real‑world calculus applications** in software engineering. It models vehicle fuel consumption using a differentiable function $F(v) = a v^2 + b/v + c$, finds optimal speeds via $v^* = (b/(2a))^{1/3}$, and provides an interactive dashboard for drivers, analysts, and policy makers – specifically tailored for **Pakistan** (fuel prices in PKR, local vehicle fleet, population‑scale impact).

![Dashboard Preview](https://via.placeholder.com/800x450?text=ITOS+Dashboard+Screenshot)  
<img width="1365" height="715" alt="Screenshot 2026-04-25 131459" src="https://github.com/user-attachments/assets/79516099-8c92-4b7f-b8c5-9b554009246f" />
<img width="285" height="128" alt="Screenshot 2026-04-25 132811" src="https://github.com/user-attachments/assets/8ed3cdba-a65d-446d-a9f0-c888e01f8bef" />
<img width="296" height="107" alt="Screenshot 2026-04-25 132927" src="https://github.com/user-attachments/assets/98554d4b-bc58-4509-9ecc-a88b1e1fcb7c" />

<img width="275" height="177" alt="Screenshot 2026-04-25 132643" src="https://github.com/user-attachments/assets/456c8faa-10dd-436d-8fd2-2c3beab6ae72" />

<img width="282" height="125" alt="Screenshot 2026-04-25 132722" src="https://github.com/user-attachments/assets/87c8b9e4-21fa-4d0a-9990-881b51c18433" />



<img width="1180" height="586" alt="Screenshot 2026-04-25 133628" src="https://github.com/user-attachments/assets/a3116035-7491-4ca9-8bf1-3156403c3101" />

<img width="942" height="36" alt="Screenshot 2026-04-25 133338" src="https://github.com/user-attachments/assets/982d7d3a-64a4-4211-93d4-36ca4776db77" />





---

## ✨ Features

### 🔢 Core Calculus Engine
- **Fuel model**: $F(v) = a v^2 + \frac{b}{v} + c$ (air resistance + engine inefficiency + rolling friction)
- **Optimal speed**: $v^* = \left(\frac{b}{2a}\right)^{1/3}$ – derived by setting $\frac{dF}{dv}=0$ and confirming minimum via $\frac{d^2F}{dv^2}>0$
- **Trade‑off optimization**: $G(v) = \alpha \cdot F(v) + \beta \cdot T(v)$ where $T(v) = D/v$ (time cost)

### 🇵🇰 Pakistan‑Specific Data
- 7 real vehicles: Suzuki Mehran, Honda Civic, Toyota Corolla, Toyota Hilux, Hybrid, Electric, Honda CD70
- City/highway mileage curves, optimal speed per vehicle
- Current fuel prices (Petrol, Diesel, Octane, Electric) – editable by user
- Savings in PKR, CO₂ in kg, national‑level impact (millions of vehicles)

### 📊 Interactive Dashboard
- Real‑time metrics: fuel cost, time, CO₂, optimal speed, total cost, efficiency score (0–100)
- **Live graph** showing the $F(v)$ curve with your current speed and the optimal point
- Performance meters (fuel economy, time efficiency, CO₂ impact)
- Adaptive recommendation engine (e.g., “Reduce speed by 12 km/h to save Rs. 180”)

### 🏛️ Policy Simulator (National Impact)
- Set a hypothetical national speed limit
- Calculate fuel saved per vehicle and total national fuel/CO₂ reduction
- Mathematical basis: difference $F(80) - F(v_{policy})$ scaled by population and trips

### 🧪 Experiment Mode
- Modify the constants $a$, $b$, $c$ in real time
- Watch how the curve and optimal speed change – see the derivative and second derivative live
- Full calculus derivation panel with step‑by‑step equations

### 📁 Reports & Export
- **Export CSV** – current run data  
- **Save HTML Report** – formatted summary with history  
- **Print Preview** – plain‑text report  
- **Compare** – speed vs cost/time/CO₂, vehicle vs mileage

### 🎨 UI & Usability
- Premium dark theme (customizable light/dark via `Ctrl+H`)
- Keyboard shortcuts: `Ctrl+X` (CSV), `Ctrl+R` (HTML), `F1` (Help)
- Responsive layout, modern progress bars, score gauge, tooltips

---

## 📐 Mathematical Foundation (Calculus in SE)

| Component | Expression |
|-----------|-------------|
| Fuel consumption rate | $F(v) = a v^2 + \frac{b}{v} + c$ |
| First derivative | $\frac{dF}{dv} = 2a v - \frac{b}{v^2}$ |
| Critical point (set $dF/dv=0$) | $v^3 = \frac{b}{2a}$ |
| **Optimal speed** | $v^* = \left(\frac{b}{2a}\right)^{1/3}$ |
| Second derivative (minimum check) | $\frac{d^2F}{dv^2} = 2a + \frac{2b}{v^3} > 0$ |
| General trade‑off | $G(v) = \alpha F(v) D + \beta \cdot \frac{D}{v}$ (cost + time) |

These formulas are implemented in the `ITOS` class with numerical validation and analytic solvers.

---

## 🚀 Getting Started

### Prerequisites
- Java 17 or later (JDK 11+ should work, but 17 recommended)
- Swing is included in standard JDK – no external dependencies

### Running the Application
```bash
# Clone the repository
git clone https://github.com/your-username/ITOS-Intelligent-Transportation-Optimization-System.git
cd ITOS-Intelligent-Transportation-Optimization-System

# Compile
javac ITOS.java

# Run
java ITOS 
