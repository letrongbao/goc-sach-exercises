# Kết quả xác minh — 03/09/2026

| Bản giao | Build WAR | Test tự động | Phạm vi |
|---|---|---|---|
| submission-01-r1 | PASS | 24/24 | JDBC services, mock transaction, JSP/HTTP/auth/CSRF |
| submission-02-r1 | PASS | 25/25 | Hồi quy, JPA metadata offline, mock transaction |
| submission-03 | PASS | 56/56 | OTP, SMTP loopback, sản phẩm, upload, bảo mật, JSP/HTTP |

Bài 03: CoreTest 14; ImageTest 7; JpaTransactionTest 3; OtpTest 10; ProductTest 14; SmtpTest 2; WebTest 6. Không có failures/errors/skipped trong lần verify cuối.

## Đã xác minh

- Java 21, Maven 3.9.11 build WAR; dependency được cố định.
- Render thực tế các JSP bằng Tomcat nhúng 10.1.44 (Servlet 6). Phân trang 13 sản phẩm → 6/6/1, trang chủ 10, trang chi tiết và admin forms.
- Đăng ký/activation/reset qua HTTP bằng fake repository + email capture; kiểm tra OTP hết hạn, dùng lại, sai 5 lần, resend, lỗi SMTP và thu hồi session/cookie.
- Gửi email qua protocol SMTP thực bằng GreenMail loopback 127.0.0.1, chỉ địa chỉ example.test, không gửi Gmail.
- Transaction commit/rollback/close được kiểm tra bằng mock. Mapping JPA 5 entity được Hibernate đọc offline; không mở kết nối DB.
- Chặn URL admin dạng thường, path parameter và percent-encoding. Tag gốc 01/02 được thay thế cho mục đích nộp bằng r1, không di chuyển tag cũ.
- Chrome kiểm tra desktop/mobile 390x844, không tràn ngang; sửa lỗi font tách dấu tiếng Việt. Ảnh ở evidence/ dùng dữ liệu giả, có banner phân biệt.

## Chưa thể xác minh — cần bạn chuẩn bị

1. PostgreSQL thật: chưa có database/schema được người dùng chuẩn bị và cấu hình cấp cho ứng dụng. Do đó chưa xác minh CRUD/SQL/FK/locking/rollback/restart persistence trên PostgreSQL.
2. Gmail thật: chưa có App Password và mailbox cấu hình. Chưa xác minh inbox/spam, khả năng gửi, kích hoạt/reset qua Gmail.
3. GitHub/UTEx: chưa tạo remote, push hoặc nộp bài. Chưa có link repository thật để nộp.

Không có file SQL, DDL, migration hay chế độ tự tạo bảng nào được chạy. Các tag là snapshot source đã build/test trong phạm vi nêu trên, chưa phải nghiệm thu end-to-end bằng PostgreSQL/Gmail.

## Lưu ý vận hành

- Chỉ triển khai học tập một node; rate limit bổ sung còn ở RAM. SMTP gửi đồng bộ có thể lộ khác biệt thời gian phản hồi; cần queue/biện pháp chống timing enumeration trước production.
- Chưa audit CVE đầy đủ/toàn bộ tải hoặc môi trường triển khai thật.
- Không tự xóa ảnh cũ. Backup dữ liệu/media trước khi vận hành chính thức.
