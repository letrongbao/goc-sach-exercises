# OTP và email — Bài 03

Đăng ký → user pending → SMTP → OTP activation → đăng nhập. Quên mật khẩu → OTP reset → gửi mã và mật khẩu mới cùng form → transaction xác minh rồi đổi mật khẩu; không tự đăng nhập sau reset.

OTP 6 chữ số, SecureRandom, hạn 10 phút, 5 lần sai, resend tối thiểu 60 giây. Mã dùng một lần, gắn user và purpose. HMAC-SHA256 dùng otp.secret ngoài source; không lưu/log mã rõ. Challenge pending chỉ được xác minh khi SMTP gửi thành công. SMTP lỗi: giữ user pending, vô hiệu challenge và cho gửi lại sau cooldown.

Lock user bằng PESSIMISTIC_WRITE để serialize gửi/xác minh/reset; tăng attempts trong transaction rồi mới báo lỗi bên ngoài, không rollback bộ đếm sai. Reset tăng auth_version (mọi session được kiểm tra lại mỗi request), xóa remember_tokens và xóa cookie trình duyệt hiện tại.

Rate limit 10 lần gửi / IP / 15 phút và 3 lần / email / 15 phút; xác minh 30 lần / IP / 15 phút và 10 lần / email / 15 phút. Bộ giới hạn bổ sung này ở RAM (phù hợp một node local); challenge/cooldown/5 lần sai nằm trong PostgreSQL. Khi triển khai nhiều node phải chuyển rate limit sang shared store. Thông báo quên mật khẩu thống nhất; SMTP đồng bộ có thể khác thời gian phản hồi giữa email tồn tại/không tồn tại, cần queue + chống timing enumeration trước production.

## Mailpit

Tự chạy Mailpit riêng, SMTP 127.0.0.1:1025, UI 127.0.0.1:8025. config/local.example.properties trỏ vào SMTP này; không gửi ra Gmail. Không tự khởi động Docker/PostgreSQL hoặc tự tạo schema.

## Gmail

Sao chép gmail.example.properties thành file cấu hình riêng, dùng Gmail hỗ trợ App Password + 2-Step Verification. STARTTLS 587 và kiểm tra chứng chỉ/hostname bật. Không dùng mật khẩu Google chính. Không gửi bí mật qua chat; điền trên máy của bạn.

Để tạo otp.secret: dùng password manager tạo chuỗi ngẫu nhiên ít nhất 32 byte; giữ ổn định khi restart. Đổi secret làm OTP đang chờ mất hiệu lực.

Trước nộp: xác minh nhận và dùng OTP activation, rồi reset bằng email thật (cả inbox/spam). Chỉ test SMTP giả không chứng minh Gmail đã hoạt động.

Nguồn: https://support.google.com/accounts/answer/185833 ; https://cheatsheetseries.owasp.org/cheatsheets/Forgot_Password_Cheat_Sheet.html
