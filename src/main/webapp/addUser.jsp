<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ajouter un utilisateur</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f8fb; }
        form { width: 350px; margin: 60px auto; background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 8px #e0e0e0; }
        label { display: block; margin-bottom: 8px; color: #2d8cf0; }
        input[type="text"], input[type="password"] { width: 100%; padding: 10px; margin-bottom: 18px; border: 1px solid #ccc; border-radius: 4px; }
        button { background: #2d8cf0; color: #fff; border: none; padding: 12px 0; width: 100%; border-radius: 4px; font-weight: bold; }
        a { display: block; text-align: center; margin-top: 18px; color: #2d8cf0; text-decoration: none; }
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <input type="hidden" name="action" value="add"/>
    <label>Nom d'utilisateur :</label>
    <input type="text" name="username" required/>
    <label>Mot de passe :</label>
    <input type="password" name="password" required/>
    <button type="submit">Créer l'utilisateur</button>
</form>
<a href="${pageContext.request.contextPath}/login">Retour à la liste</a>
</body>
</html>