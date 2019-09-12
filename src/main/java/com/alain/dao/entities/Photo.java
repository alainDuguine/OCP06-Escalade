package com.alain.dao.entities;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import javax.servlet.http.Part;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Photo implements Serializable{

    private static final Logger logger = LogManager.getLogger("Photo");

    private static final String CHEMIN = "D:\\fichiers\\";
    private static final int TAILLE_TAMPON = 10240;
    public static final String CHAMP_PHOTO = "photo";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nom;

    @Transient
    private String erreur;

    @Transient
    private InputStream contenu;



    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public Photo() {
    }

    public Photo(String nom, String erreur, InputStream contenu) {
        this.nom = nom;
        this.erreur = erreur;
        this.contenu = contenu;
    }

    /* ********************************************************************************************
     **** METHODS           ************************************************************************
     ******************************************************************************************** */

    public void uploadPhoto(Part part, int index){
        try {
            this.setNom(createPhotoName(part, index));
            this.contenu = part.getInputStream();
            if (this.contenu == null){
                this.erreur = "Le fichier est vide";
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.erreur = "Erreur de configuration du serveur";
            logger.error("Probleme upload photo " + Arrays.toString(e.getStackTrace()));
        } catch (IllegalStateException e){
            e.printStackTrace();
            this.erreur = "Les données envoyées sont trop volumineuses.";
            logger.warn("Probleme upload photo " + Arrays.toString(e.getStackTrace()));
        }
        if (this.erreur == null){
            try{
                ecrireFichier(this.contenu, this.nom, CHEMIN);
            }catch (Exception e){
                this.erreur = "Erreur lors de l'écriture du fichier sur le disque";
                logger.error("Probleme upload photo " + Arrays.toString(e.getStackTrace()));
            }
        }
    }

    private String createPhotoName (Part part, int index) {
        String dateTime;
        String extension;
        String nomGenere;
        extension = getExtension(part);
        if ((!extension.equalsIgnoreCase("jpg")) && (!extension.equalsIgnoreCase("png")) && (!extension.equalsIgnoreCase("jpeg"))){
            this.erreur = "Les fichiers doivent être au format jpg ou png";
        }
        dateTime = formatDate(LocalDateTime.now());
        nomGenere = dateTime + index + "." + extension;
        logger.info("Nom fichier généré : " + nomGenere);
        return nomGenere;
    }

    private static String getExtension(Part part) {
        for (String contentDisposition : part.getHeader("content-disposition").split(";")){
            if(contentDisposition.trim().startsWith("filename")){
                String extension = contentDisposition.substring(contentDisposition.indexOf('.')+ 1).trim().replace("\"","");
                logger.info("Récupération de l'extension du fichier " + extension);
                return extension;
            }
        }
        return null;
    }

    private static String formatDate(LocalDateTime now) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(myFormatObj);
    }

    private void ecrireFichier(InputStream contenu, String nomPhoto, String chemin) throws Exception{
        logger.info("Ecriture du fichier sur disque");
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try{
            entree = new BufferedInputStream(contenu, TAILLE_TAMPON);
            sortie = new BufferedOutputStream(new FileOutputStream(new File(CHEMIN + this.nom)), TAILLE_TAMPON);
            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur = 0;
            while((longueur = entree.read(tampon))>0){
                sortie.write(tampon,0,longueur);
            }
            logger.info("Ecriture du fichier terminée");
        }finally {
            try{
                sortie.close();
            }catch (IOException ignore){
            }
            try{
                entree.close();
            }catch (IOException ignore){
            }
        }
    }

    /* ***********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     *********************************************************************************************** */

    public static String getCHEMIN() {
        return CHEMIN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getErreur() {
        return erreur;
    }

    public void setErreur(String erreur) {
        this.erreur = erreur;
    }

    public InputStream getContenu() {
        return contenu;
    }

    public void setContenu(InputStream contenu) {
        this.contenu = contenu;
    }
}
