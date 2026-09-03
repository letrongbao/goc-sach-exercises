# Thiết kế và cách nghiên cứu source

## Ba tầng

`HTTP → SecurityFilter → Servlet → Service → Store.tx(Repository) → PostgreSQL`

- web/: nhận form, session/cookie, điều hướng và render JSP; không chứa SQL.
- auth/, category/, product/: nghiệp vụ, validation và interfaces repository.
- persistence/: EntityManagerFactory dùng chung; EntityManager và transaction riêng từng use case.
- mail/, image/: thay được adapter SMTP/lưu ảnh mà không đổi nghiệp vụ.
- config/: cấu hình ngoài Git và lắp ráp dependencies.
- WEB-INF/views/: JSP chỉ render, escape dữ liệu qua c:out; không có scriptlet SQL/Java.

User và Category là POJO ánh xạ bằng orm.xml. Product.category là MANY_TO_ONE LAZY; repository join-fetch category và service tạo ProductView ngay trong transaction. JSP không chạm lazy entity hoặc passwordHash.

## Đọc theo bài

1. Bài 01: Web/SecurityFilter → AuthService → UserRepository/TokenRepository → JdbcStore trong tag submission-01; CategoryService → CRUD JDBC.
2. Bài 02: so sánh JpaStore với JdbcStore, xem META-INF/persistence.xml và orm.xml; chạy các test commit/rollback/close.
3. Bài 03: OtpService.register/issue/verify → OtpServlet; ProductService.latest/page → ProductServlet → book-cards.jsp.

## Giao ước

- GET chỉ đọc; mutation dùng POST + CSRF; login thay session ID.
- Session được đối chiếu auth_version mỗi request. Cookie 7 ngày chỉ chứa random token, DB giữ SHA-256.
- OTP HMAC với secret ngoài source, đồng thời kiểm tra mục đích, hạn, lần sai, delivered/consumed. Lock user để đồng bộ request cạnh tranh.
- Category đang có sách không bị xóa. Product CRUD là xóa thật trong phạm vi bài tập; khi có đơn hàng phải đổi sang lưu lịch sử/ẩn sản phẩm.
- Public catalog Bài 03 hiển thị mọi sản phẩm, không lọc trạng thái Category. active ở Category hiện là thuộc tính quản trị.
- Tệp tối đa 5 MB / 20 megapixel, kiểm tra bytes và decode ảnh; không tin tên file/MIME client, không cho SVG. File nằm ngoài WAR; không tự xóa ảnh cũ vì có thể được tham chiếu.

## Giới hạn hiện tại

Ứng dụng học tập một node. Trước production cần shared rate limiter/queue mail, xử lý timing enumeration, quan sát vận hành, kiểm thử tải, quy trình backup và audit dependency. Chưa có cart, orders, thanh toán, Seller/shop; không giả lập các nút chức năng chưa làm.

Giao diện responsive và test fixtures phục vụ minh họa; không thay thế database thật. Nâng Spring Boot sẽ giữ nghiệp vụ, repository contracts và JSP nhưng thay cấu hình, controller và security adapter.
