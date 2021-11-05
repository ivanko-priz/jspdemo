<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <body>
        <h2>Create Food</h2>

        <form name="createFoodForm" method="post">
            <input type="hidden" name="action" value="create"/>
            name: <input type="text" name="name"/> <br/>
            description: <input type="text" name="description"/> <br/>
            calories per 100 g: <input type="number" name="caloriesPer100g"/> <br/>

            category:
                <select name="category">
                        <option value="0">--No category--</option>

                        <c:forEach var="category" items="${categories}">
                            <option value="${category.getId()}">${category.getName()}</option>
                        </c:forEach>
                </select>
            <input type="submit" value="Save" />
        </form>

        <form name="goBackForm" method="get" action="../food">
            <input type="submit" value="Cancel" />
        </form>
    </body>
</html>

