<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter une Voie</title>
    <%@ include file="includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../css/form.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "header.jsp"%>
<section class="mainDiv">
    <div class="formDiv">
        <h1>Ajouter une voie - <c:out value="${secteur.nom}"/> - <c:out value="${secteur.spot.nom}"/></h1>
        <c:if test="${not empty form.resultat}">
            <p class="${form.resultat ? 'success' : 'echec'}">
                <c:out value="${form.resultat ? 'Enregistrement effectué' : 'Enregistrement échoué'}"/>
            </p>
        </c:if>
        <form method="post" action="saveVoie.do" enctype="multipart/form-data">
            <div class="erreur">
                <div class="erreurDescription">${form.listErreurs['server']}</div>
            </div>
            <input type="text" name="idSpot" id="idSpot" hidden="hidden" value="<c:out value="${param.idSpot}"/>">
            <input type="text" name="idSecteur" id="idSecteur" hidden="hidden" value="<c:out value="${param.idSecteur}"/>">
            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['nom']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="nom">Nom de la voie :</label>
                <input type="text" name="nom" id="nom" maxlength="50" required="required" value="<c:out value="${param.nom}"/>">
            </div>

            <div class="inscriptionForm">
                <label for="cotation">Cotation :</label>
                <select name="cotation" id="cotation" required="required">
                    <option value="">Choisissez une cotation</option>
                    <c:forEach items="${cotations}" var="cotation">
                        <option value="${cotation.id}">${cotation.code}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="inscriptionForm">
                <label for="altitude">Altitude :</label>
                <input type="number" step="1" min="0" name="altitude" id="altitude">
            </div>

            <div class="inscriptionForm">
                <label for="longueur">Nombre de longueurs :</label>
                <input type="number" step="1" min="0" name="longueur" id="longueur">
            </div>

            <div class="erreur">
                <div class="erreurDescription">${form.listErreurs['description']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="description">Description :</label>
                <textarea name="description" id="description" required="required" ><c:out value="${param.description}"/></textarea>
            </div>

            <div class="erreur">
                <div>${form.listErreurs['photo']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="photo" id="labelPhoto">Ajouter des photos (format jpeg, png) :<br> (Taille max par fichier : 5 Mo, taille totale max : 50 Mo): <br><span id="taillePhoto"></span></label>
                <input type="file" id="photo" name="photo" multiple="multiple" accept="image/jpg, image/png"/>
            </div>

            <div class="bouton">
                <input type="submit" value="Enregistrer">
            </div>

        </form>
    </div>
</section>
<%@include file="social.jsp"%>
<script src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
        crossorigin="anonymous">
</script>
<script src="../js/checkPhotos.js"></script>
</body>
</html>
