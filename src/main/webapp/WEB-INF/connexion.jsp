<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscription</title>
    <%@ include file="includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../css/form.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "header.jsp"%>
<section class="mainDiv">
    <div class="formDiv">
        <h1>Connexion :</h1>
        <c:if test="${not empty form.resultat}">
            <p class="${form.resultat ? 'succes' : 'echec'}">
                <c:out value="${form.resultat ? 'Connexion réussie' : 'Connexion échouée'}"/>
            </p>
        </c:if>
        <form method="post" action="connexionForm.do">
            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['email']}</div>
            </div>
            <div class="connexionForm">
                <c:set var="email" value="${empty cookieEmail ? param.email : cookieEmail}"/>
                <label for="email">Adresse e-mail :</label>
                <input type="email" name="email" id="email" placeholder="name@example.com" required="required" value="<c:out value="${email}"/>">
            </div>
            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['password']}</div>
            </div>
            <div class="connexionForm">
                <label for="password">Mot de passe :</label>
                <input type="password" name="password" id="password" required="required" />
            </div>
            <div>
                <input type="checkbox" name="cookie" id="cookie">
                <label for="cookie" class="connexionLabel">Se souvenir de moi</label>
            </div>
            <br>
            <span class="connexionLabel"><a href="">Mot de passe oublié</a></span>
            <div class="bouton">
                <input type="submit" value="Se Connecter">
            </div>
            <span class="connexionLabel">Pas encore membre ? <a href="inscription.do">Inscrivez-vous !</a></span>
        </form>
    </div>
</section>
<%@include file="social.jsp"%>
</body>
</html>
