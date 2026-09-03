# Mộc Sách — Bài 01: Servlet/JDBC

Ứng dụng bán sách phát triển tuần tự cho môn Lập trình Web. Bản này có đăng nhập Session/Cookie và CRUD danh mục. Chưa có Spring Boot, OTP hay sản phẩm.

## Quy tắc trước khi chạy

- Đọc `docs/SETUP.md`, `docs/REQUIREMENTS.md` và `docs/ROADMAP.md`.
- **Không tự chạy tạo bảng/migration.** SQL chỉ để chủ project đọc và thực thi thủ công trên database riêng.
- Không lưu mật khẩu SMTP/database trong Git; không push/nộp bài tự động.
- Java 21, PostgreSQL 17, Tomcat 10.1; Maven 3.9.11 qua Maven Wrapper.

## Build

Windows: `./mvnw.cmd clean verify`. Linux/macOS: `./mvnw clean verify`.

WAR: `target/bookstore.war`. Triển khai vào Tomcat 10.1 và mở `/bookstore/`.
Test mặc định không kết nối PostgreSQL, không thực thi SQL và không gửi Gmail.

## Bản sửa an toàn

Bản giao hiện tại dùng tag submission-01-r1: bổ sung xử lý URL quản trị đã chuẩn hóa và sửa font tiếng Việt. Tag submission-01 gốc chỉ giữ để đối chiếu, không dùng để nộp.

## Bản nộp

`submission-01` → JDBC; `submission-02` → JPA; `submission-03` → JPA + OTP + sản phẩm.
Chỉ chốt tag khi kiểm tra phù hợp; các mục chưa xác minh thực tế ghi trong `docs/VERIFICATION.md`.
