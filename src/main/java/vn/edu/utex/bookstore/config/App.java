package vn.edu.utex.bookstore.config;
import java.nio.file.Path;
import java.time.Clock;
import vn.edu.utex.bookstore.auth.*;
import vn.edu.utex.bookstore.category.*;
import vn.edu.utex.bookstore.image.*;
import vn.edu.utex.bookstore.persistence.*;
public final class App implements AutoCloseable {
    public final Settings settings;
    public final Store store;
    public final AuthService auth;
    public final CategoryService categories;
    public final LocalImageStorage images;
    public final RateLimiter limits;
    public App(Settings settings, Store store, Clock clock) {
        this.settings = settings; this.store = store; this.auth = new AuthService(store,new Passwords(),clock);
        this.categories = new CategoryService(store); this.images = new LocalImageStorage(Path.of(settings.require("uploads.dir")));
        this.limits = new RateLimiter(clock);
    }
    public static App create() { Settings s = Settings.load(); return new App(s,new JdbcStore(s),Clock.systemUTC()); }
    public void close() { store.close(); }
}
