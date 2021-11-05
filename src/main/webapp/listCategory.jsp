<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <body>
        <a href="${pageContext.request.getContextPath()}/food">Check your food</a>


        <h2>Categories:</h2>
        <td><a href="category/create">Create</a>

        <table>
            <tr>
                <th>id</th>
                <th>name</th>
                <th>created at</th>
                <th></th>
                <th></th>

            </tr>
            <c:forEach var="category" items="${categories}">
                <tr>
                    <td>${category.getId()}</td>
                    <td>${category.getName()}</td>
                    <td>${category.getCreatedAt()}</td>
                    <td><a href="category/edit?id=${category.getId()}">Edit</a> </td>
                    <td><a href="category/delete?id=${category.getId()}">Delete</a> </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
