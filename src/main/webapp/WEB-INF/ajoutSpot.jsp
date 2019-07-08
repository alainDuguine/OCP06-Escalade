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
        <form method="post" action="saveSpot.do">

            <div class="erreur">
                <div></div>
                <div>${listErreur['nom']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="nom">Nom du spot :</label>
                <input type="text" name="nom" id="nom" required="required" value="<c:out value="${param.nom}"/>">
            </div>

            <div class="erreur">
                <div></div>
                <div>${listErreur['adresse']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="adresse">Adresse :</label>
                <input type="text" name="adresse" id="adresse" required="required" value="<c:out value="${param.adresse}"/>">
            </div>

            <div class="inscriptionForm">
                <label for="departement">Département :</label>
                <select name="departement" id="departement" required="required" value="<c:out value="${param.departement}"/>">
                    <option value="" selected>Choisissez un département</option>
                    <option value="Ain">Ain</option>
                    <option value="Aisne">Aisne</option>
                    <option value="Allier">Ain</option>
                    <option value=" Alpes-de-Haute-Provence">Alpes-de-Haute-Provence</option>
                    <option value="Hautes-Alpes">Hautes-Alpes</option>
                </select>
            </div>

            <div class="erreur">
                <div></div>
                <div>${listErreur['ville']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="ville">Ville :</label>
                <input type="text" name="ville" id="ville" required="required" value="<c:out value="${param.ville}"/>">
            </div>

            <div class="erreur">
                <div></div>
                <div>${listErreur['description']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="description">Description :</label>
                <textarea name="description" id="description" required="required" value="<c:out value="${param.description}"/>"></textarea>
            </div>


            <div class="erreur">
                <div></div>
                <div>${listErreur['photo']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="photo">Ajouter des photos :</label>
                <input type="file" id="photo" name="photo" value="<c:out value="${photo.nom}"/>" multiple="multiple" accept="image/*"/>
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
