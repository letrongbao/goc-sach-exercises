# Kiểm tra Bài 03 — 04/09/2026

## Đã đạt

- PostgreSQL 17.11: kết nối bằng cấu hình ứng dụng, kiểm tra schema và truy vấn JPA.
- Tạo 13 sản phẩm: trang chủ 10, danh sách 6–6–1; xem/sửa/xóa, từ chối giá âm và chặn xóa danh mục còn sách.
- Giao dịch lỗi rollback; mở kết nối JPA mới vẫn đọc đủ dữ liệu đã lưu.
- Tomcat/JSP đọc catalog từ PostgreSQL thật: trang chủ, ba trang danh sách và chi tiết trả về thành công.
- Gmail SMTP chấp nhận hai email kích hoạt/đặt lại mật khẩu, khoảng 11:21.
- OTP kích hoạt và reset hoạt động; mã dùng lại bị từ chối, mật khẩu/session/cookie cũ bị vô hiệu sau reset.

PostgresReadinessTest: 1/1 PASS lúc 11:07. LiveAcceptanceTest: 1/1 PASS lúc 11:21.

## Kiểm tra bổ sung 11:26–11:28

- PostgresWebTest: 1/1 PASS, các form HTTP chạy trên PostgreSQL thật. Email được bắt trong bộ nhớ, không gửi thêm Gmail.
- Đăng ký, chặn đăng nhập trước kích hoạt, kích hoạt, đăng nhập Session/Cookie, chặn USER vào admin, quên/đặt lại mật khẩu và đăng nhập bằng mật khẩu mới.
- Form sản phẩm: thêm/sửa/xóa, từ chối giá âm/ID sai, CSRF, escape XSS, chặn xóa danh mục có sách, upload PNG thật và đọc ảnh qua /media.
- Đếm card HTML: trang chủ 10, danh sách 6–6–1. Link chi tiết lấy từ cả trang chủ và danh sách đều mở thành công. Trang/ID không hợp lệ trả 400/404.
- Chrome trên PostgreSQL: xem trang chủ desktop, catalog và chi tiết mobile 390×844; kiểm tra trang chủ/catalog và năm form tài khoản ở 320×740. Không tràn ngang, không ảnh lỗi; console không ghi lỗi trong lượt xem.
- Đã đóng server kiểm thử cổng 18081, khôi phục viewport Chrome và dọn catalog thử. Tài khoản thử bị vô hiệu hóa; ảnh PNG thử còn trong target/live-ui-uploads (không đóng gói WAR).

## Giới hạn

OTP trong test được giữ trong bộ nhớ trước khi gửi, không đọc từ hộp thư. Vì vậy vẫn cần người dùng xác nhận nhận đủ hai email trong Inbox/Spam. Form admin đã kiểm tra qua HTTP nhưng chưa đánh giá trực quan mọi màn hình admin. Chưa kiểm tra cạnh tranh nhiều request hoặc restart container/Tomcat với cùng phiên người dùng. Chưa đối chiếu đầy đủ mọi PDF môn học; không khẳng định đạt toàn bộ rubric chỉ từ test.

Test dọn 13 sách và danh mục do nó tạo; tài khoản thử được giữ nhưng vô hiệu hóa, thu hồi token. Không xóa dữ liệu người dùng có sẵn. Database hiện không có catalog để demo; cần chuẩn bị dữ liệu demo trước khi trình bày.

Schema chỉ được chạy một lần theo yêu cầu trực tiếp của người dùng. Các test không tạo bảng/migration. Mật khẩu/OTP không được ghi vào tài liệu hoặc Git.

Ba test tích hợp mặc định không chạy; bật riêng khi cần. LiveAcceptanceTest chỉ cho phép database local bài tập đúng URL, yêu cầu catalog trống và gửi hai email thật mỗi lần. PostgresWebTest cũng yêu cầu catalog trống nhưng không gửi email thật. Không đưa chúng vào CI mặc định.

Bản đổi tên Góc Sách dùng mốc `submission-03-r2`. Sau đổi tên, build đạt: 61 kiểm thử thường thành công, 3 kiểm thử tích hợp bỏ qua theo cấu hình mặc định. Không gửi thêm email thật trong lượt này. Tag và ảnh minh chứng cũ giữ nguyên nội dung lịch sử. Chưa nộp UTEx.
