$(document).ready(function() {

    // Gestion de l'arborescence d'un spot
    $(".treeview li:has(li)").addClass("parent");

    $(".treeview li").click(function (e) {
        $(".treeElement").removeClass("selected");
        $(this).children(".treeElement").addClass("selected");
        e.stopPropagation();
        $(this).find(">ul").toggle("slow");
        if ($(this).hasClass("close"))
            $(this).removeClass("close");
        else
            $(this).addClass("close");
    });

    //Affichage de la description d'un topo dans un popup sur click
    var idTopo;

    $(".itemTopo a").click(function (e) {
        e.preventDefault();
        idTopo = $(this).parent().prev('div').attr('id');
        $("#"+idTopo+" > div").toggleClass("show");
        $(this).focus();
    });

    $('.itemTopo a').blur(function(){
        $("#"+idTopo+" > div").toggleClass("show");
    });

});
