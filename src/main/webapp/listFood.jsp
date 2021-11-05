<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <body>
        <a href="${pageContext.request.getContextPath()}/category">Check your categories</a>

        <h2>Foods:</h2>

        <a href="food/create">Create</a>

        <table>
            <tr>
                <th>id</th>
                <th>name</th>
                <th>category</th>
                <th>description</th>
                <th>calories per 100 g</th>
                <th>created at</th>
                <th></th>
                <th></th>

            </tr>
            <c:forEach var="food" items="${foods}">
                <tr>
                    <td>${food.getId()}</td>
                    <td>${food.getName()}</td>
                    <td>${food.getCategory()}</td>
                    <td>${food.getDescription()}</td>
                    <td>${food.getCaloriesPer100g()}</td>
                    <td>${food.getCreatedAt()}</td>
                    <td><a href="food/edit?id=${food.getId()}">Edit</a> </td>
                    <td><a href="food/delete?id=${food.getId()}">Delete</a> </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
