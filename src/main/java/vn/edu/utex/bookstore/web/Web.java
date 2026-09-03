package vn.edu.utex.bookstore.web;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import vn.edu.utex.bookstore.common.Problem;
public final class Web {
    private Web() {}
    public static long id(String raw) {
        try { long id = Long.parseLong(raw); if (id <= 0) throw Problem.missing(); return id; }
        catch (NumberFormatException e) { throw Problem.missing(); }
    }
    public static Long optionalId(String raw) { return raw == null || raw.isBlank() ? null : id(raw); }
    public static void view(HttpServletRequest req,HttpServletResponse res,String view) throws ServletException,IOException {
        req.setAttribute("view",view); req.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(req,res);
    }
    public static void redirect(HttpServletRequest req,HttpServletResponse res,String route) throws IOException { res.sendRedirect(req.getContextPath()+route); }
}
