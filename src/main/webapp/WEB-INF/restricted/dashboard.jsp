<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord</title>
    <%@ include file="../includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../../css/table.css">
    <link rel="stylesheet" type="text/css" href="../../css/dashboard.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "../header.jsp"%>
<section class="mainDiv">
    <c:if test="${not empty param.resultat}">
        <p class="${param.resultat ? 'succes' : 'echec'}">
            <c:out value="${param.resultat ? 'Enregistrement effectué' : ''}"/>
        </p>
    </c:if>
    <div id="bannerDashboard">
        <nav class="menuDashboard">
            <button class="menu-button" id="Spot">Mes Spots</button>
            <button class="menu-button" id="Secteur">Mes Secteurs</button>
            <button class="menu-button" id="Voie">Mes Voies</button>
        </nav>
    </div>

    <!-- Table Spot -->
    <div class="resultatDiv" id="tableSpot">
        <table>
            <thead>
                <tr>
                    <th>Nom Spot</th><th>Ville</th><th>Département</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${utilisateur.spots}" var="spot">
                <tr class="item">
                    <td><c:out value="${spot.nom}"/></td>
                    <td><c:out value="${spot.ville.nom}"/></td>
                    <td><c:out value="${spot.departement.nom}"/></td>
                    <td><a href="modifierSpot.do?idSpot=${spot.id}">Modifier ce spot</a></td>
                    <td><a href="ajoutSecteur.do?idSpot=${spot.id}">Ajouter un secteur</a></td>
                    <td><a onclick="return confirm('Etes vous sûr de vouloir supprimer ce spot');" href="supprimerSpot.do?id=${spot.id}">Supprimer</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Table Secteur -->
    <div class="resultatDiv" id="tableSecteur">
        <table>
            <thead>
                <tr>
                    <th>Nom Secteur</th><th>Nom Spot</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${utilisateur.secteurs}" var="secteur">
                <tr class="item">
                    <td><c:out value="${secteur.nom}"/></td>
                    <td><c:out value="${secteur.spot.nom}"/></td>
                    <td><a href="modifierSecteur.do?idSecteur=${secteur.id}">Modifier ce secteur</a></td>
                    <td><a href="ajoutVoie.do?idSecteur=${secteur.id}">Ajouter une voie</a></td>
                    <td><a onclick="return confirm('Etes vous sûr de vouloir supprimer ce secteur');" href="supprimerSecteur.do?id=${secteur.id}">Supprimer</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Table Voie -->
    <div class="resultatDiv" id="tableVoie">
        <table>
            <thead>
                <tr>
                    <th>Nom Voie</th><th>Nom Secteur</th><th>Nom Spot</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${utilisateur.voies}" var="voie">
                <tr class="item">
                    <td><c:out value="${voie.nom}"/></td>
                    <td><c:out value="${voie.secteur.nom}"/></td>
                    <td><c:out value="${voie.secteur.spot.nom}"/></td>
                    <td><a href="modifierVoie.do?idVoie=${voie.id}">Modifier cette voie</a></td>
                    <td><a onclick="return confirm('Etes vous sûr de vouloir supprimer cette voie');" href="supprimerVoie.do?id=${voie.id}">Supprimer</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</section>
<script src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
        crossorigin="anonymous"></script>
<script>
    $(document).ready(function () {
        //Affiche chaque taleau (spot, secteur, voie) sur le clic du menu
        $(".menu-button").click(function() {
            $(".resultatDiv").hide()
            $(".menuDashboard button").removeClass("active");
            $(this).addClass("active");
            var idTable = "#table" + $(this).attr("id");
            $(idTable).show();
        });
    })
</script>
</body>
</html>
