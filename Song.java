package p2;

import java.util.ArrayList;

public class Song implements Comparable<Song>{
    
    //Atributos.
    private String title;
    private int duration;
    private String composer;

    private String sid;
    private String lang;
    
    private ArrayList<String> genre;

    //Constructor.
    public Song(String title, int duration, String composer, String sid, String lang, ArrayList<String> genre) {

	this.title = title;
	this.duration = duration;
	this.composer = composer;
	this.sid = sid;
	this.lang = lang;
	this.genre = genre;
	
    }

    //
    public int compareTo(Song songAux) {

	if(this.genre.size() > songAux.genre.size()) return 1;
	if(this.genre.size() < songAux.genre.size()) return -1;
	if(this.genre.size() == songAux.genre.size()) {

	      if(this.title.compareTo(songAux.title) < 0) return -1;
	      if(this.title.compareTo(songAux.title) > 0) return 1;

	      return 0;
	}
	return 0;
    }
    
    //Getters y Setter.

    public String getTitle() {

	return title;
    }

    public void setTitle(String sTitle) {

	this.title = sTitle;
    }


    //
    public int getDuration() {

	return duration;
    }

    public void setDuration(int sDuration) {

	this.duration = sDuration;
    }


    //
    public String getComposer() {

	return composer;
    }

    public void setComposer(String sComposer) {

	this.composer = sComposer;
    }


    //
    public String getSid() {

	return sid;
    }

    public void setSid(String sSid) {

	this.sid = sSid;
    }


    //
    public String getLang() {

	return lang;
    }

    public void setLang(String sLang) {

	this.lang = sLang;
    }


    //Comprobamos si la canción tiene más de un género para poder presentarlos por pantalla correctamente al llamar a la fase 13.
    public String getGenre() {

	int numberOfGenres = genre.size();

	//String generos = null;
	
	//System.out.println(numberOfGenres);

	if(genre.size() == 1) return genre.get(0);
	if(genre.size() == 2) return genre.get(0) + "," + genre.get(1);
	if(genre.size() == 3) return genre.get(0) + "," + genre.get(1) + "," + genre.get(2);
	if(genre.size() == 4) return genre.get(0) + "," + genre.get(1) + "," + genre.get(2) + "," + genre.get(3);
	if(genre.size() == 5) return genre.get(0) + "," + genre.get(1) + "," + genre.get(2) + "," + genre.get(3) + "," + genre.get(4);		

	   return null;
    }

    public void setGenre(ArrayList<String> sGenre) {

	this.genre = sGenre;
    }
}
