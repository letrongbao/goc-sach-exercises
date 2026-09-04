# Bản sửa sau rà soát — 03/09/2026

Phạm vi: bản sửa phát triển từ `submission-03` / commit `cd23ba47c73d373b75ec688c20f5dc60e933c5fb`, được đưa vào mốc source `submission-03-r1` khi tách repository ngày 04/09/2026. Chưa xuất lại folder hoặc nộp UTEx. Các tag/folder snapshot cũ được giữ nguyên; bản sửa chưa được backport sang Bài 01/02.

## Đã sửa

1. **ID sai không gây 500:** parse ID form có validation 400; khi render lại không parse lần nữa. Giữ nguyên ID đã nhập (đã escape) để không vô tình biến lệnh sửa thành tạo mới. Dropdown category chỉ so sánh giá trị số đã kiểm tra; giữ các trường còn lại khi lỗi.
2. **Phản hồi OTP:** forgot/resend không phản ánh lỗi SMTP bằng status/nội dung khác giữa email có/không tồn tại. Challenge lỗi vẫn consumed, chưa delivered; đăng ký mới giữ phản hồi lỗi hữu ích. Log không chứa email, OTP hoặc exception nhà cung cấp.
3. **Trang lỗi chung:** xử lý 400/403/404/405/413/429/500/503 và exception qua trang tiếng Việt, không in stack trace/message nội bộ; có nút về trang chủ/đăng nhập, no-store và CSP. Trang lỗi không truy cập database.
4. **Bìa mobile:** dùng bố cục giới hạn nội dung trang trí; màn hình <=380px dùng một cột. Tên đầy đủ vẫn ở dưới bìa, không mất thông tin khi phần trang trí dùng dấu ba chấm. Chuỗi dài được xuống dòng an toàn.
5. **Upload:** ảnh hợp lệ được ưu tiên hơn URL cũ ở cả hai form. Multipart được phân tích trước khi lấy CSRF để ảnh vượt 5 MB báo đúng 413, không báo nhầm 403. CSRF vẫn bắt buộc với request hợp lệ.
6. Form đăng ký/OTP dùng heading h1; giữ font tiếng Việt và cấu trúc giao diện hiện có.

## Kiểm thử

- Ba regression test ban đầu đều fail trên source cũ và pass sau sửa: malformed IDs; public OTP khi SMTP fail; trang lỗi an toàn.
- Thêm HTTP multipart: PNG thật, URL cũ không hợp lệ, file giả và >5 MB; test này bắt thêm lỗi 403/413 và đã được sửa.
- Thêm unit test reset khi SMTP fail: mật khẩu không đổi, mã không dùng được, có thể retry sau cooldown.
- Chạy lại script HTTP của lượt audit: category ID sai 500→400; product category ID sai 500→400; forgot email tồn tại khi SMTP lỗi 503→302, giống email không tồn tại.
- Kiểm tra Chrome desktop, 320px và 390px bằng preview dữ liệu RAM. Bìa không tràn khung; không tràn ngang trang. Kết quả test cuối cập nhật ở [VERIFICATION](VERIFICATION.md).

## Giới hạn còn lại

Chưa kiểm thử PostgreSQL/schema thật hoặc Gmail thật; không chạy DDL/migration, không tự tạo bảng. SMTP vẫn đồng bộ nên chưa giải quyết timing enumeration; việc chống khác biệt status/nội dung không đồng nghĩa đã chống mọi cách dò tài khoản.

Đã chạy lại Maven offline ngày 04/09/2026: 61 tests, không failures/errors/skipped; WAR build thành công. Cần backport phần form ID/trang lỗi/upload vào hai bài trước và xuất lại từng bản từ đúng tag mới. Không di chuyển tag đã chốt hoặc ghi đè folder cũ.
