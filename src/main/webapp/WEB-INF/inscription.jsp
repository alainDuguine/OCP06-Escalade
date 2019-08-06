<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscription</title>
    <%@ include file="includeCss.jsp"%>
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "header.jsp"%>
<section class="mainDiv" id="mainDivCenter">
    <div class="formDiv">
        <h1>Formulaire d'inscription :</h1>
        <p class="${empty form.listErreurs ? 'succesSuscribe' : 'echecSuscribe'}">${form.resultat}</p>
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
                <div>${form.listErreurs['nom']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="nom">Nom :</label>
                <input type="nom" name="nom" id="nom" placeholder="Ex : Dupont" required="required" value="<c:out value="${param.nom}"/>">
            </div>
            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['prenom']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="prenom">Prénom :</label>
                <input type="prenom" name="prenom" id="prenom" placeholder="Ex : Jean" required="required" value="<c:out value="${param.prenom}"/>">
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
