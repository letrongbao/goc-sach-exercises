package vn.edu.utex.bookstore;
import java.util.*;
import java.util.function.Function;
import vn.edu.utex.bookstore.persistence.Store;
import vn.edu.utex.bookstore.auth.*;
import vn.edu.utex.bookstore.category.*;
public class MemoryStore implements Store, Store.Data {
    public final Map<Long,User> users = new LinkedHashMap<>();
    public final Map<Long,Category> categories = new LinkedHashMap<>();
    public final Map<String,RememberToken> tokens = new HashMap<>();
    private long nextUser = 1, nextCategory = 1;
    public synchronized <T> T tx(Function<Store.Data,T> action) { return action.apply(this); }
    public User user(long id) { return users.get(id); }
    public User findLogin(String login) { return users.values().stream().filter(u -> u.username.equals(login) || u.email.equals(login)).findFirst().orElse(null); }
    public User saveUser(User u) { if(u.id == null) u.id = nextUser++; users.put(u.id,u); return u; }
    public Category category(long id) { return categories.get(id); }
    public List<Category> categories(String q) { return categories.values().stream().filter(c -> c.name.toLowerCase().contains(q.toLowerCase())).sorted(Comparator.comparing((Category c)->c.id).reversed()).toList(); }
    public Category saveCategory(Category c) { if(c.id == null) c.id=nextCategory++; categories.put(c.id,c); return c; }
    public void deleteCategory(long id) { categories.remove(id); }
    public RememberToken token(String hash) { return tokens.get(hash); }
    public void saveToken(RememberToken token) { tokens.put(token.tokenHash,token); }
    public void deleteToken(String hash) { tokens.remove(hash); }
    public void deleteUserTokens(long id) { tokens.values().removeIf(t -> t.userId == id); }
}
