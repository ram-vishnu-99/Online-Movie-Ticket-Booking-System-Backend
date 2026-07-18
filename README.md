[README.md](https://github.com/user-attachments/files/30146515/README.md)
# CyberCinema - Online Movie Ticket Booking System

A premium, full-stack Online Movie Ticket Booking System featuring a responsive, dark glassmorphic React frontend and a Spring Boot (Java) backend connected to a MySQL database.

---

## 🚀 Modules & Features

### 👤 Customer Portal
* **Browse Movies**: A beautiful grid catalog of movies with search and genre-filtering capabilities.
* **Showtimes Selector**: Dynamically browse scheduling times, ticket pricing, and location indicators for chosen movies.
* **Seat Selection**: An interactive theater map to view occupied vs available seats, pick custom seat coordinates, and calculate costs in real-time.
* **Fidelity Ticket Invoice**: Printable digital tickets rendered with booking metadata and simulated admission barcodes / QR codes.
* **Ticket History**: Browse past bookings and retrieve details at any time.

### 🎭 Theater Manager Portal
* **Show Scheduling**: Form wizard mapping films to theater rooms, setting start/end ranges and seat pricing.
* **Seating Auditing**: Read-only seat matrix layouts detailing booking metrics, occupancy ratios, and estimated revenues per show.

### ⚙️ Administrator Portal
* **Movie CRUD**: Create, edit, and delete movie catalog records, including synopsis information and poster links.
* **Theater CRUD**: Set up screen dimensions (dynamic row & column grids) to control capacity definitions.
* **Revenue Analytics**: Business dashboard calculating aggregate gross earnings, total ticket sales, and sales distributions across films and theaters.

---

## 🛠️ Technology Stack
* **Frontend**: React.js (Vite compiler), React Router, Vanilla CSS Variables.
* **Backend**: Java 17+, Spring Boot (3.2.2), Spring Data JPA, Hibernate, Lombok.
* **Database**: MySQL (Default), H2 Database (Dev profile fallback).

---

## 📂 Directory Structure

```text
movie-ticket-booking/
├── backend/
│   ├── src/main/java/com/movie/booking/
│   │   ├── controller/     # REST Endpoints (Auth, Movies, Bookings, Shows, Theaters)
│   │   ├── entity/         # JPA Entities / Models
│   │   ├── repository/     # JPA Data persistence repositories
│   │   └── service/        # Business logic services (Seat booking transactions, seeder)
│   ├── src/main/resources/
│   │   └── application.properties  # Database connection profiles (MySQL / H2)
│   └── pom.xml             # Backend Maven build file
│
└── frontend/
    ├── src/
    │   ├── pages/          # Pages (Login, Customer, Manager, Admin)
    │   ├── App.jsx         # Client routers & main navbar layout
    │   ├── index.css       # Custom glassmorphic CSS styling system
    │   └── main.jsx        # React entrypoint
    ├── package.json        # Frontend NPM configurations
    └── vite.config.js      # Vite compiler setup
```

---

## ⚙️ Prerequisites
Ensure you have the following installed:
* **Java Development Kit (JDK) 17+**
* **Node.js (v18+)**
* **MySQL Server** (running, typically on port `3306`)

---

## 🏁 How to Get Started

### 1. Database Setup (MySQL)
By default, the project is configured to look for a MySQL server on `localhost:3306` with username `root` and password `root`.

1. Ensure your MySQL server is running.
2. Open `backend/src/main/resources/application.properties` to edit details:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/movie_booking_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=YOUR_MYSQL_ROOT_PASSWORD
   ```
3. The schema `movie_booking_db` and tables will be created automatically on backend startup.

*(Note: To switch to an in-memory **H2 database** with no local setup, comment out the MySQL block and uncomment the Dev H2 block in `application.properties`.)*

### 2. Run the Backend Server
In your terminal, navigate to the backend folder:
```bash
cd backend
```
Compile and run the Spring Boot app using Maven:
```bash
mvn spring-boot:run
```
*The server will start on [http://localhost:8080](http://localhost:8080).*

### 3. Run the Frontend Client
In a new terminal window, navigate to the frontend folder:
```bash
cd frontend
npm install
npm run dev
```
*The React client will start on [http://localhost:5173](http://localhost:5173). Open this URL in your web browser.*

---

## 🔑 Seeding & Test Credentials
On initial startup, the database seeder automatically creates the following test user profiles for immediate evaluation:

| User Role | Username | Password | Email |
| :--- | :--- | :--- | :--- |
| **Customer** | `customer` | `customer` | `customer@movie.com` |
| **Theater Manager** | `manager` | `manager` | `manager@movie.com` |
| **Administrator** | `admin` | `admin` | `admin@movie.com` |
