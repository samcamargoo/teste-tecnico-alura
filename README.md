# Alura Backend Developer

This project is an API built using Java, Spring Boot and Spring Security.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Available users for testing](#available-users-for-testing)
- [Adding new users](#adding-new-users)

## Installation
1. Install docker:
    ```bash
    https://www.docker.com/get-started/
    ```

2. Clone the repository:
    ```bash
    git clone https://github.com/samcamargoo/teste-tecnico-alura.git
    ```

3. Enter the project directory
   ```bash
    cd teste-tecnico-alura
    ```

3. Run docker
   ```bash
    docker-compose up -d --build
    ```    

## Usage
1. The API will be accessible at http://localhost:8080

## API Endpoints
- Check http://localhost:8080/swagger-ui/index.html#/

## Available users for testing
```bash
id: 1
username: admin
password: 123

id: 2
username: instructor
password: 123
```

## Adding new users
If you want to add new users (administrator or instructor), please follow these steps.
- Create a new migration file called V10__insert-into-table-users.sql and add the snippet bellow
```bash
INSERT IGNORE INTO tb_users(id, name, username, email, password)
    VALUES
    (3, 'name', 'username', 'email@email.com', '$2a$12$7H5r9rR6LObbl5/QRjvbbufJLjL9fX1/DCQzYz1IRfUn/3iB1PpJa'),
    (4, 'name' , 'username1', 'email1@email.com', '$2a$12$7H5r9rR6LObbl5/QRjvbbufJLjL9fX1/DCQzYz1IRfUn/3iB1PpJa');

INSERT IGNORE INTO tb_user_roles (user_id, role_id)
    VALUES
    (3, 3),
    (4, 2);
```
The migration might fail, so check if there is no users with the same ID

Please note that the password is encoded using bcrypt https://bcrypt-generator.com/
