# Cấu hình trên máy

## Cài PostgreSQL riêng bằng Docker

Chạy `scripts/setup-postgres.ps1` trong PowerShell, tự đặt mật khẩu hai lần. Script tạo instance demo riêng tại `127.0.0.1:5434`, database `bookstore_03`, user `bookstore`; cập nhật cấu hình local và giữ nguyên mục Gmail. Không chạy SQL tạo bảng bài tập. Nếu container hoặc volume đã có, script dừng để tránh đụng dữ liệu cũ.

Mật khẩu được lưu trên máy trong Docker và file cấu hình, không in ra màn hình. Tài khoản này có quyền quản trị instance demo riêng, không dùng cho production. PostgreSQL 17.11-alpine được tải ngày 04/09/2026; digest `sha256:18cfe3ef5e6815560c98237d6216d1e5119702fb0f3894c8785dd58b8bbe5d73`.

## Điền cấu hình thủ công

Mở `C:/BaoLT/spring_boot/config/local.properties`. Khi tải source từ GitHub, sao chép `config/gmail.example.properties` thành `config/local.properties`.

Thay các giá trị REPLACE:

| Dòng | Nội dung |
|---|---|
| db.url | Máy chủ, cổng và tên database PostgreSQL riêng đã chuẩn bị |
| db.user | Tài khoản PostgreSQL có quyền dùng database đó |
| db.password | Mật khẩu PostgreSQL |
| smtp.user, smtp.from | Cùng địa chỉ Gmail gửi OTP |
| smtp.password | Gmail App Password, không dùng mật khẩu Google chính |
| otp.secret | Chuỗi ngẫu nhiên riêng ít nhất 32 byte từ trình quản lý mật khẩu |

Giữ các dòng SMTP khác theo mẫu. `cookie.secure=false` dành cho HTTP trên máy; triển khai HTTPS dùng true.

Điền tên database không tự tạo database/bảng. Chủ project tự chuẩn bị theo SETUP.md, không dùng database dự án khác.

Thêm vào tùy chọn Java của Tomcat:

```text
-Dbookstore.config=C:/BaoLT/spring_boot/config/local.properties
```

File local.properties được Git bỏ qua. Không gửi file, mật khẩu hay App Password vào chat/bài nộp. Điền xong chỉ cần báo đã cấu hình.
