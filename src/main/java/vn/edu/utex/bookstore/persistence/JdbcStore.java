package vn.edu.utex.bookstore.persistence;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import org.postgresql.ds.PGSimpleDataSource;
import vn.edu.utex.bookstore.auth.*;
import vn.edu.utex.bookstore.category.*;
import vn.edu.utex.bookstore.common.Problem;
import vn.edu.utex.bookstore.config.Settings;
public class JdbcStore implements Store {
    private final javax.sql.DataSource source;
    public JdbcStore(Settings config) {
        var pg = new PGSimpleDataSource();
        pg.setURL(config.require("db.url")); pg.setUser(config.require("db.user")); pg.setPassword(config.require("db.password"));
        pg.setConnectTimeout(5); pg.setSocketTimeout(15); source = pg;
    }
    public JdbcStore(javax.sql.DataSource source) { this.source = source; }
    @Override public <T> T tx(Function<Data, T> action) {
        try (Connection c = source.getConnection()) {
            c.setAutoCommit(false);
            try { T result = action.apply(new JdbcData(c)); c.commit(); return result; }
            catch (RuntimeException e) { c.rollback(); throw e; }
        } catch (SQLException e) { throw databaseError(e); }
    }
    private static RuntimeException databaseError(SQLException e) {
        if ("23505".equals(e.getSQLState())) return new Problem(409, "Tên hoặc email đã tồn tại.");
        if ("23503".equals(e.getSQLState())) return new Problem(409, "Dữ liệu đang được sử dụng, không thể xóa.");
        return new IllegalStateException("Lỗi truy cập database.", e);
    }
    private static final class JdbcData implements Data {
        private final Connection c;
        JdbcData(Connection c) { this.c = c; }
        interface Mapper<T> { T read(ResultSet row) throws SQLException; }
        <T> List<T> query(String sql, Mapper<T> mapper, Object... args) {
            try (PreparedStatement p = statement(sql, args); ResultSet rows = p.executeQuery()) {
                List<T> result = new ArrayList<>(); while (rows.next()) result.add(mapper.read(rows)); return result;
            } catch (SQLException e) { throw databaseError(e); }
        }
        PreparedStatement statement(String sql, Object... args) throws SQLException {
            PreparedStatement p = c.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) p.setObject(i + 1, args[i] instanceof Instant t ? Timestamp.from(t) : args[i]);
            return p;
        }
        void update(String sql, Object... args) {
            try (PreparedStatement p = statement(sql, args)) { p.executeUpdate(); }
            catch (SQLException e) { throw databaseError(e); }
        }
        static <T> T first(List<T> items) { return items.isEmpty() ? null : items.getFirst(); }
        User mapUser(ResultSet r) throws SQLException {
            User u = new User(); u.id = r.getLong("id"); u.username = r.getString("username"); u.email = r.getString("email");
            u.passwordHash = r.getString("password_hash"); u.role = r.getString("role"); u.active = r.getBoolean("active");
            u.authVersion = r.getInt("auth_version"); u.createdAt = r.getTimestamp("created_at").toInstant(); return u;
        }
        public User user(long id) { return first(query("SELECT * FROM users WHERE id=?", this::mapUser, id)); }
        public User findLogin(String login) { return first(query("SELECT * FROM users WHERE username=? OR email=?", this::mapUser, login, login)); }
        public User saveUser(User u) {
            if (u.id == null) u.id = first(query("INSERT INTO users(username,email,password_hash,role,active,auth_version,created_at) VALUES(?,?,?,?,?,?,?) RETURNING id",
                    r -> r.getLong(1), u.username,u.email,u.passwordHash,u.role,u.active,u.authVersion,u.createdAt));
            else update("UPDATE users SET username=?,email=?,password_hash=?,role=?,active=?,auth_version=? WHERE id=?",u.username,u.email,u.passwordHash,u.role,u.active,u.authVersion,u.id);
            return u;
        }
        Category mapCategory(ResultSet r) throws SQLException {
            Category x = new Category(); x.id = r.getLong("id"); x.name = r.getString("name"); x.image = r.getString("image"); x.active = r.getBoolean("active"); return x;
        }
        public Category category(long id) { return first(query("SELECT * FROM categories WHERE id=?",this::mapCategory,id)); }
        public List<Category> categories(String q) {
            return query("SELECT * FROM categories WHERE position(lower(?) in lower(name)) > 0 ORDER BY id DESC", this::mapCategory,q);
        }
        public Category saveCategory(Category x) {
            if (x.id == null) x.id = first(query("INSERT INTO categories(name,image,active) VALUES(?,?,?) RETURNING id",r -> r.getLong(1),x.name,x.image,x.active));
            else update("UPDATE categories SET name=?,image=?,active=? WHERE id=?",x.name,x.image,x.active,x.id);
            return x;
        }
        public void deleteCategory(long id) { update("DELETE FROM categories WHERE id=?",id); }
        public RememberToken token(String hash) {
            return first(query("SELECT * FROM remember_tokens WHERE token_hash=?", r -> { RememberToken t = new RememberToken(); t.tokenHash = r.getString("token_hash");
                t.userId = r.getLong("user_id"); t.expiresAt = r.getTimestamp("expires_at").toInstant(); return t; },hash));
        }
        public void saveToken(RememberToken t) { update("INSERT INTO remember_tokens(token_hash,user_id,expires_at) VALUES(?,?,?)",t.tokenHash,t.userId,t.expiresAt); }
        public void deleteToken(String hash) { update("DELETE FROM remember_tokens WHERE token_hash=?",hash); }
        public void deleteUserTokens(long id) { update("DELETE FROM remember_tokens WHERE user_id=?",id); }
    }
}
