# Bài 03 — nghiệm thu và nộp

Cập nhật 04/09 lúc 11:21: đã kiểm thử tự động với PostgreSQL/Gmail, xem [kết quả](LIVE_CHECK.md). Bảng dưới dành cho nghiệm thu thủ công qua giao diện; không đánh dấu thay bằng kết quả service test.

Chỉ áp dụng Bài 03. Không coi test fake/SMTP loopback là nghiệm thu database/email thật. Không chạy tự động bất kỳ SQL tạo bảng/migration nào.

## 1. Chuẩn bị riêng trên máy

- Chủ project tự chuẩn bị schema theo SETUP.md, chọn database demo riêng, không dùng database dự án khác.
- Điền config/local.properties từ mẫu (file được Git bỏ qua). Không gửi mật khẩu/App Password vào chat hoặc commit.
- Cấu hình Gmail theo OTP.md; email nhận phải là hộp thư do người nghiệm thu kiểm soát.
- Dùng SQL demo thủ công hoặc UI quản trị để có ít nhất 13 sản phẩm, tài khoản ADMIN và USER.

## 2. Kiểm tra schema và truy vấn, không ghi dữ liệu

Sau khi có schema và cấu hình thật, chạy trong PowerShell tại thư mục project:

```powershell
.\mvnw.cmd -B -ntp test '-Dtest=PostgresReadinessTest' '-Dbookstore.test.postgres=true' '-Dbookstore.config=C:/BaoLT/spring_boot/config/local.properties'
```

Test chỉ validate schema và SELECT bằng JPA, không tạo bảng, seed, sửa/xóa dữ liệu hay gửi email. Mặc định bị skip khi không bật bookstore.test.postgres; skip không phải pass PostgreSQL. Test này không thay thế kiểm tra CRUD/rollback/restart bên dưới. Không chạy đồng thời với chỉnh sửa catalog để tránh số đếm thay đổi giữa các request.

## 3. Nghiệm thu trên Tomcat + PostgreSQL thật

Ghi ngày, commit SHA, kết quả thực tế và ảnh không chứa bí mật. Chưa đánh dấu đạt trước khi chạy.

| Kịch bản | Kết quả mong đợi | Kết quả thực tế |
|---|---|---|
| Đăng ký với hộp thư thật | Nhận OTP; chưa kích hoạt thì không đăng nhập được | Chưa chạy |
| Kích hoạt OTP | Đăng nhập được; mã dùng lại bị từ chối | Chưa chạy |
| Quên mật khẩu | Nhận OTP reset qua hộp thư thật | Chưa chạy |
| Reset và đăng nhập | Mật khẩu mới được; mật khẩu cũ, session/cookie cũ bị vô hiệu | Chưa chạy |
| OTP sai/hết hạn/gửi lại | Có thông báo; mã cũ bị vô hiệu, cooldown hoạt động | Chưa chạy |
| ADMIN CRUD sản phẩm | Tạo/sửa/xóa/đọc đúng; giá/tồn kho âm bị từ chối | Chưa chạy |
| Danh mục đang có sản phẩm | Không xóa được; thông báo rõ | Chưa chạy |
| USER hoặc chưa đăng nhập gọi URL admin | Bị chặn, không thay đổi dữ liệu | Chưa chạy |
| Trang chủ với 13 sản phẩm | Đúng 10 sản phẩm mới nhất | Chưa chạy |
| /product với 13 sản phẩm | Ba trang 6–6–1, thứ tự mới nhất ổn định | Chưa chạy |
| Bấm sách từ trang chủ và /product | Mở đúng chi tiết sách | Chưa chạy |
| Catalog rỗng, ID/trang sai, upload sai | Thông báo rõ, không stack trace/500 ngoài dự kiến | Chưa chạy |
| Restart Tomcat | Sản phẩm/tài khoản còn trong PostgreSQL | Chưa chạy |
| Desktop/mobile | Form, menu, ảnh, nút và thông báo không tràn ngang | Chưa chạy |

## 4. Chốt source và nộp

Chỉ chốt revision sau khi ghi rõ kết quả và giới hạn. Không di chuyển tag cũ. Nếu sửa từ submission-03-r1, dùng revision mới và cập nhật link tương ứng; kiểm tra SHA đã push.

Repository bài tập: https://github.com/letrongbao/goc-sach-exercises . Không dùng repo goc-sach của đồ án.

UTEx Bài 03: https://utexlms.hcmute.edu.vn/mod/assign/view.php?id=1565194 . Hạn đã kiểm tra ngày 04/09/2026: 23:45 ngày 06/09/2026 (UTC+7).

Repo đang Private: cấp quyền đúng tài khoản người chấm hoặc xin chủ repo đồng ý chuyển Public. Vào Thêm bài nộp, điền Văn bản trực tuyến với họ tên, MSSV, link repo và link /tree/TAG_DA_NGHIEM_THU. Lưu rồi kiểm tra trạng thái; hoàn tất bước gửi/xác nhận nếu hệ thống còn yêu cầu. Mark as done không thay cho nộp bài.

ZIP source chỉ là đính kèm bổ sung, không thay link GitHub. Không ZIP folder Bài 03 cũ. Dùng script export-bai03.ps1 với đúng tag; folder xuất không có .git, target, cấu hình thật, uploads hoặc cache. Người dùng tự ZIP/nộp.
