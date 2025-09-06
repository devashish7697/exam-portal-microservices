# Exam Portal Microservices

This is a **learning project** built with **Spring Boot (Java)** and **React** to practice **Microservices architecture**.

## 🚀 Microservices Implemented
- **Exam Service (8081)**
    - Manage exams (create, update, delete, fetch).
    - Stores list of question IDs.
    - Communicates with Question Service to fetch full question details.

- **Question Service (8082)**
    - Manage questions (CRUD).
    - Each question has subject, text, options (A–D), and correct answer.

## 📦 Tech Stack
- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Cloud OpenFeign
- MySQL
- Maven
- (Future: Docker, Spring Cloud Gateway, Eureka, JWT Auth)

## 📂 Project Structure
