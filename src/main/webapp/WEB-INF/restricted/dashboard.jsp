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
                <button class="menu-button" id="Topo">Mes Topos</button>
            </c:if>
            <c:if test="${admin}">
                <button class="menu-button" id="Spot">Les Spots</button>
                <button class="menu-button" id="Secteur">Les Secteurs</button>
                <button class="menu-button" id="Voie">Les Voies</button>
                <button class="menu-button" id="Topo">Les Topos</button>
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
        <!-- Bouton ajout Spot -->
        <div id="addSpot">
            <p class="menu-button" id="addSpotButton"><a href="ajoutSpot.do"><img src="../../images/plus.png"/><span>Ajouter un Spot</span></a></p>
        </div>
    </div>


    <!-- Table Secteur -->
    <div class="resultatDiv" id="tableSecteur">
        <table>
            <thead>
                <tr>
                    <c:if test="${admin}">
                        <th>Id Secteur</th>
                    </c:if>
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
                    <c:if test="${admin}">
                        <td><c:out value="${secteur.id}"/></td>
                    </c:if>
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
                    <c:if test="${admin}">
                        <th>Id Voie</th>
                    </c:if>
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
                    <c:if test="${admin}">
                        <td><c:out value="${voie.id}"/></td>
                    </c:if>
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

    <!-- Table Topo -->
    <div class="resultatDiv" id="tableTopo">
        <h3>Mes topo enregistrés :</h3>
        <table>
            <thead>
            <tr>
                <c:if test="${admin}">
                    <th>Id Topo</th>
                </c:if>
                <th>Nom Topo</th><th>Date Edition</th>
                <c:if test="${admin}">
                    <th>Utilisateur</th>
                </c:if>
                <th>Disponible</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="topos" value="${admin ? listTopo : utilisateur.topos}"/>
            <c:forEach items="${topos}" var="topo">
                <tr class="item" id="${topo.id}">
                    <c:if test="${admin}">
                        <td><c:out value="${topo.id}"/></td>
                    </c:if>
                    <td><c:out value="${topo.nom}"/></td>
                    <td><c:out value="${topo.dateEdition}"/></td>
                    <c:if test="${admin}">
                        <td><c:out value="${topo.utilisateur.username}"/></td>
                    </c:if>
                    <td class="topoTable"><c:out value="${topo.disponible ? 'Oui' : 'Non'}"/></td>
                    <td><button class="buttonTopo" id="dispoTopo" type="button">Disponibilité</button></td>
                    <td><a href="modifierTopo.do?idTopo=${topo.id}">Modifier ce topo</a></td>
                    <td class="supprElem"><a href="supprimerTopo">Supprimer</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!-- Bouton ajout de Topo -->
        <div id="addTopo">
            <p class="menu-button" id="addTopoButton"><a href="ajoutTopo.do"><img src="../../images/plus.png"/><span>Ajouter un Topo</span></a></p>
        </div>

        <!-- Table Pret de Topos-->

        <c:if test="${!admin}">
            <h3>Mes Prêts de topo :</h3>
        </c:if>
        <c:if test="${admin}">
            <h3>Les Prêts de topo :</h3>
        </c:if>
        <table>
            <thead>
            <tr>
                <c:if test="${admin}">
                    <th>Id Topo</th>
                    <th>Id Reservation</th>
                    <th>Propriétaire Topo</th>
                </c:if>
                <th>Nom Topo</th>
                <th>Emprunteur</th>
                <th>Date de réservation</th>
                <th>Statut réservation</th>
                <th>Adresse email</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="prets" value="${admin ? listReservations : utilisateur.prets}"/>
            <c:forEach items="${prets}" var="pret">
                    <tr class="item" id="${pret.id}">
                        <c:if test="${admin}">
                            <td><c:out value="${pret.topo.id}"/></td>
                            <td><c:out value="${pret.id}"/></td>
                            <td><c:out value="${pret.preteur.username}"/></td>
                        </c:if>
                        <td><c:out value="${pret.topo.nom}"/></td>
                        <td><c:out value="${pret.emprunteur.username}"/></td>
                        <td><c:out value="${pret.dateDernierStatut}"/></td>
                        <c:choose>
                            <c:when test="${pret.dernierStatut == 'PENDING'}">
                                <td id="statutReservation">En Attente</td>
                                <td class="containerButtonReservation">
                                    <button class="buttonReservation" id="accepterPret.do" type="button">Accepter</button>
                                    <button class="buttonReservation" id="refuserPret.do" type="button">Refuser</button>
                                </td>

                            </c:when>
                            <c:when test="${pret.dernierStatut == 'APPROVED'}">
                                <td>Acceptée</td>
                                <td>${pret.emprunteur.email}</td>
                            </c:when>
                            <c:otherwise>
                                <td>Refusée</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
            </c:forEach>
            </tbody>
        </table>


        <c:if test="${!admin}">
            <h3>Mes emprunts de topo :</h3>
        </c:if>
        <c:if test="${admin}">
            <h3>Les emprunts de topo :</h3>
        </c:if>
        <table>
            <thead>
            <tr>
                <c:if test="${admin}">
                    <th>Id Topo</th>
                    <th>Id Reservation</th>
                    <th>Emprunteur Topo</th>
                </c:if>
                <th>Nom Topo</th>
                <th>Propriétaire Topo</th>
                <th>Date de réservation</th>
                <th>Statut réservation</th>
                <th>Adresse email</th>

            </tr>
            </thead>
            <tbody>
            <c:set var="emprunts" value="${admin ? listReservations : utilisateur.emprunts}"/>
            <c:forEach items="${emprunts}" var="emprunt">
                <tr class="item" id="${emprunt.id}">
                    <c:if test="${admin}">
                        <td><c:out value="${emprunt.topo.id}"/></td>
                        <td><c:out value="${emprunt.id}"/></td>
                        <td><c:out value="${emprunt.emprunteur.username}"/></td>
                    </c:if>
                    <td><c:out value="${emprunt.topo.nom}"/></td>
                    <td><c:out value="${emprunt.preteur.username}"/></td>
                    <td><c:out value="${emprunt.dateDernierStatut}"/></td>
                    <c:choose>
                        <c:when test="${emprunt.dernierStatut == 'PENDING'}">
                            <td>En Attente</td>
                            <td class="supprElem"><a href="supprimerReservation">Supprimer</a></td>
                        </c:when>
                        <c:when test="${emprunt.dernierStatut == 'APPROVED'}">
                            <td>Acceptée</td>
                            <td>${emprunt.preteur.email}</td>
                        </c:when>
                        <c:otherwise>
                            <td>Refusée</td>
                        </c:otherwise>
                    </c:choose>
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
                    <td class="adminTable"><c:out value="${user.admin? 'Oui':'Non'}"/></td>
                    <td><button class="buttonAdmin"type="button">Administrateur</button></td>
                    <td class="supprElem"><a href="supprimerUser">Supprimer</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    </c:if>


