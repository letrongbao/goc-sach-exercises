package vn.edu.utex.bookstore;

import static org.junit.jupiter.api.Assertions.*;

import java.net.*;
import java.net.http.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.*;
import vn.edu.utex.bookstore.auth.*;
import vn.edu.utex.bookstore.config.*;

class WebTest {
  @Test
  void normalizedAdminPathsCannotBypassAuthorization() throws Exception {
    for (String path :
        List.of("/admin;ignored/categories", "/%61dmin/categories", "/admin;ignored/products")) {
      var response = get(path);
      assertEquals(302, response.statusCode(), path + response.body());
    }
  }

  private Tomcat tomcat;
  private HttpClient client;
  private String base;
  private OtpTest.Mail mail;

  @BeforeEach
  void start() throws Exception {
    var props = new Properties();
    props.setProperty("uploads.dir", Path.of("target/test-uploads").toAbsolutePath().toString());
    props.setProperty("cookie.secure", "false");
    props.setProperty("otp.secret", "test-only-secret-not-for-production-0123456789");
    MemoryStore store = DemoFixtures.store();
    mail = new OtpTest.Mail();
    tomcat = new Tomcat();
    tomcat.setBaseDir("target/tomcat-test");
    tomcat.setPort(0);
    tomcat.getConnector().setProperty("address", "127.0.0.1");
    var context =
        tomcat.addWebapp("/bookstore", Path.of("src/main/webapp").toAbsolutePath().toString());
    context.setParentClassLoader(getClass().getClassLoader());
    context
        .getServletContext()
        .setAttribute("app", new App(new Settings(props), store, Clock.systemUTC(), mail));
    tomcat.start();
    base = "http://127.0.0.1:" + tomcat.getConnector().getLocalPort() + "/bookstore";
    client =
        HttpClient.newBuilder()
            .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
            .build();
  }

  @AfterEach
  void stop() throws Exception {
    if (tomcat != null) {
      tomcat.stop();
      tomcat.destroy();
    }
  }

  HttpResponse<String> get(String route) throws Exception {
    return client.send(
        HttpRequest.newBuilder(URI.create(base + route)).GET().build(),
        HttpResponse.BodyHandlers.ofString());
  }

  HttpResponse<String> post(String route, String body) throws Exception {
    return client.send(
        HttpRequest.newBuilder(URI.create(base + route))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build(),
        HttpResponse.BodyHandlers.ofString());
  }

  @Test
  void jspLoginAndAdminFlow() throws Exception {
    var home = get("/");
    assertEquals(200, home.statusCode(), home.body());
    assertTrue(home.body().contains("mộc"));
    var login = get("/auth/login");
    assertEquals(200, login.statusCode(), login.body());
    var matcher =
        java.util.regex.Pattern.compile("name=\"_csrf\" value=\"([^\"]+)\"").matcher(login.body());
    assertTrue(matcher.find());
    String csrf = matcher.group(1);
    assertEquals(403, post("/auth/login", "login=admin&password=demo-password-123").statusCode());
    var logged =
        post(
            "/auth/login",
            "_csrf=" + csrf + "&login=admin&password=demo-password-123&mode=session");
    assertEquals(302, logged.statusCode(), logged.body());
    var list = get("/admin/categories");
    assertEquals(200, list.statusCode(), list.body());
    assertTrue(list.body().contains("Danh mục sách"));
    assertEquals(200, get("/admin/category/add").statusCode());
    assertEquals(404, get("/admin/category/edit?id=999").statusCode());
  }

  @Test
  void unauthenticatedCannotEnterAdminAndAssetsWork() throws Exception {
    assertEquals(302, get("/admin/categories").statusCode());
    assertEquals(200, get("/assets/app.css").statusCode());
    assertEquals(404, get("/media/invalid").statusCode());
  }

  String csrf(String route) throws Exception {
    var response = get(route);
    assertEquals(200, response.statusCode(), response.body());
    var matcher =
        java.util.regex.Pattern.compile("name=\"_csrf\" value=\"([^\"]+)\"")
            .matcher(response.body());
    assertTrue(matcher.find(), response.body());
    return matcher.group(1);
  }

