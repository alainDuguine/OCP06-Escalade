$(document).ready(function() {

    // Gestion de l'arborescence d'un spot
    $(".treeview li:has(li)").addClass("parent");

    $(".treeElement").click(function (e) {
        $(".treeElement").removeClass("selected");
        $(this).addClass("selected");
        e.stopPropagation();
        $(this).parent().find(">ul").toggle("slow");
        if ($(this).parent().hasClass("close"))
            $(this).parent().removeClass("close");
        else
            $(this).parent().addClass("close");
    });

    //Affichage de la description d'un topo dans un popup sur click
    var idTopo;

    $(".description").click(function (e) {
        e.preventDefault();
        idTopo = $(this).parent().prev('div').attr('id');
        $("#"+idTopo+" > div").toggleClass("show");
        $(this).focus();
    });

    $(".parent a[class=description]").blur(function(){
        $("#"+idTopo+" > div").toggleClass("show");
    });

});
