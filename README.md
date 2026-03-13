# EcommerceApplication

Backend API cho hệ thống thương mại điện tử, xây dựng với Spring Boot theo kiến trúc module (Auth, Product, Category, Cart, Order).

## 1. Overview

Mục tiêu dự án là cung cấp REST API cho các nghiệp vụ cơ bản của một hệ thống ecommerce:

- Quản lý người dùng và xác thực bằng JWT (access/refresh token qua HttpOnly cookie)
- Quản lý danh mục và sản phẩm
- Quản lý giỏ hàng
- Đặt hàng và xem lịch sử đơn hàng

## 2. Tech Stack

- Java 21
- Spring Boot 4.0.2
- Spring Web MVC
- Spring Data JPA + Hibernate
- Spring Security
- JWT (`jjwt`)
- MySQL
- MapStruct
- Lombok
- Springdoc OpenAPI (Swagger UI)
- Maven (kèm Maven Wrapper)

## 3. Getting Started

### 3.1 Prerequisites

- JDK 21
- MySQL 8+
- Git

### 3.2 Clone source code

```bash
git clone <your-repo-url>
cd EcommerceApplication
```

### 3.3 Create database

```sql
CREATE DATABASE ecommerce_db;
```

### 3.4 Configure application

File cấu hình chính: `src/main/resources/application.properties`

| Key | Default value | Mô tả |
| --- | --- | --- |
| `server.port` | `8080` | Port chạy API |
| `spring.datasource.url` | `jdbc:mysql://localhost:3306/ecommerce_db?...` | JDBC URL MySQL |
| `spring.datasource.username` | `root` | DB username |
| `spring.datasource.password` | `12345` | DB password |
| `spring.jpa.hibernate.ddl-auto` | `update` | Cơ chế migrate schema cho môi trường local |
| `spring.jpa.show-sql` | `true` | In SQL ra log |

Khuyến nghị: không dùng credential mặc định cho môi trường thật.

### 3.5 Run application

Linux/Mac:

```bash
./mvnw spring-boot:run
```

Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

API base URL: `http://localhost:8080`

## 4. Usage

### 4.1 API documentation

Swagger UI:

`http://localhost:8080/swagger-ui/index.html`

### 4.2 Auth flow (cookie-based)

1. `POST /api/auth/register`
2. `POST /api/auth/login`  
   Server trả về và set `accessToken`, `refreshToken` vào HttpOnly cookies.
3. `POST /api/auth/refresh`
4. `POST /api/auth/logout`

### 4.3 Main endpoints

- Auth: `/api/auth/*`
- Products: `/api/products`
- Categories: `/api/categories`
- Cart: `/api/cart`
- Orders: `/orders`

### 4.4 API testing resources

- Postman collection: `postman/collections/EcommerceAPI.postman_collection.json`
- HTTP examples: `postman/test.http`

## 5. Build, Test, Run

Chạy unit/integration tests:

```bash
./mvnw test
```

Windows PowerShell:

```powershell
.\mvnw.cmd test
```

Build package:

```bash
./mvnw clean package
```

Chạy file JAR sau khi build:

```bash
java -jar target/EcommerceApplication-0.0.1-SNAPSHOT.jar
```

## 6. Project Structure

```text
EcommerceApplication/
├── src/
│   ├── main/
│   │   ├── java/org/example/ecommerceapplication/
│   │   │   ├── auth/       # Authentication, refresh token, auth service/controller
│   │   │   ├── user/       # User entity, repository, mapper, service
│   │   │   ├── category/   # Category CRUD
│   │   │   ├── product/    # Product CRUD + stock operations
│   │   │   ├── cart/       # Cart and cart item operations
│   │   │   ├── order/      # Place order, history, order items
│   │   │   └── common/     # Security, config, exceptions, base entity, enums
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/org/example/ecommerceapplication/
├── postman/
│   ├── collections/
│   ├── globals/
│   └── test.http
├── pom.xml
├── mvnw
└── mvnw.cmd
```

## 7. Security & Environment Notes

- Dự án đang dùng JWT filter + HttpOnly cookies cho auth flow.
- Trong cấu hình hiện tại, `SecurityConfig` cho phép tất cả request (`anyRequest().permitAll()`), phù hợp giai đoạn development/test; cần siết lại rule trước khi đưa production.
- CORS đã bật cho các origin local phổ biến (`localhost:3000`, `4200`, `8080`).


## 8. Contributing

Gợi ý quy trình làm việc:

1. Tạo branch mới từ `main`
2. Commit theo scope rõ ràng, message ngắn gọn
3. Chạy test trước khi tạo Pull Request
4. Mô tả thay đổi và ảnh hưởng trong PR

## 9. License

Hiện tại repository chưa khai báo file license riêng. Nên bổ sung `LICENSE` để chuẩn hóa quyền sử dụng và phân phối.
