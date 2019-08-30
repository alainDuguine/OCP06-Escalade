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
    <c:set var="admin" value="${sessionScope.admin}"/>

    <c:if test="${not empty param.resultat}">
        <p class="${param.resultat ? 'succes' : 'echec'}">
            <c:out value="${param.resultat ? 'Enregistrement effectué' : ''}"/>
        </p>
    </c:if>
    <div id="bannerDashboard">
        <nav class="menuDashboard">
            <c:if test="${!admin}">
                <button class="menu-button" id="Spot">Mes Spots</button>
                <button class="menu-button" id="Secteur">Mes Secteurs</button>
                <button class="menu-button" id="Voie">Mes Voies</button>
            </c:if>
            <c:if test="${admin}">
                <button class="menu-button" id="Spot">Les Spots</button>
                <button class="menu-button" id="Secteur">Les Secteurs</button>
                <button class="menu-button" id="Voie">Les Voies</button>
                <button class="menu-button" id="Utilisateur">Les Utilisateurs</button>
            </c:if>
        </nav>
    </div>
    <!-- Table Spot -->
    <div class="resultatDiv" id="tableSpot">
        <table>
            <thead>
                <tr>
                    <c:if test="${admin}">
                        <th>Id Spot</th>
                    </c:if>
                    <th>Nom Spot</th><th>Ville</th><th>Département</th>
                    <c:if test="${admin}">
                        <th>Utilisateur</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
            <%--   Si le user est admin, on affiche tous les spots  --%>
            <c:set var="spots" value="${ admin ? listSpot : utilisateur.spots}"/>
            <c:forEach items="${spots}" var="spot">
                <tr class="item" id="${spot.id}">
                    <c:if test="${admin}">
                        <td><c:out value="${spot.id}"/></td>
                    </c:if>
                    <td><a href="display.do?idSpot=${spot.id}"><c:out value="${spot.nom}"/></a></td>
                    <td><c:out value="${spot.ville.nom}"/></td>
                    <td><c:out value="${spot.departement.nom}"/></td>
                    <c:if test="${admin}">
                        <td><c:out value="${spot.utilisateur.username}"/></td>
                    </c:if>
                    <td><a href="modifierSpot.do?idSpot=${spot.id}">Modifier ce spot</a></td>
                    <td><a href="ajoutSecteur.do?idSpot=${spot.id}">Ajouter un secteur</a></td>
                    <c:if test="${admin}">
                        <td class="supprElem"><a href="supprimerSpot">Supprimer</a></td>
                    </c:if>
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
                    <c:if test="${admin}">
                        <th>Utilisateur</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
            <c:set var="secteurs" value="${admin ? listSecteur : utilisateur.secteurs}"/>
            <c:forEach items="${secteurs}" var="secteur">
                <tr class="item" id="${secteur.id}">
                    <td><a href="display.do?idSpot=${secteur.spot.id}#${secteur.nom}"><c:out value="${secteur.nom}"/></a></td>
                    <td><c:out value="${secteur.spot.nom}"/></td>
                    <c:if test="${admin}">
                        <td><c:out value="${secteur.utilisateur.username}"/></td>
                    </c:if>
                    <td><a href="modifierSecteur.do?idSecteur=${secteur.id}">Modifier ce secteur</a></td>
                    <td><a href="ajoutVoie.do?idSecteur=${secteur.id}">Ajouter une voie</a></td>
                    <c:if test="${admin}">
                        <td class="supprElem"><a href="supprimerSecteur">Supprimer</a></td>
                    </c:if>
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
                    <c:if test="${admin}">
                        <th>Utilisateur</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
            <c:set var="voies" value="${admin ? listVoie : utilisateur.voies}"/>
            <c:forEach items="${voies}" var="voie">
                <tr class="item" id="${voie.id}">
                    <td><a href="display.do?idSpot=${voie.secteur.spot.id}#${voie.nom}"><c:out value="${voie.nom}"/></a></td>
                    <td><c:out value="${voie.secteur.nom}"/></td>
                    <td><c:out value="${voie.secteur.spot.nom}"/></td>
                    <c:if test="${admin}">
                        <td><c:out value="${voie.utilisateur.username}"/></td>
                    </c:if>
                    <td><a href="modifierVoie.do?idVoie=${voie.id}">Modifier cette voie</a></td>
                    <c:if test="${admin}">
                        <td class="supprElem"><a href="supprimerVoie">Supprimer</a></td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <c:if test="${admin}">
    <!-- Table Utilisateur -->
    <div class="resultatDiv" id="tableUtilisateur">
        <table>
            <thead>
            <tr>
                <th>Id utilisateur</th><th>Nom Utilisateur</th><th>Email</th><th>Nom</th><th>Prénom</th><th>Admin</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listUtilisateur}" var="user">
                <tr class="item" id="${user.id}">
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.username}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.nom}"/></td>
                    <td><c:out value="${user.prenom}"/></td>
                    <td class="adminTable"><c:out value="${user.admin}"/></td>
                    <td><button class="buttonAdmin"type="button">Administrateur</button></td>
                    <td class="supprElem"><a href="supprimerUser">Supprimer</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    </c:if>

    <div id="addSpot">
        <p class="menu-button" id="addSpotButton"><a href="ajoutSpot.do"><img src="../../images/plus.png"/><span>Ajouter un Spot</span></a></p>
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

        //Change le statut administrateur de l'utilisateur sur le clic du bouton
        $(".buttonAdmin").click(function() {
            var el = $(this);
            var userId = $(this).parent().parent().attr('id');
            if (confirm("Etes-vous sûr de vouloir modifier les doits utilisateurs pour cet utilisateur ?")) {
                $.post("toggleAdmin.do", {idUser: userId}, function (data) {
                    $(el).parent().siblings(".adminTable").text(data);
                    alert("Modification des droits effectuée");
                })
            }

        });

        //Suppression des éléments Spots, Secteurs, Voies
        $(".supprElem").click(function(event){
            event.preventDefault();
            var el = $(this),
                path = $(this).children('a').attr('href')+".do",
                elId = $(this).parent().attr('id');
            if (confirm("Etes-vous sûr de vouloir supprimer cet élément ?")) {
                $.post(path, {idElement: elId}, function (data) {
                    if (data == 'true') {
                        el.parent().remove();
                        alert("Suppression effectuée");
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
