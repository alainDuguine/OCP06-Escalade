<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@ include file="includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../css/gallery.css">
    <link rel="stylesheet" type="text/css" href="../css/displaySpot.css">
    <link rel="stylesheet" type="text/css" href="../css/lightbox.min.css">
    <link rel="stylesheet" type="text/css" href="../css/treeview.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
    <title>Spot : <c:out value="${spot.nom}"/></title>
</head>
<body>
<c:set var="chemin">file:///D:/fichiers/</c:set>
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
                    </ul>
                </li>
            </ul>
        </div>
    </section>
    <section class="mainDiv" id="displayDiv">
        <span class="ancre" id="${spot.nom}"></span>
        <div class="descriptionDiv">
            <h1><c:out value="${spot.nom}"/></h1>
            <h5><c:out value="${spot.departement.nom}"/> - <c:out value="${spot.ville.nom}"/></h5>
            <h3>Description :</h3>
            <p><c:out value="${spot.description}"/></p>
        </div>
        <div class="gallery">
            <div class="scroller">
<%--                <c:forEach items="${spot.photos}" var="photo">--%>
<%--                    <a href="${chemin}${photo.nom}" data-lightbox="mygallery"><img src="${chemin}${photo.nom}"/></a>--%>
<%--                </c:forEach>--%>
                <a href="../img/adventure-1807524_1920.jpg" data-lightbox="mygallery"><img src="../img/adventure-1807524_1920.jpg"></a>
                <a href="../img/climb-2805903_1920.jpg" data-lightbox="mygallery"><img src="../img/climb-2805903_1920.jpg"></a>
                <a href="../img/climber-299018_1920.jpg" data-lightbox="mygallery"><img src="../img/climber-299018_1920.jpg"></a>
                <a href="../img/climber-984380_1920.jpg" data-lightbox="mygallery"><img src="../img/climber-984380_1920.jpg"></a>
                <a href="../img/climbing-1761386_1920.jpg" data-lightbox="mygallery"><img src="../img/climbing-1761386_1920.jpg"></a>
                <a href="../img/mountain-806915_1920.jpg" data-lightbox="mygallery"><img src="../img/mountain-806915_1920.jpg"></a>
                <a href="../img/mountain-918637_1920.jpg" data-lightbox="mygallery"><img src="../img/mountain-918637_1920.jpg"></a>
                <a href="../img/mountain-climber-2427191_1920.jpg" data-lightbox="mygallery"><img src="../img/mountain-climber-2427191_1920.jpg"></a>
                <a href="../img/mountain-climbing-802099_1920.jpg" data-lightbox="mygallery"><img src="../img/mountain-climbing-802099_1920.jpg"></a>
                <a href="../img/mountaineer-2100050_1920.jpg" data-lightbox="mygallery"><img src="../img/mountaineer-2100050_1920.jpg"></a>
                <a href="../img/mountaineering-895659_1920.jpg" data-lightbox="mygallery"><img src="../img/mountaineering-895659_1920.jpg"></a>
                <a href="../img/person-1245959_1920.jpg" data-lightbox="mygallery"><img src="../img/person-1245959_1920.jpg"></a>
                <a href="../img/rock-climbing-403487_1920.jpg" data-lightbox="mygallery"><img src="../img/rock-climbing-403487_1920.jpg"></a>
                <a href="../img/rock-climbing-1283693_1920.jpg" data-lightbox="mygallery"><img src="../img/rock-climbing-1283693_1920.jpg"></a>
                <a href="../img/saxon-switzerland-539418_1920.jpg" data-lightbox="mygallery"><img src="../img/saxon-switzerland-539418_1920.jpg"></a>
                <a href="../img/summit-1209168_1920.jpg" data-lightbox="mygallery"><img src="../img/summit-1209168_1920.jpg"></a>
                <a href="../img/woman-2594934_1920.jpg" data-lightbox="mygallery"><img src="../img/woman-2594934_1920.jpg"></a>
            </div>
        </div>
        <c:forEach items="${spot.secteurs}" var="secteur">
            <span class="ancre" id="${secteur.nom}"></span>
            <div class="descriptionDiv">
                <h1>Secteur - <c:out value="${secteur.nom}"/></h1>
                <h3>Description :</h3>
                <p><c:out value="${secteur.description}"/></p>
                <div class="gallery">
                    <div class="scroller">
                            <%--                <c:forEach items="${secteur.photos}" var="photo">--%>
                            <%--                    <a href="${chemin}${photo.nom}" data-lightbox="mygallery"><img src="${chemin}${photo.nom}"/></a>--%>
                            <%--                </c:forEach>--%>
                        <a href="../img/adventure-1807524_1920.jpg" data-lightbox="mygallery"><img src="../img/adventure-1807524_1920.jpg"></a>
                        <a href="../img/climb-2805903_1920.jpg" data-lightbox="mygallery"><img src="../img/climb-2805903_1920.jpg"></a>
                        <a href="../img/climber-299018_1920.jpg" data-lightbox="mygallery"><img src="../img/climber-299018_1920.jpg"></a>
                    </div>
                </div>
                <c:forEach items="${secteur.voies}" var="voie">
                    <hr>
                    <span class="ancre" id="${voie.nom}"></span>
                    <div>
                        <h1>Voie - <c:out value="${voie.nom}"/></h1>
                        <p>Cotation : <c:out value="${voie.cotation}"/></p>
                        <p>Altitude : <c:out value="${voie.altitude}"/></p>
                        <p>Nombre de longueurs : <c:out value="${voie.nbLongueurs}"/></p>
                        <h3>Description :</h3>
                        <p><c:out value="${voie.description}"/></p>
                        <div class="gallery">
                            <div class="scroller">
                                    <%--                <c:forEach items="${voie.photos}" var="photo">--%>
                                    <%--                    <a href="${chemin}${photo.nom}" data-lightbox="mygallery"><img src="${chemin}${photo.nom}"/></a>--%>
                                    <%--                </c:forEach>--%>
                                <a href="../img/adventure-1807524_1920.jpg" data-lightbox="mygallery"><img src="../img/adventure-1807524_1920.jpg"></a>
                                <a href="../img/climb-2805903_1920.jpg" data-lightbox="mygallery"><img src="../img/climb-2805903_1920.jpg"></a>
                                <a href="../img/climber-299018_1920.jpg" data-lightbox="mygallery"><img src="../img/climber-299018_1920.jpg"></a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </section>
    <section class="commentaire">

    </section>
</section>
<%--<%@include file="social.jsp"%>--%>

<script type="text/javascript" src="../js/lightbox-plus-jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function(){

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
    });
</script>
</body>
</html>