</section>
<script src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
        crossorigin="anonymous"></script>
<script>
    $(document).ready(function () {
        // Affiche chaque taleau (spot, secteur, voie, topo, utilisateur) sur le clic du menu
        $(".menu-button").click(function() {
            $(".resultatDiv").hide()
            $(".menuDashboard button").removeClass("active");
            $(this).addClass("active");
            var idTable = "#table" + $(this).attr("id");
            $(idTable).show();
        });

        // Change le statut disponible d'un topo
        $("#dispoTopo").click(function() {
            var el = $(this);
            var topoId = $(this).parent().parent().attr('id');
            if (confirm("Etes-vous sûr de vouloir modifier la disponibilité de ce topo ?")) {
                $.post("toggleTopo.do", {idTopo: topoId}, function (data) {
                    var result;
                    if (data === 'true'){
                        result = 'Oui';
                    }else{
                        result = 'Non';
                    }
                    $(el).parent().siblings(".topoTable").text(result);
                    alert("Modification de la disponibilité effectuée");
                })
            }
        });

        // Change le statut administrateur de l'utilisateur sur le clic du bouton
        $(".buttonAdmin").click(function() {
            var el = $(this);
            var userId = $(this).parent().parent().attr('id');
            if (confirm("Etes-vous sûr de vouloir modifier les doits utilisateurs pour cet utilisateur ?")) {
                $.post("toggleAdmin.do", {idUser: userId}, function (data) {
                    var result;
                    if (data === 'true'){
                        result = 'Oui';
                    }else{
                        result = 'Non';
                    }
                    $(el).parent().siblings(".adminTable").text(result);
                    alert("Modification des droits effectuée");
                })
            }
        });

        // Suppression des éléments Spots, Secteurs, Voies
        $(".supprElem").click(function(event){
            event.preventDefault();
            var el = $(this),
                path = $(this).children('a').attr('href')+".do",
                elId = $(this).parent().attr('id');
            var msg;
            if (path.includes('Topo')){
                msg = "Etes vous sûr de vouloir supprimer ce topo ?"
            }else if (path.includes('Reservation')){
                msg = "Etes vous sûr de vouloir annuler cette demande de réservation ?"
            }else{
                msg ="Attention ! La suppression d'un élément entraînera la suppression de tous les éléments associés ! Spots, Secteurs, Voies, Photos, Commentaires. Etes-vous sûr de vouloir continuer ?";
            }
            if (confirm(msg)) {
                $.post(path, {idElement: elId}, function (data) {
                    if (data == 'true') {
                        el.parent().remove();
                        if(!alert("Suppression effectuée")){window.location.reload()};
                    } else {
                        alert("Suppression échouée");
                    }
                })
            }
        })

        // Accord ou refus réservation

        $(".buttonReservation").click(function(){
            var idReservation = $(this).parent().parent().attr('id'),
                buttonPath = $(this).attr('id');
            if (buttonPath === "accepterPret.do") {
                msg = "En acceptant la demande de prêt, votre adresse email sera communiquée au demandeur. Etes-vous sûr ?"
            } else {
                msg = "Etes-vous sûr de vouloir refuser la demande de prêt ?"
            }
            if (confirm(msg)){
                $.post(buttonPath, {idReservation: idReservation}, function (data){
                    if (data === 'true') {
                        if (buttonPath === "accepterPret.do"){
                            $('#statutReservation').val("Acceptée");
                            if(!alert("La demande de prêt a bien été acceptée !")){window.location.reload()};
                        }else if(buttonPath === "refuserPret.do"){
                            $('#statutReservation').val("Refusée");
                            alert("La demande de prêt a bien été refusée !");
                            // if(!alert("La demande de prêt a bien été refusée !")){window.location.reload()};
                        }
                    } else {
                        alert("Une erreur est apparue, merci de réessayer plus tard");
                    }
                })
            }
        })


    })
</script>
</body>
</html>
