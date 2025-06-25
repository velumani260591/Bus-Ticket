## 🚌 Online Bus Ticket Booking System

A Spring Boot based full-stack web application that allows users to book, view and cancel bus tickets. The system is designed with CRUD operations (Create, Read, Update, Delete) and follows MVC architecture using Thymeleaf, HTML/CSS/JS for frontend, and MySQL for backend database storage.

---

### 🚀 Features

* User Registration & Login (Authentication)
* Admin Panel to manage buses and schedules
* Book Bus Tickets (Create)
* View Ticket Details (Read)
* Update Ticket Info (Update)
* Cancel Tickets (Delete)
* View available buses based on date, time, and route
* Secure session handling and form validation

---

### 🔧 CRUD Operations Explained

#### 🎫 1. Create Ticket

* **Functionality**: Users can search for available buses and book tickets.
* **Tech Used**: Thymeleaf form → Spring Controller → TicketService → MySQL.
* **Endpoint**: `POST /tickets/book`
* **Entity**: `Ticket.java`

#### 📋 2. Read Tickets

* **Functionality**: View all booked tickets by a user or by admin.
* **Tech Used**: `GET` mapping in Controller with pagination support.
* **Endpoint**: `GET /tickets`, `GET /tickets/{id}`
* **Entity**: `TicketRepository.findAll()`

#### 🛠️ 3. Update Ticket

* **Functionality**: Allows users to update ticket details like travel date or seat number.
* **Tech Used**: Thymeleaf form prefilled with existing data.
* **Endpoint**: `PUT /tickets/{id}` or `POST /tickets/update/{id}`
* **Form**: Edit page for each ticket

#### ❌ 4. Delete Ticket

* **Functionality**: Allows users to cancel tickets.
* **Tech Used**: Delete button with confirmation dialog.
* **Endpoint**: `DELETE /tickets/{id}` or `GET /tickets/delete/{id}`

---

### 🛠️ Technologies Used

* **Backend**: Spring Boot, Spring Data JPA
* **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript
* **Database**: MySQL
* **Security**: Spring Security 
* **IDE**: IntelliJ 
* **Build Tool**: Maven

---

### 📁 Project Structure

```
src/
└── main/
    ├── java/
    │   └── com.example.Bus_Ticket_Booking/
    │       ├── Configuration/                # App configuration (e.g., Security, CORS)
    │       ├── Controller/                   # REST Controllers
    │       │   ├── AuthController.java
    │       │   ├── BusProviderController.java
    │       │   └── PassengerController.java
    │       ├── Dto/                          # DTOs for request/response
    │       ├── Entity/                       # Entity classes (mapped to DB)
    │       │   ├── Buses.java
    │       │   ├── BusProvider.java
    │       │   ├── Passenger.java
    │       │   └── TicketBooking.java
    │       ├── Repository/                   # JPA Repositories
    │       │   ├── BusesRepository.java
    │       │   ├── BusProviderRepository.java
    │       │   ├── PassengerRepository.java
    │       │   └── TicketBookingRepository.java
    │       ├── Service/                      # Interfaces
    │       │   ├── BusesService.java
    │       │   ├── BusProviderService.java
    │       │   ├── PassengerService.java
    │       │   └── TicketBookingService.java
    │       └── Service/impl/                 # Service Implementations
    │           ├── BusesServiceImpl.java
    │           ├── BusProviderServiceImpl.java
    │           ├── PassengerServiceImpl.java
    │           └── TicketBookingServiceImpl.java
    └── resources/
        ├── application.properties
        └── ...


---

### 🧪 How to Run

1. Clone the repository:

   ```
   git clone https://github.com/thrivendra1/Bus_ticket_bookings.git
   ```

2. Import into your IDE and run `OnlineBusTicketBookingApplication.java`.

3. Make sure your MySQL DB is configured properly in `application.properties`.

4. Access the app at: `http://localhost:8080`

---

### 🧑‍💻 Future Enhancements

* Integration with payment gateway
* Seat map and real-time booking
* Email confirmation after booking
* Mobile responsiveness

---

Let me know if you want a Markdown version or personalized GitHub README formatting!
