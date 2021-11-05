<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <body>
        <h2>Edit Food</h2>
        <form name="editFood" method="post">
            <input type="hidden" name="action" value="update"/>
            name: <input type="text" name="name" value="${food.getName()}"/> <br/>
            description: <input type="text" name="description" value="${food.getDescription()}"> <br/>
            calories per 100 g: <input type="number" name="caloriesPer100g" value="${food.getCaloriesPer100g()}">/> <br/>
            category:
                <select name="category">
                        <option value="0">--No category--</option>

                        <c:forEach var="category" items="${categories}">
                            <option value="${category.getId()}" ${food.getFoodCategoryId() == category.getId() ? "selected" : ""} >${category.getName()}</option>
                        </c:forEach>
                </select>
            <input type="submit" value="Save" />
        </form>

        <form name="goBackForm" method="get" action="../food">
            <input type="submit" value="Cancel" />
        </form>

    </body>
</html>

