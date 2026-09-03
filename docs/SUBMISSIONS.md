# Ba bản nộp độc lập

| Tag local | Thư mục xuất | Phạm vi |
|---|---|---|
| submission-01-r1 | submissions/Bai_01_Servlet_JDBC | Cookie/Session, Category JDBC |
| submission-02-r1 | submissions/Bai_02_Servlet_JPA | Chức năng Bài 01 dùng JPA |
| submission-03 | submissions/Bai_03_OTP_Product | OTP, Product và phân trang |

Chạy scripts/export-submissions.ps1 sau khi các tag tồn tại. Script xuất đúng cây Git của tag, không sửa tag, không ZIP và không push. Từ chối ghi đè folder có sẵn. Thư mục export không kèm .git hoặc cấu hình riêng; clone repository để xem lịch sử.

Trong mỗi thư mục: README, config mẫu, source, SQL thủ công, tài liệu, Maven Wrapper. Build từng bản bằng mvnw.cmd clean verify; database theo cấu hình riêng. Manifest ở thư mục submissions ghi SHA/tag, không ghép source khác phiên bản.

Bài 01 nộp source theo UTEx. Bài 02/03: sau khi bạn yêu cầu tạo/push remote, dùng URL GitHub /tree/submission-02-r1 và /tree/submission-03, kèm repository URL. Hiện chưa có URL GitHub thật, không dùng URL giả làm bằng chứng.

Tag submission-01 và submission-02 gốc giữ nguyên để đối chiếu lịch sử. Dùng bản r1 khi kiểm tra/nộp vì đã sửa xử lý URL quản trị có tham số đường dẫn và font tiếng Việt. Không đổi hoặc ghi đè tag cũ.

## Trước khi tự ZIP/nộp

- [ ] Chạy ứng dụng bằng PostgreSQL đã chuẩn bị; restart vẫn giữ dữ liệu.
- [ ] Chạy CRUD, phân quyền và các trang bắt buộc trên DB thật.
- [ ] Bài 03: nhận và dùng cả OTP kích hoạt/reset qua Gmail thật.
- [ ] Đảm bảo người chấm được quyền xem GitHub nếu repository private.
- [ ] Không nộp .git, target, .tools, config bí mật, uploads cá nhân.
- [ ] Giữ nguyên tag đã công bố. Nếu sửa sau đó, tạo tag revision mới và ghi rõ.

Deadline đã đọc: Bài 01 26/08/2026 08:37; Bài 02 26/08/2026 10:45; Bài 03 06/09/2026 23:45 (UTC+7). Hai bài đầu đã quá hạn khi bắt đầu triển khai; hỏi giảng viên việc mở lại. Không backdate commit.
