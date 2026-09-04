# Góc Sách — Bài tập Lập trình Web

Sinh viên: Lê Trọng Bảo

Đề tài: Website bán sách.

## Nội dung Bài 03

Phát triển tiếp Bài 02:

- Đăng ký và kích hoạt tài khoản bằng OTP qua email.
- Đăng nhập, quên mật khẩu và đặt lại mật khẩu bằng OTP.
- Thêm, sửa, xóa sản phẩm; mỗi sản phẩm thuộc một danh mục.
- Trang chủ hiển thị 10 sản phẩm mới nhất.
- Trang `/product` hiển thị 6 sản phẩm mỗi trang.
- Xem chi tiết sản phẩm từ trang chủ và danh sách.

## Công nghệ

Java 21, Servlet, JSP/JSTL, JPA, PostgreSQL và Bootstrap.

## Cách chạy

1. Chuẩn bị database theo [hướng dẫn](docs/SETUP.md). SQL chỉ chạy thủ công.
2. Điền `config/local.properties` theo [hướng dẫn cấu hình](docs/CAU_HINH.md).
3. Chạy `mvnw.cmd clean verify`.
4. Thêm tùy chọn Java cho Tomcat 10.1: `-Dbookstore.config=C:/BaoLT/spring_boot/config/local.properties`.
5. Chép `target/bookstore.war` vào Tomcat, mở `http://localhost:8080/bookstore/`.

## Tình trạng

Đã chạy 61 kiểm thử thường và kiểm thử tích hợp PostgreSQL/Gmail. Các luồng sản phẩm, kích hoạt và đặt lại mật khẩu đã qua kiểm thử; đang chờ xác nhận hai email xuất hiện trong hộp thư. Xem [kết quả](docs/LIVE_CHECK.md).

[Hướng dẫn nộp bài](docs/SUBMISSIONS.md). Repo này dành cho bài tập, không phải đồ án cuối kỳ.
