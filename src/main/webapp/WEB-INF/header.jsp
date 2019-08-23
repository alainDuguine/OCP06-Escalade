<header>
    <div id="logo">
        <a href=""><img src="../img/logo.png" alt="Logo association" title="Notre association" id="img-logo"/></a>
    </div>
    <div id="banner">
        <nav class="menu">
            <p class="menu-button"><a href="index.do">Accueil</a></p>
            <p class="menu-button"><a href="listeSpot.do">Les Spots</a></p>
            <p class="menu-button"><a href="">Les Topos</a></p>
            <p class="menu-button"><a href="">Apprendre</a></p>
            <p class="menu-button"><a href="">Liens</a></p>
            <c:if test="${!empty sessionScope.username}">
                <p class="menu-button"><a href="dashboard.do">Mon Espace</a></p>
            </c:if>
        </nav>
        <nav class="user">
            <c:choose>
                <c:when test="${empty sessionScope.username}">
                    <p class="menu-button"><a href="connexion.do"><img id="icon-user" src="../img/User.png" title="Se connecter" alt="Se connecter"/></a></p>
                    <p class="menu-button"><a href="inscription.do"><img id="icon-addUser" src="../img/addUser.png" title="Créer un compte" alt="Créer un compte"/></a></p>
                </c:when>
                <c:otherwise>
                    <p class="menu-button"><a href="dashboard.do" id="userConnected"><span id="username">${sessionScope.username}</span><img id="icon-userConnected" src="../img/UserConnected.png" title="Tableau de bord" alt="Tableau de bord"/></a></p>
                </c:otherwise>
            </c:choose>
        </nav>
    </div>
</header>