# Bài tập Lập trình Web

Sinh viên: Lê Trọng Bảo

MSSV: 22110106

Đề tài: Website bán sách Góc Sách.

## Nội dung

- Bài 01: đăng nhập Session/Cookie và quản lý danh mục bằng JDBC.
- Bài 02: chuyển phần lưu dữ liệu sang JPA.
- Bài 03: OTP qua email, quản lý sách, trang chủ và phân trang.
- Bài 04: SiteMesh 3 với Bootstrap, validation các form và cập nhật hồ sơ có tải ảnh.

## Cách chạy

Yêu cầu Java 21, PostgreSQL và Tomcat 10.1.

1. Sao chép `config/local.example.properties` thành `config/local.properties` và điền cấu hình máy.
2. Database mới dùng `sql/schema.sql`. Database Bài 03 dùng `sql/upgrade-03-to-04.sql`.
3. Chạy `mvnw.cmd clean verify`, sau đó deploy `target/bookstore.war` lên Tomcat.

Không đưa mật khẩu hoặc file `config/local.properties` lên GitHub. Các bản nộp được đánh dấu bằng tag `submission-01` đến `submission-04`.
