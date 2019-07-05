<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un Spot</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "header.jsp"%>
<section class="mainDiv" id="mainDivCenter">
    <div id="inscriptionDiv">
        <h1>Ajouter un spot d'escalade :</h1>
<%--        <p class="${empty listErreur ? 'succesSuscribe' : 'echecSuscribe'}">${resultat}</p>--%>
        <form method="post" action="saveUser.do">
            <div class="erreur">
                <div></div>
                <div>${listErreur['name']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="name">Nom du spot :</label>
                <input type="text" name="name" id="name" placeholder="" required="required" value="<c:out value="${param.name}"/>">
            </div>
            <div class="inscriptionForm">
                <label for="address">Adresse :</label>
                <input type="text" name="address" id="address" required="required">
            </div>
            <div class="erreur">
                <div></div>
                <div>${listErreur['address']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="region">Region :</label>
                <input type="password" name="confirmation" id="confirmation" required="required">
            </div>
            <div class="erreur">
                <div></div>
                <div>${listErreur['nom']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="nom">Nom :</label>
                <input type="nom" name="nom" id="nom" placeholder="Ex : Dupont" required="required" value="<c:out value="${param.nom}"/>">
            </div>
            <div class="erreur">
                <div></div>
                <div>${listErreur['prenom']}</div>
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
