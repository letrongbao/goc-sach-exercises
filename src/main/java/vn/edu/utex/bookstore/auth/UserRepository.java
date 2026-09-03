package vn.edu.utex.bookstore.auth;
public interface UserRepository {
    User user(long id);
    User findLogin(String login);
    User saveUser(User user);
}