  long countCards(String body) {
    return java.util.regex.Pattern.compile("data-product-id=").matcher(body).results().count();
  }

  @Test
  void catalogPagesAndDetailsRender() throws Exception {
    assertEquals(10, countCards(get("/").body()));
    for (int n = 1; n <= 3; n++) {
      var page = get("/product?page=" + n);
      assertEquals(200, page.statusCode(), page.body());
      assertEquals(n == 3 ? 1 : 6, countCards(page.body()));
    }
    var detail = get("/product/detail?id=13");
    assertEquals(200, detail.statusCode(), detail.body());
    assertTrue(detail.body().contains("Một đời đáng đọc"));
    assertEquals(404, get("/product/detail?id=999").statusCode());
    assertEquals(400, get("/product?page=0").statusCode());
    assertEquals(404, get("/product?page=4").statusCode());
    for (String route :
        List.of("/auth/register", "/auth/activate", "/auth/forgot", "/auth/reset")) {
      var response = get(route);
      assertEquals(200, response.statusCode(), response.body());
    }
  }

  @Test
  void otpRegistrationActivationAndResetThroughHttp() throws Exception {
    String token = csrf("/auth/register");
    var registered =
        post(
            "/auth/register",
            "_csrf="
                + token
                + "&username=webreader&email=webreader%40example.test&password=web-password-123&confirm=web-password-123");
    assertEquals(302, registered.statusCode(), registered.body());
    String code = mail.code();
    assertEquals(
        302,
        post(
                "/auth/activate",
                "_csrf=" + csrf("/auth/activate") + "&email=webreader%40example.test&code=" + code)
            .statusCode());
    var login =
        post(
            "/auth/login",
            "_csrf="
                + csrf("/auth/login")
                + "&login=webreader&password=web-password-123&mode=session");
    assertEquals(302, login.statusCode(), login.body());
    assertEquals(403, get("/admin/products").statusCode());
    assertEquals(
        302,
        post("/auth/forgot", "_csrf=" + csrf("/auth/forgot") + "&email=webreader%40example.test")
            .statusCode());
    String resetCode = mail.code();
    var reset =
        post(
            "/auth/reset",
            "_csrf="
                + csrf("/auth/reset")
                + "&email=webreader%40example.test&code="
                + resetCode
                + "&password=changed-password-123&confirm=changed-password-123");
    assertEquals(302, reset.statusCode(), reset.body());
    assertEquals(
        302,
        post(
                "/auth/login",
                "_csrf="
                    + csrf("/auth/login")
                    + "&login=webreader&password=changed-password-123&mode=cookie")
            .statusCode());
  }

  @Test
  void productAndCategoryAdminForms() throws Exception {
    assertEquals(
        302,
        post(
                "/auth/login",
                "_csrf="
                    + csrf("/auth/login")
                    + "&login=admin&password=demo-password-123&mode=session")
            .statusCode());
    for (String route :
        List.of(
            "/admin/products",
            "/admin/product/add",
            "/admin/product/edit?id=1",
            "/admin/category/edit?id=1")) {
      var page = get(route);
      assertEquals(200, page.statusCode(), page.body());
    }
    String token = csrf("/admin/product/add");
    var added =
        post(
            "/admin/product/save",
            "_csrf="
                + token
                + "&categoryId=1&title=%3Cscript%3Ealert(1)%3C%2Fscript%3E&author=Test&price=99000&stock=3&description=Demo&image=");
    assertEquals(302, added.statusCode(), added.body());
    var detail = get("/product/detail?id=14");
    assertTrue(detail.body().contains("&lt;script&gt;"));
    assertFalse(detail.body().contains("<script>alert(1)</script>"));
    assertEquals(409, post("/admin/category/delete", "_csrf=" + token + "&id=1").statusCode());
    assertEquals(
        400,
        post(
                "/admin/product/save",
                "_csrf=" + token + "&categoryId=1&title=Book&author=Test&price=-1&stock=3")
            .statusCode());
    assertEquals(302, post("/admin/product/delete", "_csrf=" + token + "&id=14").statusCode());
    assertEquals(404, get("/product/detail?id=14").statusCode());
  }
}
