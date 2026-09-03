# Trạng thái xác minh

Bài 01, kiểm tra ngày 03/09/2026: Maven verify BUILD SUCCESS; 23 tests, 0 failures, 0 errors. WAR đã build. HTTP tests render JSP trên Tomcat nhúng và kiểm tra đăng nhập/admin/CSRF; repository trong HTTP tests là fake, transaction JDBC dùng mock connection.

Tag submission-01 là snapshot source để kiểm tra/nộp sau khi hoàn tất các bước bên dưới, không phải chứng nhận đã chạy end-to-end với database thật.

Chưa xác minh: PostgreSQL thật (chưa có schema do người dùng chuẩn bị), restart giữ dữ liệu trên DB thật, Gmail thật, trạng thái nộp UTEx. Không có DDL/migration nào được chạy.
