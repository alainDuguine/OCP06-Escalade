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
    <title>Spot : ${spot.nom}</title>
</head>
<body>
<%@ include file= "header.jsp"%>
<section class="mainDiv" id="displayDiv">
    <div class="descriptionDiv">
        <h1>${spot.nom} - ${spot.departement.nom} - ${spot.ville.nom}</h1>
        <h3>${spot.description}</h3>
    </div>
    <div class="gallery">
        <div id="scroller">
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
    <div class="dataSpot">
        <ul class="navTree">
            <c:forEach items="${spot.secteurs}" var="secteur">
                <li class="secteurTree"><input type="checkbox" id="${secteur.id}" hidden="hidden"/><label for="${secteur.id}">Secteur : ${secteur.nom}</label>
                    <ul>
                        <c:forEach items="${secteur.voies}" var="voie">
                            <li class="voieTree"><input type="checkbox" id="${voie.id}" hidden="hidden"/><label for="${voie.id}">Voie : ${voie.nom}</label></li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
        </ul>
    </div>


    <div class="container">
        <ul id="treeview">
            <li class="parent"><a>North America</a>
                <ul>
                    <li class="parent"><a>USA</a>
                        <ul>
                            <li><a>Alabama</a></li>
                            <li><a>New York</a></li>
                        </ul>
                    </li>
                    <li><a>Canada</a></li>
                </ul>
            </li>
            <li class="parent"><a>Asia</a>
                <ul>
                    <li class="parent"><a>China</a>
                        <ul>
                            <li><a>Guangdong</a></li>
                        </ul>
                    </li>
                    <li class="parent"><a>India</a>
                        <ul>
                            <li class="parent"><a>Uttar Pradesh</a>
                                <ul>
                                    <li><a>Lucknow</a></li>
                                    <li><a>Kanpur</a></li>
                                </ul>
                            </li>
                            <li class="parent"><a>Gujarat</a>
                                <ul>
                                    <li><a>Surat</a></li>
                                    <li><a>Ahmedabad</a></li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</section>
<%@include file="social.jsp"%>

<script type="text/javascript" src="../js/lightbox-plus-jquery.min.js"></script>
<%--<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>--%>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script>
    $(document).ready(function(){
        $("#treeview li").click(function (e){
            $("a").removeClass("selected");
            $(this).children("a").addClass("selected");
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
