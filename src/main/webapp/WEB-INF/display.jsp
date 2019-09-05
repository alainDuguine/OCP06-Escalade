<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@ include file="includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../css/gallery.css">
    <link rel="stylesheet" type="text/css" href="../css/displaySpot.css">
    <link rel="stylesheet" type="text/css" href="../css/lightbox.css">
    <link rel="stylesheet" type="text/css" href="../css/displayNav.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
    <title>Spot : <c:out value="${spot.nom}"/></title>
</head>
<body>
<c:set var="chemin">/imagesUsers/</c:set>
<%@ include file= "header.jsp"%>
<section id="displaySpot">
    <section id="navDiv">
        <div class="treeviewDiv">
            <ul class="treeview">
                <li class="parent"><a class="treeElement" href="#${spot.nom}"><c:out value="${spot.nom}"/></a>
                    <ul>
                        <c:forEach items="${spot.secteurs}" var="secteur">
                            <li><a class="treeElement" href="#${secteur.nom}">Secteur : <c:out value="${secteur.nom}"/></a>
                                <ul>
                                    <c:forEach items="${secteur.voies}" var="voie">
                                        <li><a class="treeElement" href="#${voie.nom}">Voie : <c:out value="${voie.nom}"/></a></li>
                                    </c:forEach>
                                    <li><a id="ajoutVoie" href="ajoutVoie.do?idSecteur=${secteur.id}">Ajouter une voie</a></li>
                                </ul>
                            </li>
                        </c:forEach>
                            <li><a id="ajoutSecteur" href="ajoutSecteur.do?idSpot=${spot.id}">Ajouter un secteur</a></li>
                        <li><a class="treeElement" href="#commentaireAncre">Commentaires :</a></li>
                    </ul>
                </li>
            </ul>
        </div>

        <c:if test="${!empty sessionScope.sessionUtilisateur}">
            <div class="topoDisplay">
                <div class="topoDisplayDiv">
                    <h3>Topos disponible :</h3>
                    <c:forEach items="${spot.topos}" var="topo">
                        <div>
                            <p><a><c:out value="${topo.nom}"/></a></p>
                            <button class="buttonReservationTopo" type="button">Demander Réservation</button>
                        </div>
                    </c:forEach>
                </div>
                <a id="ajoutTopo" href="ajoutTopoSpot.do?idSpot=${spot.id}">Ajouter des Topos</a>
            </div>
        </c:if>
    </section>

    <section class="mainDiv" id="displayDiv">
        <span class="ancre" id="${spot.nom}"></span>
        <div class="descriptionDiv">
            <c:if test="${spot.officiel}">
                <img  id="iconeOfficiel" src="../images/officiel.png" alt="Spot officiel" title="Ce spot est validé par l'association !">
            </c:if>
            <h1>Spot - <c:out value="${spot.nom}"/></h1>
            <h5><c:out value="${spot.departement.nom}"/> - <c:out value="${spot.ville.nom}"/> - <i>ajouté par <c:out value="${spot.utilisateur.username}"/></i></h5>
            <h3>Description :</h3>
            <p><c:out value="${spot.description}"/></p>
            <c:if test="${!empty spot.photos}">
                <div class="gallery">
                    <div class="scroller">
                        <c:forEach items="${spot.photos}" var="photo">
                            <a href="${chemin}${photo.nom}" data-lightbox="gallerySpot"><img src="${chemin}${photo.nom}"/></a>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </div>

        <c:forEach items="${spot.secteurs}" var="secteur">
            <span class="ancre" id="${secteur.nom}"></span>
            <div class="descriptionDiv">
                <h1>Secteur - <c:out value="${secteur.nom}"/></h1>
                <h5><i>ajouté par <c:out value="${secteur.utilisateur.username}"/></i></h5>
                <h3>Description :</h3>
                <p><c:out value="${secteur.description}"/></p>
                <c:if test="${!empty secteur.photos}">
                    <div class="gallery">
                        <div class="scroller">
                            <c:forEach items="${secteur.photos}" var="photo">
                                <a href="${chemin}${photo.nom}" data-lightbox="gallerySecteur"><img src="${chemin}${photo.nom}"/></a>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
                <c:forEach items="${secteur.voies}" var="voie">
                    <hr>
                    <span class="ancre" id="${voie.nom}"></span>
                    <div>
                        <h1>Voie - <c:out value="${voie.nom}"/></h1>
                        <h5><i>ajoutée par <c:out value="${voie.utilisateur.username}"/></i></h5>
                        <p>Cotation : <c:out value="${voie.cotation.code}"/></p>
                        <p>Altitude : <c:out value="${voie.altitude}"/></p>
                        <p>Nombre de longueurs : <c:out value="${voie.nbLongueurs}"/></p>
                        <h3>Description :</h3>
                        <p><c:out value="${voie.description}"/></p>
                        <c:if test="${!empty voie.photos}">
                            <div class="gallery">
                                <div class="scroller">
                                    <c:forEach items="${voie.photos}" var="photo">
                                        <a href="${chemin}${photo.nom}" data-lightbox="galleryVoie"><img src="${chemin}${photo.nom}"/></a>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
        <div class="descriptionDiv">
            <h1>Commentaires publics</h1>
            <span class="ancre" id="commentaireAncre">ancre</span>
            <c:if test="${!empty sessionScope.sessionUtilisateur}">
                <div class="commentaireForm">
                    <input type="text" id="usernameCommentaire" hidden="hidden" value="<c:out value="${sessionScope.sessionUtilisateur}"/>"/>
                    <label for="commentaireInput" id="labelCommentaire">Ajouter un commentaire public :</label>
                    <textarea name="commentaire" id="commentaireInput" ></textarea>
                    <input type="button" id="submitCommentaire" value="Ajouter un commentaire">
                </div>
                <hr/>
            </c:if>
            <div class="commentaireDisplay">
                <c:forEach items="${spot.commentaires}" var="commentairePublic">
                    <div class="commentaire">Par <c:out value="${commentairePublic.utilisateur.username}"/> le ${commentairePublic.dateFormat}
                    <br/><span id="content${commentairePublic.id}"><c:out value="${commentairePublic.contenu}"/></span>
                        <c:if test="${admin}">
                            <p class="modifComm" id="${commentairePublic.id}">
                                <a href="modifierCommentaire.do">Modifier</a>
                                <a href="supprimerCommentaire.do">Supprimer</a>
                                <input type="button" style='display:none;' class="updateCommentaire" id="updateCommentaire${commentairePublic.id}" Value="Enregistrer"/>
                                <input type="button" style='display:none;' class="annulerUpdate" id="annulerUpdate${commentairePublic.id}" Value="Annuler"/>
                            </p>
                        </c:if>
                        <hr/>
                    </div>

                </c:forEach>
            </div>
        </div>
    </section>
