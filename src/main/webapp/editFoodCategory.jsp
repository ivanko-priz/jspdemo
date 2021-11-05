<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <body>
        <h2>Edit Food Category</h2>

        <form name="editFoodCategoryForm" method="post">
            <input type="hidden" name="action" value="update"/>
            <input type="hidden" name="id" value="${category.getId()}"/>
            name: <input type="text" name="name" value="${category.getName()}"/> <br/>
            <input type="submit" value="Save" />
        </form>

        <form name="goBackForm" method="get" action="../category">
            <input type="submit" value="Cancel" />
        </form>
    </body>
</html>

