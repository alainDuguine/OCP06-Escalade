package com.alain.dao.entities;


import javax.persistence.*;
import javax.servlet.http.Part;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Photo implements Serializable{
    // todo fichier properties
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

    public Photo() {
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
        } catch (IllegalStateException e){
            e.printStackTrace();
            this.erreur = "Les données envoyées sont trop volumineuses.";
        }
        if (this.erreur == null){
            try{
                ecrireFichier(this.contenu, this.nom, CHEMIN);
            }catch (Exception e){
                this.erreur = "Erreur lors de l'écriture du fichier sur le disque";
            }
        }
    }

    private String createPhotoName (Part part, int index) {
        String dateTime;
        String extension;
        String nomGenere;
        extension = getExtension(part);
        dateTime = formatDate(LocalDateTime.now());
        nomGenere = dateTime + index + "." + extension;
        return nomGenere;
    }

    private static String getExtension(Part part) {
        for (String contentDisposition : part.getHeader("content-disposition").split(";")){
            if(contentDisposition.trim().startsWith("filename")){
                return contentDisposition.substring(contentDisposition.indexOf('.')+ 1).trim().replace("\"","");
            }
        }
        return null;
    }

    private static String formatDate(LocalDateTime now) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(myFormatObj);
    }

    private void ecrireFichier(InputStream contenu, String nomPhoto, String chemin) throws Exception{
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
}