</section>

<script type="text/javascript" src="../js/lightbox-plus-jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function(){

        // Gestion de l'arborescence d'un spot
        $(".treeview li:has(li)").addClass("parent");

        $(".treeview li").click(function (e){
            $(".treeElement").removeClass("selected");
            $(this).children(".treeElement").addClass("selected");
            e.stopPropagation();
            $(this).find(">ul").toggle("slow");
            if ($(this).hasClass("close"))
                $(this).removeClass("close");
            else
                $(this).addClass("close");
        });


        // Publication des commentaires
        $("#submitCommentaire").click(function(e){
            e.preventDefault();
            var commentaire = $.trim($("#commentaireInput").val());
            var utilisateur = ($("#usernameCommentaire").val());
            var idSpot = ${spot.id};
            if (commentaire.length > 280){
                alert("Un commentaire peut au maximum contenir 280 caractères");
            }else if (commentaire.length == 0) {
                alert("Vous ne pouvez pas publier un commentaire vide")
            }else{
                $.post("saveCommentaire.do", {contenu: commentaire, idSpot: idSpot, utilisateur: utilisateur},
                function (data) {
                    if (!data.hasOwnProperty('erreur')) {
                        $("#commentaireInput").val('');
                        if ($(".commentaire").length) {
                            $(".commentaire").first().before("<p class=\"commentaire\">Par " + data.username + " le "
                                + data.dateFormat + "<br/>" + data.contenu + "</p><hr/>");
                        } else {
                            $(".commentaireDisplay").append("<p class=\"commentaire\">Par " + data.username + " le "
                                + data.dateFormat + "<br/>" + data.contenu + "</p><hr/>");
                        }
                    }else{
                        alert(data.erreur);
                    }
                });
            }
        })

        var commentaireAvantUpdate, idComm

        //Suppression et modification des commentaires par l'administrateur
        $(".modifComm > a").click(function (event) {
            event.preventDefault();
            var el = $(this),
                path = $(this).attr('href');
            idComm = $(this).parent().attr('id');
            if (path === "supprimerCommentaire.do") {
                if (confirm("Etes-vous sûr de vouloir supprimer ce commentaire ?")) {
                    $.post(path, {idElement: idComm}, function (data) {
                        if (data == 'true') {
                            el.parent().parent().remove();
                            alert("Suppression effectuée");
                        } else {
                            alert("Suppression échouée");
                        }
                    })
                }
            // remplace l'affichage du commentaire en input<text> et affiche les boutons de sauvegardes de modification
            }else if (path === "modifierCommentaire.do"){
                event.preventDefault();
                commentaireAvantUpdate = $('#content'+idComm).text().trim();
                $('#content'+idComm).replaceWith("<input id='inputModifCommentaire' type='text' id='content' value='"+commentaireAvantUpdate+"' style='width:100%;height: 2.5em;border-radius: 5px;'/>");
                $('#updateCommentaire'+idComm).show();
                $('#annulerUpdate'+idComm).show();
                $(this).closest('div').focusin();
            }
        });

        //Sauvegarde de la modification d'un commentaire
        $(".updateCommentaire").click(function (event) {
            var contenuComm = $('#inputModifCommentaire').val();
            if (confirm("Etes-vous sûr de vouloir modifier ce commentaire ?")) {
                $.post("updateCommentaire.do", {idCommentaire: idComm, contenu: contenuComm}, function (data) {
                    if (data.resultat == 'true') {
                        if(!alert("Modification effectuée")){window.location.reload()};
                    } else {
                        if(!alert("Modification échouée : "+ data.erreur)){
                            $('#content'+idComm).text(commentaireAvantUpdate);
                        };
                    }
                })
            }
        });

        $(".commentaire").focusout(function(){
            var $elem=$(this);
            setTimeout(function(){
                if(!$elem.find(':focus').length) {
                    $('#inputModifCommentaire').replaceWith("<span id='content" + idComm + "'>" + commentaireAvantUpdate + "</span>");
                    $('#updateCommentaire' + idComm).hide();
                    $('#annulerUpdate' + idComm).hide();
                }
            },0);
        });

        $(".annulerUpdate").click(function () {
            window.location.reload();
        });

    });
</script>
</body>
</html>
