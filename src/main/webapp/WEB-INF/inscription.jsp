<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>Inscription</title>
    <%@ include file="includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../css/form.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "header.jsp"%>
<section class="mainDiv">
    <div class="formDiv">
        <h1>Formulaire d'inscription :</h1>
        <c:if test="${not empty form.resultat}">
            <p class="${form.resultat ? 'succes' : 'echec'}">
                <c:out value="${form.resultat ? 'Inscription effectuée vous pouvez maintenant vous connecter !' : 'Inscription échouée'}"/>
            </p>
        </c:if>
        <form method="post" action="saveUser.do">
            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['email']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="email">Adresse e-mail :</label>
                <input type="email" name="email" id="email" placeholder="name@example.com" required="required" value="<c:out value="${param.email}"/>">
            </div>
            <div class="inscriptionForm">
                <label for="password">Mot de passe :</label>
                <input type="password" name="password" id="password" required="required">
            </div>
            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['password']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="confirmation">Confirmer mot de passe :</label>
                <input type="password" name="confirmation" id="confirmation" required="required">
            </div>
            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['username']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="username">Nom d'utilisateur :</label>
                <input type="text" name="username" id="username" required="required" value="<c:out value="${param.username}"/>">
            </div>
            <div class="inscriptionForm">
                <label for="nom">Nom :</label>
                <input type="text" name="nom" id="nom" maxlength="50" placeholder="Ex : Dupont" value="<c:out value="${param.nom}"/>">
            </div>
            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['prenom']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="prenom">Prénom :</label>
                <input type="text" name="prenom" id="prenom" maxlength="50" placeholder="Ex : Jean" value="<c:out value="${param.prenom}"/>">
            </div>
            <div class="bouton">
                <input type="submit" value="Enregistrer">
            </div>

        </form>
    </div>
</section>
<%@include file="social.jsp"%>
</body>
</html>
