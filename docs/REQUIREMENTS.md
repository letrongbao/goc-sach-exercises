# Ma trận yêu cầu và kiểm tra

| Bài | Yêu cầu | URL / phần xử lý | Kiểm thử |
|---|---|---|---|
| 01 | Login Session/Cookie | /auth/login, AuthService, SecurityFilter | CoreTest, WebTest |
| 01 | MVC ba tầng, CRUD Category JDBC | /admin/categories; tag 01 JdbcStore | CoreTest, JdbcTransactionTest (tag 01) |
| 01–03 | Ảnh, validation, phân quyền | ImageStorage, filter, forms | ImageTest, WebTest |
| 02 | Chuyển toàn bộ dữ liệu sang JPA | JpaStore, persistence.xml, orm.xml | JpaTransactionTest, hồi quy WebTest |
| 03 | Đăng ký + OTP activation | /auth/register, /auth/activate, /auth/resend | OtpTest, WebTest |
| 03 | Quên/đặt lại mật khẩu OTP | /auth/forgot, /auth/reset | OtpTest, WebTest |
| 03 | Email gửi thực tế qua SMTP | SmtpEmailSender | SmtpTest loopback; Gmail chờ cấu hình |
| 03 | Category 1:N Product, CRUD | /admin/products, /admin/product/* | ProductTest, WebTest, mapping metadata |
| 03 | 10 sản phẩm mới nhất | /, ProductService.latest | ProductTest, HTTP card count |
| 03 | 6 sản phẩm/trang | /product?page=N | 13 records → 6/6/1 |
| 03 | Chi tiết từ cả hai danh sách | /product/detail?id=N | WebTest, Chrome |

Nguồn đã đọc trước khi triển khai:
- https://utexlms.hcmute.edu.vn/mod/assign/view.php?id=1452286
- https://utexlms.hcmute.edu.vn/mod/assign/view.php?id=1452292
- https://utexlms.hcmute.edu.vn/mod/assign/view.php?id=1565194
- https://utexlms.hcmute.edu.vn/mod/page/view.php?id=1452205

Test fake/mocked repository không xác minh SQL/PostgreSQL thật. Chi tiết ở VERIFICATION.md. Trạng thái, giới hạn upload, bảo mật và thiết kế bộ nhớ là quyết định triển khai, không tự gán thành yêu cầu nguyên văn của giảng viên.
