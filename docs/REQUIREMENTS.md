# Đối chiếu yêu cầu

| Yêu cầu Bài 01 | Màn hình | Xử lý | Kiểm tra |
|---|---|---|---|
| Login Session/Cookie | /auth/login | AuthService, SecurityFilter | đăng nhập/hết hạn/đăng xuất/giả token |
| MVC ba tầng | toàn ứng dụng | web → service → repository | không có SQL trong JSP |
| CRUD danh mục JDBC | /admin/categories | CategoryService, JdbcStore | thêm/sửa/xóa/tìm kiếm/validation |
| Ảnh danh mục | form danh mục | LocalImageStorage | định dạng/kích thước/path traversal |

Nguồn: https://utexlms.hcmute.edu.vn/mod/assign/view.php?id=1452286

Các giới hạn bảo mật, kiểu ảnh và cấu trúc thư mục là quyết định triển khai; không phải nguyên văn yêu cầu giảng viên.

Bài 02 giữ toàn bộ các màn hình trên, thay JdbcStore bằng JpaStore + META-INF/orm.xml. Kiểm thử thêm JpaTransactionTest: commit/rollback/close và đọc mapping offline. Nguồn: https://utexlms.hcmute.edu.vn/mod/assign/view.php?id=1452292
