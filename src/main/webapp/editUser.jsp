<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Modifier l'utilisateur</title>
</head>
<body>
<h2>Modifier l'utilisateur</h2>
<form action="${pageContext.request.contextPath}/login" method="post">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="${editUser.id}" />
    <label>Nom d'utilisateur :</label>
    <input type="text" name="username" value="${editUser.username}" required/><br/>
    <label>Mot de passe :</label>
    <input type="password" name="password" value="${editUser.password}" required/><br/>
    <button type="submit">Enregistrer</button>
</form>
<a href="${pageContext.request.contextPath}/login">Annuler</a>
</body>
</html>
