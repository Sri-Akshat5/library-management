# Library Management 
This is a spring boot application for learning and practice

## Tech Stack 
- Spring Boot
- JPA
- Thymeleaf
- Java 17
- mysql

## Endpoints 
Thymeleaf - http://localhost:8080/

## API Endpoints - Set headers for POST/PUT: Content-Type: application/json

### Book Endpoints (/api/books)
| Method | Endpoint                     | Description                  | 
| ------ | ---------------------------- | ---------------------------- | 
| GET    | `/books`                     | Get all books                | 
| GET    | `/books/{id}`                | Get book by ID               |              
| POST   | `/books`                     | Add new book                 |         
| PUT    | `/books/{id}`                | Update book                  |          
| DELETE | `/books/{id}`                | Delete book                  |             
| GET    | `/books/search?keyword=java` | Search books by title/author |              

### Sample Request Body for POST/PUT
```
{
  "bookName": "Java Programming",
  "author": "James Gosling",
  "isbn": "1234567890123",
  "totalCopies": 5,
  "availableCopies": 5
}
```

### Student Endpoints (/api/students)
| Method | Endpoint                        | Description             | 
| ------ | ------------------------------- | ----------------------- | 
| GET    | `/students`                     | Get all students        | 
| GET    | `/students/{id}`                | Get student by ID       | 
| POST   | `/students`                     | Add new student         | 
| PUT    | `/students/{id}`                | Update student          | 
| DELETE | `/students/{id}`                | Delete student          | 
| GET    | `/students/search?keyword=John` | Search students by name | 

### Sample Request Body for POST/PUT
```
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "9876543210"
}
```

### History Endpoints (/api/history)
| Method | Endpoint                               | Description                        | 
| ------ | -------------------------------------- | ---------------------------------- | 
| GET    | `/history`                             | Get all history records            | 
| GET    | `/history/search?keyword=John`         | Search history by student name     | 
| GET    | `/history/borrow/form-data`            | Get list of all students and books | 
| POST   | `/history/borrow?studentId=1&bookId=2` | Borrow a book                      | 
| PUT    | `/history/return/{id}`                 | Return a borrowed book             | 

