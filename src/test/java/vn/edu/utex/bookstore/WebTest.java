package vn.edu.utex.bookstore;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.catalina.startup.Tomcat;
import java.net.*;
import java.net.http.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import vn.edu.utex.bookstore.auth.*;
import vn.edu.utex.bookstore.config.*;
class WebTest {
    @Test void normalizedAdminPathsCannotBypassAuthorization() throws Exception {
        for(String path:List.of("/admin;ignored/categories","/%61dmin/categories")) {
            var response=get(path);assertEquals(302,response.statusCode(),path+response.body());
        }
    }
    private Tomcat tomcat;
    private HttpClient client;
    private String base;
    @BeforeEach void start() throws Exception {
        var props=new Properties(); props.setProperty("uploads.dir",Path.of("target/test-uploads").toAbsolutePath().toString()); props.setProperty("cookie.secure","false");
        MemoryStore store=new MemoryStore(); User user=new User(); user.username="admin"; user.email="admin@example.test"; user.active=true; user.role="ADMIN";
        user.createdAt=Instant.now(); user.passwordHash=new Passwords().hash("demo-password-123"); store.saveUser(user);
        tomcat=new Tomcat(); tomcat.setBaseDir("target/tomcat-test"); tomcat.setPort(0); tomcat.getConnector().setProperty("address","127.0.0.1");
        var context=tomcat.addWebapp("/bookstore",Path.of("src/main/webapp").toAbsolutePath().toString());
        context.setParentClassLoader(getClass().getClassLoader());
        context.getServletContext().setAttribute("app",new App(new Settings(props),store,Clock.systemUTC()));
        tomcat.start(); base="http://127.0.0.1:"+tomcat.getConnector().getLocalPort()+"/bookstore";
        client=HttpClient.newBuilder().cookieHandler(new CookieManager(null,CookiePolicy.ACCEPT_ALL)).build();
    }
    @AfterEach void stop() throws Exception { if(tomcat!=null){tomcat.stop();tomcat.destroy();} }
    HttpResponse<String> get(String route) throws Exception { return client.send(HttpRequest.newBuilder(URI.create(base+route)).GET().build(),HttpResponse.BodyHandlers.ofString()); }
    HttpResponse<String> post(String route,String body) throws Exception { return client.send(HttpRequest.newBuilder(URI.create(base+route)).header("Content-Type","application/x-www-form-urlencoded").POST(HttpRequest.BodyPublishers.ofString(body)).build(),HttpResponse.BodyHandlers.ofString()); }
    @Test void jspLoginAndAdminFlow() throws Exception {
        var home=get("/"); assertEquals(200,home.statusCode(),home.body()); assertTrue(home.body().contains("mộc"));
        var login=get("/auth/login"); assertEquals(200,login.statusCode(),login.body());
        var matcher=java.util.regex.Pattern.compile("name=\"_csrf\" value=\"([^\"]+)\"").matcher(login.body()); assertTrue(matcher.find()); String csrf=matcher.group(1);
        assertEquals(403,post("/auth/login","login=admin&password=demo-password-123").statusCode());
        var logged=post("/auth/login","_csrf="+csrf+"&login=admin&password=demo-password-123&mode=session"); assertEquals(302,logged.statusCode(),logged.body());
        var list=get("/admin/categories"); assertEquals(200,list.statusCode(),list.body()); assertTrue(list.body().contains("Danh mục sách"));
        assertEquals(200,get("/admin/category/add").statusCode());
        assertEquals(404,get("/admin/category/edit?id=999").statusCode());
    }
    @Test void unauthenticatedCannotEnterAdminAndAssetsWork() throws Exception {
        assertEquals(302,get("/admin/categories").statusCode()); assertEquals(200,get("/assets/app.css").statusCode());
        assertEquals(404,get("/media/invalid").statusCode());
    }
}
