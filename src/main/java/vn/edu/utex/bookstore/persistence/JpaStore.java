package vn.edu.utex.bookstore.persistence;
import jakarta.persistence.*;
import java.util.*;
import java.util.function.Function;
import vn.edu.utex.bookstore.auth.*;
import vn.edu.utex.bookstore.category.*;
import vn.edu.utex.bookstore.common.Problem;
import vn.edu.utex.bookstore.config.Settings;
public class JpaStore implements Store {
    private final EntityManagerFactory factory;
    public JpaStore(Settings config) {
        Map<String,Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url",config.require("db.url"));
        properties.put("jakarta.persistence.jdbc.user",config.require("db.user"));
        properties.put("jakarta.persistence.jdbc.password",config.require("db.password"));
        properties.put("hibernate.hbm2ddl.auto","validate");
        factory = Persistence.createEntityManagerFactory("bookstore",properties);
    }
    public JpaStore(EntityManagerFactory factory) { this.factory = factory; }
    @Override public <T> T tx(Function<Data,T> action) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin(); T result = action.apply(new JpaData(em)); transaction.commit(); return result;
        } catch (RuntimeException e) {
            if (transaction.isActive()) transaction.rollback();
            if (e instanceof Problem) throw e;
            for (Throwable cause=e;cause!=null;cause=cause.getCause()) {
                if (cause instanceof java.sql.SQLException sql) {
                    if ("23505".equals(sql.getSQLState())) throw new Problem(409,"Tên hoặc email đã tồn tại.");
                    if ("23503".equals(sql.getSQLState())) throw new Problem(409,"Dữ liệu đang được sử dụng, không thể xóa.");
                }
            }
            throw new IllegalStateException("Lỗi truy cập database.",e);
        } finally { em.close(); }
    }
    @Override public void close() { factory.close(); }
    private static final class JpaData implements Data {
        private final EntityManager em;
        JpaData(EntityManager em) { this.em=em; }
        public User user(long id) { return em.find(User.class,id); }
        public User findLogin(String login) {
            return em.createQuery("select u from User u where u.username = :login or u.email = :login",User.class)
                .setParameter("login",login).getResultStream().findFirst().orElse(null);
        }
        public User saveUser(User u) { if(u.id==null){em.persist(u); return u;} return em.merge(u); }
        public Category category(long id) { return em.find(Category.class,id); }
        public List<Category> categories(String query) {
            return em.createQuery("select c from Category c where locate(:q,lower(c.name)) > 0 order by c.id desc",Category.class)
                .setParameter("q",query.toLowerCase(Locale.ROOT)).getResultList();
        }
        public Category saveCategory(Category c) { if(c.id==null){em.persist(c); return c;} return em.merge(c); }
        public void deleteCategory(long id) { Category c=category(id); if(c!=null) em.remove(c); }
        public RememberToken token(String hash) { return em.find(RememberToken.class,hash); }
        public void saveToken(RememberToken token) { em.persist(token); }
        public void deleteToken(String hash) { em.createQuery("delete from RememberToken t where t.tokenHash=:hash").setParameter("hash",hash).executeUpdate(); }
        public void deleteUserTokens(long id) { em.createQuery("delete from RememberToken t where t.userId=:id").setParameter("id",id).executeUpdate(); }
    }
}
