package vn.edu.utex.bookstore;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import javax.sql.DataSource;
import java.sql.Connection;
import vn.edu.utex.bookstore.persistence.JdbcStore;
class JdbcTransactionTest {
    @Test void commitsAndCloses() throws Exception {
        var source=mock(DataSource.class); var connection=mock(Connection.class); when(source.getConnection()).thenReturn(connection);
        assertEquals("ok",new JdbcStore(source).tx(d -> "ok")); verify(connection).commit(); verify(connection).close(); verify(connection,never()).rollback();
    }
    @Test void rollsBackAndCloses() throws Exception {
        var source=mock(DataSource.class); var connection=mock(Connection.class); when(source.getConnection()).thenReturn(connection);
        assertThrows(IllegalArgumentException.class,()->new JdbcStore(source).tx(d -> { throw new IllegalArgumentException(); }));
        verify(connection).rollback(); verify(connection).close(); verify(connection,never()).commit();
    }
}
