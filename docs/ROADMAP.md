# Lộ trình và quyết định

Ba bài phát triển cùng repository: JDBC → JPA EntityManager → OTP/sản phẩm. Chưa dùng Spring Boot/Spring Data/REST, không làm cart/order/payment trước Bài 03.

Sau Bài 03: chuyển Spring MVC/Security, giữ JSP/JSTL và WAR; tái sử dụng services, interfaces repository, email/image và dữ liệu. Tiếp theo hồ sơ/địa chỉ → giỏ hàng → đơn hàng/tồn kho → COD/VNPAY sandbox → cửa hàng/Seller/Admin → đánh giá/khuyến mãi/báo cáo. Mỗi giai đoạn cần test riêng.

Đề tài chuỗi cửa hàng sách cũ và nhóm cần giảng viên xác nhận. Chưa có ngày nộp cuối kỳ được xác minh. Không coi việc chọn đề tài tại đây là đăng ký trên UTEx.

Nguồn: https://utexlms.hcmute.edu.vn/mod/page/view.php?id=1452010

## Ràng buộc

- Không chạy tạo bảng/migration/DDL, kể cả trong test; SQL để chủ project đọc và thực thi thủ công.
- Không tạo remote/push/nộp UTEx tự động. Commit/tag local phục vụ ba phiên bản đã được yêu cầu.
- Java 21, Jakarta Servlet 6, PostgreSQL; không phụ thuộc kiểu SQL Server `nvarchar`.
- Chủ project giữ bí mật cấu hình; không tài khoản/mật khẩu thật trong Git.
