<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html lang="vi"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>Mộc Sách · Một trang sách, một chân trời</title>
<link rel="stylesheet" href="<c:url value='/assets/bootstrap.min.css'/>"><link rel="stylesheet" href="<c:url value='/assets/app.css'/>">
<link rel="stylesheet" href="<c:url value='/assets/typography.css'/>">
</head><body>
<div class="topline">MỘC SÁCH JOURNAL <span>Chậm lại một chút, đọc thêm một trang.</span></div>
<header class="site-header"><a class="brand" href="<c:url value='/'/>">mộc<span>sách.</span></a>
<nav aria-label="Điều hướng chính"><a href="<c:url value='/'/>">Trang chủ</a><c:if test="${identity.admin}"><a href="<c:url value='/admin/categories'/>">Quản lý danh mục</a></c:if></nav>
<div class="account"><c:choose><c:when test="${not empty identity}"><span>Chào, <c:out value="${identity.username}"/></span><form method="post" action="<c:url value='/auth/logout'/>"><input type="hidden" name="_csrf" value="<c:out value='${csrf}'/>"><button class="link-button">Đăng xuất</button></form></c:when>
<c:otherwise><a class="btn btn-outline-dark btn-sm" href="<c:url value='/auth/login'/>">Đăng nhập ↗</a></c:otherwise></c:choose></div></header>
<main id="main"><c:if test="${not empty error}"><div class="container"><div class="alert alert-danger mt-4" role="alert"><c:out value="${error}"/></div></div></c:if>
<jsp:include page="${view}.jsp"/></main>
<footer><a class="brand" href="<c:url value='/'/>">mộc<span>sách.</span></a><p>Những cuốn sách hay luôn tìm được người đọc mới.</p><small>Dự án học tập · Lập trình Web · Java / PostgreSQL</small></footer>
</body></html>
