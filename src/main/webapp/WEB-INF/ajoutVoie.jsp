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
        <h1>Ajouter une voie au secteur - ${secteur.nom} du spot - ${secteur.spot.nom} :</h1>
        <p class="${empty form.listErreurs ? 'success' : 'echec'}">${form.resultat}</p>
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
                <input type="text" name="nom" id="nom" required="required" value="<c:out value="${param.nom}"/>">
            </div>

            <div class="erreur">
                <div class="erreurDescription">${form.listErreurs['description']}</div>
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

            <div class="erreur">
                <div class="erreurAltitude">${form.listErreurs['altitude']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="altitude">Altitude :</label>
                <input type="number" step="5" min="0" name="altitude" id="altitude">
            </div>

            <div class="erreur">
                <div class="erreurLongueur">${form.listErreurs['longueur']}</div>
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
                <div></div>
                <div>${form.listErreurs['photo']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="photo">Ajouter des photos :</label>
                <input type="file" id="photo" name="photo" multiple="multiple" accept="image/jpg, image/png"/>
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
