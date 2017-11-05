<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
   <c:forEach var="m" items="${ manual }">
        <tr>
          <td>${ m.article_title }</td>
          <td>${ m.article_content }</td>
        </tr>
      </c:forEach>

</body>
</html>
