# Ghi chú kiểm thử — 04/09/2026

| Lượt kiểm tra | Kết quả |
|---|---|
| Kết nối PostgreSQL, 11:07 | Đạt |
| Sản phẩm và OTP qua Gmail, 11:21 | Đạt; Gmail chấp nhận gửi hai thư |
| Các form với PostgreSQL, 11:26 | Đạt; lượt này không gửi email thật |
| Chrome máy tính/điện thoại, 11:27–11:28 | Các trang đã xem không tràn ngang, không ảnh lỗi |
| Sau đổi tên Góc Sách | 61 kiểm thử thường đạt; 3 kiểm thử tích hợp không chạy mặc định |

OTP trong kiểm thử được lấy trước khi gửi, không đọc từ hộp thư. Vì vậy chưa xác nhận thư đến Inbox/Spam. Các giới hạn khác xem [kết quả kiểm tra](VERIFICATION.md).

## Khi chạy lại

- Chỉ dùng database thử riêng đã có bảng. Các test không tạo bảng.
- `PostgresReadinessTest` chỉ kiểm tra kết nối và đọc dữ liệu.
- `LiveAcceptanceTest` cần danh sách sách trống và gửi hai email thật mỗi lần.
- `PostgresWebTest` cũng cần danh sách sách trống nhưng không gửi email thật.
- Sau kiểm thử, sách/danh mục do test tạo được dọn; tài khoản thử bị vô hiệu hóa. Ảnh thử nằm trong `target`, không thuộc bài nộp.

Ảnh và ghi nhận ở các phiên bản cũ là minh chứng lịch sử, không phải ảnh mới của Góc Sách.
