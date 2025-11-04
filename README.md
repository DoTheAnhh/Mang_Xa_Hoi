# 🧩 JSP Spring Boot RBAC Project

## 🏗️ 1. Cấu trúc dự án

```
src/
├── main/
│   ├── java/com/example/test_project/
│   │   ├── common/                         # ApiResponse, SearchParams
│   │   ├── config/                         # WebConfig, CORS, view resolver
│   │   ├── controller/                     
│   │   │   └── render/                     # Controller hỗ trợ hiển thị JSP/HTML
│   │   ├── dto/                            # DTO (request, response)
│   │   ├── entity/              
│   │   ├── exception/
│   │   │   ├── GlobalExceptionHandler      # Bắt và xử lý tất cả exception phát sinh trong project
│   │   │   └── ValidationException         # Custom exception dùng cho các lỗi nghiệp vụ riêng, 
│   │   │                                     ném ra trong service hoặc controller và tự động được GlobalExceptionHandler xử lý.
│   │   ├── repository/          
│   │   ├── security/                       # JWT + phân quyền
│   │   ├── service/                        # Logic nghiệp vụ (validate, ...)
│   │   ├── util/                           # DynamicFilter, SpecificationBuilder
│   │   │   ├── DynamicFilter               # Chuyển đổi các điều kiện lọc linh hoạt từ client 
│   │   │   │                                 (ví dụ từ body JSON hoặc DTO SearchParams) thành Predicate trong JPA Specification.
│   │   │   └── SpecificationBuilder        # Lớp trung tâm để kết hợp nhiều điều kiện DynamicFilter thành một Specification<T>
│   │   │                                     cho phép kết hợp nhiều điều kiện với AND hoặc OR logic
│   │   └── MangXaHoiApplication.java         
│   ├── resources/
│   │   ├── static/                         # File tĩnh (CSS, JS)
│   │   ├── templates/                      # login.html, user.html
│   │   └── application.properties
│   └── webapp/                             # JSP nếu có
└── test/                                   # Unit test
```

---

## ⚙️ 2. Cách chạy project

### Bước 1: Cài môi trường
- **JDK 17+**
- **Maven 3.9+**
- **SQL Server**
- **Spring Boot 3.3+**

### Bước 2: Cấu hình file `application.properties`
> Đã có sẵn ánh xạ tới database ví dụ `DBTesting`.  
> Không cần chỉnh sửa nếu đã import database mẫu.

### Bước 3: Chạy ứng dụng
```bash
mvn spring-boot:run
```

Ứng dụng khởi động tại:
```
http://localhost:8080
```

---

## 🔐 3. Phân quyền trong hệ thống

### Các bảng chính

| Bảng | Mô tả                               |
|------|-------------------------------------|
| **User** | Thông tin tài khoản người dùng      |
| **Role** | Vai trò (VD: ADMIN, USER)           |
| **Permission** | Quyền cụ thể (PATH, METHOD)         |
| **UserRole** | Quan hệ N-N giữa User và Role       |
| **RolePermission** | Quan hệ N-N giữa Role và Permission |

### Cơ chế hoạt động

1. Người dùng đăng nhập qua `/auth/login` → server trả JWT token.
2. Token được gửi trong header `Authorization: Bearer <token>` khi gọi API.
3. `JwtAuthenticationFilter` xác thực token và load danh sách quyền.
4. `PermissionFilter` kiểm tra quyền truy cập endpoint.

---

## 🧠 4. API phân quyền ví dụ

### `POST /user/list`
**Mục đích:** Lấy danh sách người dùng (chỉ người có quyền `VIEW_USER` mới truy cập được).

**Header yêu cầu:**
```http
Authorization: Bearer <jwt_token>
Content-Type: application/json
```

**Body (tùy chọn):**
```json
{
  "code": "Anh",
  "name": "Anh"
}
```

**Response:**
```json
[
  {
    "id": 1,
    "code": "USR001",
    "name": "Đỗ Thế Anh",
    "userRoles": [
      {
        "id": 1,
        "roleCode": "ADMIN",
        "roleName": "ADMIN"
      }
    ]
  }
]
```

---

## 🧩 5. Giải thích kỹ thuật

### 🔸 DynamicFilter
Dùng Reflection để lọc các field trong DTO `SearchParams` có giá trị khác `null` hoặc `0`.  
=> Tự động bỏ qua điều kiện không có giá trị khi truy vấn.

### 🔸 SpecificationBuilder
Dùng cho `JpaSpecificationExecutor` để sinh `Predicate` động (tương tự filter OR/LIKE).

### 🔸 JwtAuthenticationFilter
- Đọc token từ header
- Giải mã user từ token
- Gắn `Authentication` vào `SecurityContextHolder`

### 🔸 PermissionFilter
- Kiểm tra quyền người dùng trên từng request
- Nếu không có quyền → trả `403 Forbidden`

---

## 🚀 6. Giao diện JSP/HTML

Ứng dụng gồm các trang:

- `login.html`: Đăng nhập, lấy JWT token, lưu vào `localStorage`
- `user.html`: Ví dụ  hiển thị danh sách người dùng, lọc theo tên real-time

---
