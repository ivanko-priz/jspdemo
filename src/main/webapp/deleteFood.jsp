<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <body>
        <h2>Delete Food</h2>

        <form name="deleteFoodForm" method="post">
            <input type="hidden" name="action" value="delete"/>
            <input type="hidden" name="id" value="${food.getId()}"/>
            <input type="submit" value="Delete" />
        </form>

        <form name="goBackForm" method="get" action="../food">
            <input type="submit" value="Cancel" />
        </form>

    </body>
</html>

