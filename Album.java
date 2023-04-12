package p2;

public class Album implements Comparable<Album>{

    //Atributos de la clase.
    private String name;
    private String country;
    private String interpreter;
    private String isbn;
    private String company;
    private String review;
    private int year;
    private String aid;

    //Constructor del objeto Album.
    public Album(String name, String country, String interpreter, String isbn, String company, String review, int year, String aid) {

	this.name = name;
	this.country = country;
	this.interpreter = interpreter;
	this.isbn = isbn;
	this.company = company;
	this.review = review;
	this.year = year;
	this.aid = aid;
    }


    //
    public int compareTo(Album albumAux) {

	if(this.year < albumAux.year) return -1;
	if(this.year > albumAux.year) return 1;
	if(this.year == albumAux.year) {

	    if(this.name.compareTo(albumAux.name) < 0) return -1;
	      if(this.name.compareTo(albumAux.name) > 0) return 1;

	      return 0;
	    
	}
	return 0;
    }
    
    //Getters y Setters.

    public String getName() {

	return name;
    }

    public void setName(String sName) {

	this.name = sName;
    }

    //
    public String getCountry() {

	return country;
    }

    public void setCountry(String sCountry) {

	this.country = sCountry;
    }

    //
    public String getInterpreter() {

	return interpreter;
    }

    public void setInterpreter(String sInterpreter) {

	this.interpreter = sInterpreter;
    }

    //
    public String getIsbn() {

	return isbn;
    }

    public void setIsbn(String sIsbn) {

	this.isbn = sIsbn;
    }

    //
    public String getCompany() {

	return company;
    }

    public void setCompany(String sCompany) {

	this.company = sCompany;
    }


    //
    public String getReview() {

	return review;
    }

    public void setReview(String sReview) {

	this.review = sReview;
    }

    //
    public int getYear() {

	return year;
    }

    public void setYear(int sYear) {

	this.year = sYear;
    }

      //
    public String getAid() {

	return aid;
    }

    public void setAid(String sAid) {

	this.aid = sAid;
    }
}
