# 🎓 Course Management System

A **Spring Boot** application that provides REST APIs for managing courses, students, teachers, grades, and course materials in an educational institution.

## 🚀 Features
- **User Management**: Create, update, and delete students and teachers.
- **Course Management**: Assign teachers, enroll students, and manage course materials.
- **Grade Management**: Assign and update student grades.
- **Pagination & Sorting**: Efficient data retrieval for large datasets.
- **JWT Authentication**
---

## 🛠️ Tech Stack
- **Backend**: Java 17, Spring Boot, Spring Data JPA, Hibernate
- **Database**: MySQL
- **Security**: JWT 
- **Testing**: JUnit 5, Mockito
- **API Documentation**: Swagger UI

---

## 📌 Setup Instructions

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/lvrgese/course-management-system.git
cd course-management-system
```

### 2️⃣ Configure the Database
Edit `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/course_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ Build and Run the Application
```sh
mvn clean install
mvn spring-boot:run
```

### 4️⃣ Test the APIs
- Swagger UI: `http://localhost:8080/api/docs/index.html`
- Sample Endpoints:
  - `POST /api/courses` → Create a new course
  - `GET /api/courses/{id}/students` → Get students enrolled in a course
  - `POST /api/students` → Register a new student
  - `POST /api/grades` → Assign a grade

---

## 📜 API Endpoints Overview

### 🎓 **Course Management**
- `POST /api/courses` → Create a course
- `GET /api/courses/{id}` → Get course details
- `PUT /api/courses/{id}` → Update a course
- `DELETE /api/courses/{id}` → Delete a course
- `POST /api/courses/{courseId}/students/{studentId}` → Enroll student in a course
- `GET /api/courses/{id}/students` → Get all students in a course

### 👨‍🏫 **Teacher Management**
- `POST /api/teachers` → Create a teacher
- `GET /api/teachers/{id}` → Get teacher details
- `GET /api/teachers/{id}/courses` → Get courses assigned to a teacher

### 🧑‍🎓 **Student Management**
- `POST /api/students` → Register a student
- `GET /api/students/{id}` → Get student details
- `GET /api/students/{id}/courses` → Get courses a student is enrolled in

### 🏆 **Grade Management**
- `POST /api/grades` → Assign a grade
- `GET /api/grades/students/{studentId}/courses/{courseId}` → Get a student's grade for a course

### 📚 **Course Materials**
- `POST /api/materials` → Add course material
- `GET /api/materials/{id}` → Get course material
- `DELETE /api/materials/{id}` → Delete course material



