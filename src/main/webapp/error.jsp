<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <body>
        <h2>Hey, something went wrong on our side! Sorry bout that(((</h2>

        <a href="${pageContext.request.getContextPath()}/food">Check your food</a>
        <a href="${pageContext.request.getContextPath()}/category">Check your food categories</a>
    </body>
</html>
