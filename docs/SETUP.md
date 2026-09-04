# Cách chạy

Cần Java 21, PostgreSQL 17 và Tomcat 10.1.

1. Tạo database riêng. Với database mới, đọc và chạy `sql/schema.sql`. Nếu dùng tiếp database Bài 02, chỉ chạy `sql/upgrade-02-to-03.sql`. Không chạy cả hai.
2. Điền cấu hình theo [hướng dẫn](CAU_HINH.md).
3. Tại thư mục project, chạy `mvnw.cmd clean verify`.
4. Thêm tùy chọn Java cho Tomcat bên dưới, sửa đường dẫn theo máy.
5. Chép `target/bookstore.war` vào thư mục `webapps` của Tomcat và khởi động Tomcat.

```text
-Dbookstore.config=C:/BaoLT/spring_boot/config/local.properties
```

Mở [website trên máy](http://localhost:8080/bookstore/).

## Dữ liệu demo

Sau khi build, chạy `scripts/hash-password.ps1` để tạo mật khẩu đã mã hóa. Điền kết quả vào chỗ trống trong `sql/demo.sql`, kiểm tra rồi chạy trên database demo. Không chạy lại schema nếu đã có bảng.

Ảnh tải lên được lưu ở đường dẫn `uploads.dir` trong cấu hình. Giữ thư mục này khi cập nhật ứng dụng.

Ứng dụng và kiểm thử không tự tạo bảng. Không dùng database của dự án khác.
