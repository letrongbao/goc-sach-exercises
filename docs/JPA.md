# Bài 02: từ JDBC sang JPA

Giữ nguyên services, interfaces, forms và URLs của Bài 01. Thay JdbcStore bằng JpaStore cho cả users, categories và remember_tokens; xóa implementation JDBC khỏi bản JPA (vẫn giữ ở tag submission-01).

Mapping JPA dùng META-INF/orm.xml, access FIELD, tránh đưa dependency persistence vào POJO domain. Đây là ánh xạ entity chuẩn JPA, không phải native SQL và không phải REST API. persistence.xml khai báo provider Hibernate; JpaStore dùng EntityManager, JPQL, persist/merge/remove.

Một EntityManagerFactory/Hikari pool cho toàn ứng dụng; EntityManager/transaction riêng cho từng use case. Đóng resources khi kết thúc; rollback trên lỗi. Không giữ EntityManager trong servlet hoặc HttpSession. Ba entity hiện tại chỉ có scalar, đã detached trước khi render JSP.

Không đổi schema Bài 01. Để chạy bản riêng, tự chuẩn bị database bookstore_02 bằng cùng schema và copy dữ liệu theo hướng dẫn PostgreSQL; không chạy lại CREATE trên database đã có bảng.

Startup chỉ validate, không tạo/sửa schema. Không truyền schema-generation database.action=none đồng thời với hbm2ddl.auto=validate vì cấu hình JPA có thể lấn át validate của Hibernate. Test metadata dùng none, tắt JDBC metadata access, không mở database hoặc tạo bảng.

Nguồn: https://utexlms.hcmute.edu.vn/mod/assign/view.php?id=1452292 và https://utexlms.hcmute.edu.vn/mod/page/view.php?id=1452205
