<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <title>Modifier un Spot</title>
    <%@ include file="../includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../../css/form.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "../header.jsp"%>
<section class="mainDiv">
    <c:set var="admin" value="${sessionScope.admin}"/>
    <div class="formDiv">
        <h1>Modifier un spot d'escalade :</h1>
        <form method="post" action="updateSpot.do" enctype="multipart/form-data">
            <div class="erreur">
                <div class="erreurSingleCol">${form.listErreurs['server']}</div>
            </div>
            <input type="text" name="idElement" id="idElement" hidden="hidden" value="<c:out value="${spot.id}"/>">
            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['nom']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="nom">Nom du spot :</label>
                <input type="text" name="nom" id="nom" maxlength="50" required="required" value="<c:out value="${spot.nom}"/>">
            </div>

            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['adresse']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="adresse">Adresse :</label>
                <input type="text" name="adresse" id="adresse" required="required" value="<c:out value="${spot.adresse}"/>">
            </div>

            <div class="inscriptionForm">
                <label for="departement">Département :</label>
                <select name="departement" id="departement" required="required">
                    <option value="">Choisissez un département</option>
                    <c:forEach items="${departements}" var="departement">
                        <option value="${departement.code}">${departement.code} - <c:out value="${departement.nom}"/></option>
                    </c:forEach>
                </select>
            </div>

            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['ville']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="ville">Ville :</label>
                <select name="ville" id="ville" required="required">
                    <option value="">Choisissez une ville</option>
                    <c:forEach items="${villes}" var="ville">
                        <option value="${ville.id}"><c:out value="${ville.nom}"/></option>
                    </c:forEach>
                </select>
            </div>

            <div class="erreur">
                <div>${form.listErreurs['description']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="description">Description :</label>
                <textarea name="description" id="description" required="required"><c:out value="${spot.description}"/></textarea>
            </div>

            <c:if test="${admin}">
                <div class="officielCheckbox">
                    <c:choose>
                        <c:when test="${spot.officiel}">
                            <input type="checkbox" id="officiel" name="officiel" value="officiel" checked/>
                        </c:when>
                        <c:otherwise>
                            <input type="checkbox" id="officiel" name="officiel" value="officiel"/>
                        </c:otherwise>
                    </c:choose>
                    <label for="officiel">Officiel "Les amis de l'escalade"</label>
                </div>
            </c:if>

            <br>
            <hr>
            <div class="inscriptionForm" id="photoForm">
                <h5 id="photoLabel">Photos enregistrées :</h5>
                <div  class="modifPhoto">
                    <c:set var="chemin">/imagesUsers/</c:set>
                    <c:forEach items="${spot.photos}" var="photo">
                        <div class="photoSaved"><img src="${chemin}${photo.nom}"><a href="${photo.id}">Supprimer</a></div>
                    </c:forEach>
                </div>
            </div>

            <br>
            <hr>

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
<%@include file="../social.jsp"%>
<script src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
        crossorigin="anonymous">
</script>
<script src="../../js/checkPhotos.js" charset="UTF-8"></script>
<script src="../../js/deletePhotos.js" charset="UTF-8"></script>
<script>
    $(document).ready(function(){

        // Affichage Département et ville depuis l'enregistrement et changement possible

        var departement =${spot.departement.code};

        if (departement < 10){
            departement = "0" + departement;
        }

        var ville =${spot.ville.id};
        if (ville < 10){
            ville = "0" + ville;
        }

        $('#departement').change(function() {
            var choixDep = $('#departement').val();
            $.getJSON("choixDepartement.do",{codeDep: choixDep},
                function (data) {
                    $("#ville").empty();
                    var option = "<option value=''>Choisissez une ville</option>";
                    $("#ville").append(option);
                    $.each( data, function(key, val) {
                        var valToString = val.toString();
                        var valToArray = valToString.split(",");
                        var option = "<option value=" + valToArray[0] + ">" + valToArray[1] + "</option>";
                        $("#ville").append(option);
                    });
                    $("#ville option[value="+ville+"]").prop('selected', true);
                }
            );
        });

        $("#departement option[value="+departement+"]").prop('selected', true);

        $('#departement').trigger("change");

    });
</script>
</body>
</html>
