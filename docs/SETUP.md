# Cài đặt và chạy

1. Cài Java 21, PostgreSQL 17 và Tomcat 10.1. Dùng PostgreSQL 17.11 theo kế hoạch; không thay database các dự án khác.
2. Chủ project tự tạo database `bookstore_01`, đọc rồi tự chạy `sql/schema.sql`. Ứng dụng không chạy file SQL.
3. Build project rồi chạy `./scripts/hash-password.ps1` trong PowerShell thật (nhập ẩn qua console; không đặt mật khẩu trên command line). Thay placeholder trong `sql/demo.sql` rồi tự thực thi trên DB demo.
4. Sao chép `config/local.example.properties` thành `config/local.properties`, điền thông tin riêng. Không commit file này.
5. Cấu hình Tomcat với JVM option `-Dbookstore.config=C:/BaoLT/spring_boot/config/local.properties`.
6. Chạy `./mvnw.cmd clean verify`; chép `target/bookstore.war` vào thư mục `webapps` của Tomcat. Mở `http://localhost:8080/bookstore/`.

Maven Wrapper tải Maven/dependency ở lần đầu. Java có sẵn, không cần cài Maven toàn máy. Production dùng HTTPS và `cookie.secure=true`. Session timeout 30 phút, Cookie ghi nhớ 7 ngày.

Ảnh upload nằm ngoài WAR theo `uploads.dir`, không bị mất khi redeploy. Không chạy nhiều bản demo dùng chung thư mục ảnh/database. Chưa có tác vụ tự xóa ảnh cũ; giữ file để tránh xóa nhầm ảnh còn được tham chiếu.

## Kịch bản demo Bài 01

Đăng nhập bằng Session → CRUD danh mục → đăng xuất → đăng nhập bằng Cookie → xóa riêng JSESSIONID và tải lại (vẫn đăng nhập bằng token) → đăng xuất (token hết hiệu lực). Dùng tài khoản USER mở `/admin/categories` phải nhận 403. Cookie không chứa mật khẩu/username làm bằng chứng đăng nhập.

## Kiểm thử tích hợp

Chỉ chạy trên schema đã được người dùng tạo thủ công. Test mặc định sử dụng repository giả; không xem đây là bằng chứng PostgreSQL hoạt động. Không dùng H2/Testcontainers tự tạo bảng.
