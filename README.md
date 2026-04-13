# Imperial Maritime - Cruise Management System

A comprehensive Java-based desktop application for managing cruise operations, built with Swing GUI and MySQL database integration.

## Overview

Imperial Maritime is a cruise management system that allows administrators to manage cruises, workers, clients, and bookings. The application provides a user-friendly interface for performing CRUD operations on all system entities.

## Features

### Administrator Management
- Secure login system for administrators
- Password management functionality
- Multiple administrator accounts support

### Cruise Management
- Create, read, update, and delete cruise information
- Support for different cruise types: luxury, premium, family, expedition
- Track cruise capacity and room availability
- Manage cruise assignments for workers

### Worker Management
- Manage cruise staff including captains, cooks, guides, and receptionists
- Assign workers to specific cruises
- Track worker information and roles

### Client Management
- Maintain client database with personal information
- Age tracking for client demographics

### Booking System
- Record cruise bookings with client details
- Track travel routes (origin/destination cities)
- Manage booking dates and pricing
- Prevent double-bookings with composite primary keys

## Technology Stack

- **Language**: Java
- **GUI Framework**: Swing
- **Database**: MySQL
- **Architecture**: MVC (Model-View-Controller)
- **Build Tool**: Maven (inferred from project structure)

## Project Structure

```
src/
├── main/
│   └── Main.java                 # Application entry point
├── controller/
│   └── LoginController.java      # Main controller handling business logic
├── model/
│   ├── Administrator.java        # Administrator entity
│   ├── Client.java              # Client entity
│   ├── Cruise.java              # Cruise entity
│   ├── Worker.java              # Worker entity
│   ├── *DAO.java                # Data Access Objects for each entity
│   ├── DBImplementation*.java   # Database implementation classes
│   └── Type*.java               # Enum types for classifications
└── view/
    └── *Window.java             # Swing GUI windows and forms
```

## Database Schema

The application uses a MySQL database with the following tables:

- **administrator**: Admin login credentials
- **cruise**: Cruise information and specifications
- **worker**: Staff assigned to cruises
- **client**: Customer information
- **book**: Cruise booking records

## Setup Instructions

### Prerequisites
- Java JDK 8 or higher
- MySQL Server
- MySQL Connector/J (JDBC driver)

### Database Setup
1. Create a MySQL database named `imperialMaritime`
2. Run the SQL script located in `data/imperialMaritime.sql` to create tables and insert sample data

### Configuration
Update the database connection settings in `src/configClass.properties`:
```
DB=imperialMaritime
Conn=jdbc:mysql://localhost:3306/imperialMaritime?serverTimezone=Europe/Madrid&useSSL=false
DBUser=root
DBPass=your_password
Driver=com.mysql.jdbc.Driver
```

### Running the Application
1. Compile the Java source files
2. Ensure the MySQL server is running
3. Execute the main class: `Main.java`

## Usage

1. **Login**: Start the application and log in with administrator credentials
2. **Dashboard**: Access the main window with navigation to different management sections
3. **Manage Cruises**: Add, edit, or remove cruise information
4. **Manage Staff**: Assign and track cruise personnel
5. **Client Management**: Maintain customer database
6. **View Bookings**: Monitor cruise reservations

## Sample Data

The database comes pre-populated with sample data including:
- 4 administrator accounts
- 5 different cruises across various types
- 5 workers assigned to cruises
- 5 client records
- Sample booking entries

## Contributing

This project follows standard Java development practices with MVC architecture separation.

## License

This project is developed as part of a final project assignment.