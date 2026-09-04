# Bài tập Lập trình Web — Mộc Sách

Repository này dành cho **Bài 01–03**, không phải repository đồ án cuối kỳ. [Đồ án Mộc Sách](https://github.com/letrongbao/moc-sach) phát triển riêng từ nền tảng này; xem [cách phân chia](docs/REPOSITORIES.md).

**Bản sửa Bài 03:** `main` và mốc `submission-03-r1` có validation ID, xử lý SMTP lỗi, trang lỗi an toàn và giao diện mobile. Các folder `submissions` cũ trên máy vẫn giữ nguyên, chưa chứa bản sửa này. Đọc [chi tiết](docs/FIXES.md). Chưa nghiệm thu PostgreSQL/email thật, chưa xác nhận đáp ứng toàn bộ tài liệu PDF.

Website bán sách bằng **Java 21 / Servlet 6 / JSP-JSTL / PostgreSQL / JPA EntityManager**, đóng gói WAR. Đây là project phát triển nối tiếp Bài 01 (JDBC), Bài 02 (JPA) và Bài 03 (OTP + sản phẩm), chưa chuyển Spring Boot.

## Bắt đầu

1. Đọc [SETUP](docs/SETUP.md). Chủ project **tự chuẩn bị schema**; không có DDL/migration tự chạy.
2. Sao chép cấu hình mẫu trong config/ thành file riêng, điền PostgreSQL, thư mục ảnh và otp.secret.
3. Dùng Mailpit để thử email local hoặc cấu hình [Gmail SMTP](docs/OTP.md).
4. Build bằng `./mvnw.cmd clean verify` (Windows) hoặc `./mvnw clean verify`.
5. Deploy `target/bookstore.war` lên Tomcat 10.1 và mở /bookstore/.

Không có tài khoản admin mặc định trên database thật. Tạo hash và nhập dữ liệu demo thủ công theo SETUP. Không đưa mật khẩu/secret thật vào Git.

## Xem giao diện không cần PostgreSQL

Chạy `./scripts/preview.ps1`, mở http://127.0.0.1:18080/bookstore/ .
Bản preview có banner cảnh báo, 13 sách giả trong RAM; không gửi email và không lưu dữ liệu. Tài khoản thử **admin / demo-password-123**, **reader / demo-password-123** chỉ tồn tại trong test fixtures, không được đóng gói vào WAR. Dừng bằng Ctrl+C.

## Chức năng

- Hai cách đăng nhập: Session hoặc Cookie token ngẫu nhiên; phân quyền ADMIN/USER.
- CRUD/tìm kiếm Category, ảnh qua URL hoặc upload hợp lệ.
- Đăng ký/kích hoạt OTP; đặt lại mật khẩu OTP và thu hồi phiên cũ.
- CRUD sách; Category 1:N Product; chặn xóa danh mục đang có sách.
- Trang chủ 10 sách mới; /product phân trang 6; chi tiết từ cả trang chủ/danh sách.

## Hồ sơ và bản nộp

- [Đối chiếu yêu cầu](docs/REQUIREMENTS.md)
- [Kết quả và giới hạn xác minh](docs/VERIFICATION.md)
- [Chuyển JDBC → JPA](docs/JPA.md)
- [Thiết kế và cách học code](docs/ARCHITECTURE.md)
- [Lộ trình cuối kỳ](docs/ROADMAP.md)
- [Hướng dẫn nộp ba bài](docs/SUBMISSIONS.md)

| Bài | Mốc source | Trạng thái |
|---|---|---|
| 01 — JDBC | [submission-01-r1](https://github.com/letrongbao/utex-web-exercises/tree/submission-01-r1) | Snapshot cũ; còn cần backport sửa form/upload và nghiệm thu DB |
| 02 — JPA | [submission-02-r1](https://github.com/letrongbao/utex-web-exercises/tree/submission-02-r1) | Snapshot cũ; còn đối chiếu/bổ sung count, phân trang Category |
| 03 — OTP/Product | [submission-03-r1](https://github.com/letrongbao/utex-web-exercises/tree/submission-03-r1) | Có bản sửa, 61 tests pass; chưa nghiệm thu DB/email thật |

Tag gốc giữ để đối chiếu, không di chuyển. Không coi tag là chứng nhận hoàn thành bài. Chưa ZIP/nộp UTEx. Repository được tạo Private; người chấm cần quyền truy cập trước khi nộp link.
