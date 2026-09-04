# Điền cấu hình

Sao chép `config/gmail.example.properties` thành `config/local.properties`, rồi thay các giá trị REPLACE.

| Mục | Điền gì? |
|---|---|
| db.url | Địa chỉ PostgreSQL, cổng và tên database |
| db.user | Tên tài khoản PostgreSQL |
| db.password | Mật khẩu PostgreSQL |
| smtp.user, smtp.from | Cùng địa chỉ Gmail gửi OTP |
| smtp.password | Mật khẩu ứng dụng Gmail, không phải mật khẩu Google chính |
| otp.secret | Khóa ngẫu nhiên riêng, ít nhất 32 byte |

Giữ các dòng gửi mail còn lại theo mẫu. Xem [hướng dẫn email](OTP.md) nếu cần.

Trên máy đã cài theo dự án: PostgreSQL ở `127.0.0.1:5434`, database `bookstore_03`, tài khoản `bookstore`. Máy khác cần điền theo cấu hình của máy đó.

Nếu chưa có PostgreSQL, có thể dùng `scripts/setup-postgres.ps1` với Docker. Script yêu cầu tự nhập mật khẩu, tạo máy chủ demo riêng và không tạo bảng bài tập. Không chạy lại nếu đã cài.

File `local.properties` chỉ giữ trên máy, không đưa vào GitHub hoặc bài nộp. Chạy HTTP trên máy dùng `cookie.secure=false`; dùng HTTPS thì đặt `true`.
