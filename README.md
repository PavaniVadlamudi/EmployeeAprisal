Employee Management System - Spring Boot

This is a Spring Boot application for managing employees, providing RESTful APIs for CRUD operations, category distribution calculations, and rating change suggestions.

📌 Prerequisites

Before running the project, ensure you have the following installed:

Java 17 or later

Maven (for dependency management)

MySQL (or any preferred database)

Postman (for API testing, optional)

🛠️ Installation and Setup

1️⃣ Clone the Repository

git clone https://github.com/your-repo/springboot-employee-management.git
cd springboot-employee-management

2️⃣ Configure the Database

Modify src/main/resources/application.properties with your MySQL credentials:

spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver


3️⃣ Build and Run the Application

mvn clean install
mvn spring-boot:run

If successful, the application will start on http://localhost:8080

🚀 API Endpoints

📌 Employee Management

Method

Endpoint

Description

GET

/employees

Get all employees

GET

/employees/{id}

Get employee by ID

POST

/employees

Add new employee

DELETE

/employees/{id}

Delete employee

📊 Category & Rating Analysis

Method

Endpoint

Description

GET

/employees/actual-percentages

Get actual category distribution

GET

/employees/deviation

Get deviation from standard distribution

GET

/employees/suggest-rating-change

Suggest employees for rating change

🔥 Testing with Postman

➤ Add an Employee (POST)

URL: http://localhost:8080/employees

Method: POST

Body (raw JSON):

{
    "name": "Alice",
    "department": "HR",
    "category": "B",
    "rating": 5
}

➤ Get All Employees (GET)

URL: http://localhost:8080/employees

Method: GET

➤ Get Employee by ID (GET)

URL: http://localhost:8080/employees/1

Method: GET

➤ Delete Employee (DELETE)

URL: http://localhost:8080/employees/1

Method: DELETE
