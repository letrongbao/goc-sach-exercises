package vn.edu.utex.bookstore;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;
import vn.edu.utex.bookstore.image.*;
import vn.edu.utex.bookstore.common.Problem;
class ImageTest {
    @TempDir Path dir;
    @Test void rejectsDangerousUrls() { var s=new LocalImageStorage(dir); for(String url:new String[]{"javascript:alert(1)","//example.org/a.png","file:///etc/passwd","https://user:pass@example.org/image"}) assertThrows(Problem.class,()->s.validateReference(url)); }
    @Test void permitsHttpImages() { assertEquals("https://example.org/a.png",new LocalImageStorage(dir).validateReference("https://example.org/a.png")); }
    @Test void rejectsHtmlDisguisedAsImage() { byte[] b="<html>not an image</html>".getBytes(); assertThrows(Problem.class,()->new LocalImageStorage(dir).store(new ByteArrayInputStream(b),b.length)); }
    @Test void rejectsOversizedUpload() { assertThrows(Problem.class,()->new LocalImageStorage(dir).store(InputStream.nullInputStream(),LocalImageStorage.MAX_BYTES+1L)); }
    @Test void noTraversal() { assertThrows(Problem.class,()->new LocalImageStorage(dir).find("../secret")); }
}
