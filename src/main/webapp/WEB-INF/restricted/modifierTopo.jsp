<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
<%--    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />--%>
<%--    <meta http-equiv="Pragma" content="no-cache" />--%>
<%--    <meta http-equiv="Expires" content="0" />--%>
    <title>Modifier un Topo</title>
    <%@ include file="../includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../../css/form.css">
    <link rel="stylesheet" type="text/css" href="../../css/table.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "../header.jsp"%>
<section class="mainDiv">
    <div class="formDiv">
        <h1>Modifier un topo :</h1>
        <form method="post" action="updateTopo.do">
            <div class="erreur">
                <div class="erreurSingleCol">${form.listErreurs['server']}</div>
            </div>
            <input type="text" name="idElement" id="idElement" hidden="hidden" value="<c:out value="${topo.id}"/>">
            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['nom']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="nom">Nom du topo :</label>
                <input type="text" name="nom" id="nom" maxlength="50" required="required" value="<c:out value="${topo.nom}"/>">
            </div>

            <div class="erreur">
                <div>${form.listErreurs['description']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="description">Description :</label>
                <textarea name="description" id="description" required="required"><c:out value="${topo.description}"/></textarea>
            </div>

            <div class="erreur">
                <div>${form.listErreurs['dateParution']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="dateParution" id="labelParution">Date de parution</label>
                <input type="date" id="dateParution" name="dateParution" min="1950-01-01" required="required" value="${topo.dateEdition}"/>
            </div>
            <br>
            <hr>
            <div class="inscriptionForm" id="photoForm">
                <h5 id="listeSpots">Spots référencés dans le topo :</h5>
                <div  class="modifTopo">
                    <table>
                        <thead>
                        <tr>
                            <th>Nom Spot</th>
                            <th>Département</th>
                            <th>Ville</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${topo.spots}" var="spot">
                            <tr class="item">
                                <c:set var="idSpot" value="${spot.id}"/>
                                <td><c:out value="${spot.nom}"/></td>
                                <td><c:out value="${spot.departement.code} - ${spot.departement.nom}"/></td>
                                <td><c:out value="${spot.ville.nom}"/></td>
                                <td class="spotInTopo"><a href="${spot.id}">Supprimer le spot</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
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
<script>
    $(document).ready(function () {
        $(".spotInTopo > a").click(function(event){
            event.preventDefault();
            var topoId = $('#idElement').val(),
                spotId = $(this).attr('href');
            if (confirm("Etes-vous sûr de vouloir supprimer ce spot de ce topo ?")) {
                $.post("supprimerSpotInTopo.do", {idSpot: spotId, idTopo: topoId}, function (data) {
                    if (data === 'true') {
                        if(!alert("Suppression effectuée")){window.location.reload()}
                    } else {
                        alert("Suppression échouée");
                    }
                })
            }
        })
    })
</script>
</body>
</html>