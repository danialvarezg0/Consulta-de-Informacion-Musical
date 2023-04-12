package p2;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

public class DataModel {

    //PARSER

    //Mapa en el que se van a ir guardando los diferentoes ficheros. La clave es el Year y el contenido el documento.
    static HashMap<String, org.w3c.dom.Document> documents = new HashMap<String, org.w3c.dom.Document>();

    //ArrayList en los que almacenamos las direcciones de los ficheros con errores y errores fatales.
    private static ArrayList<String> errores = new ArrayList<String>();
    private static  ArrayList<String> erroresFatales = new ArrayList<String>();

    //Método Parser. Extrae la información necesaria para poder leer un fichero xml.
    public static void Parser(String nuevoFichero, DocumentBuilder db) {
      	try {

	    Document doc = db.parse(nuevoFichero);
	    XPath xpath = XPathFactory.newInstance().newXPath();

	    //Buscamos el año del fichero.
	    NodeList nlYear = (NodeList)xpath.evaluate("/Music/Year", doc, XPathConstants.NODESET);
	    Element elemYear = (Element)nlYear.item(0);
	    String Year = elemYear.getTextContent().trim();
	    int yearInt = Integer.parseInt(Year);

	    //Descartamos los ficheros que no se encuentren entre el rango establecido.
	    if(!((yearInt < 1980) || ( yearInt > 2021))) {

		//Si el año se encuentra dentro del rango, añadimos el fichero al mapa.
		documents.put(Year, doc);
	    } else {

		errores.add(nuevoFichero);
		
		//Si el fichero es erróneo ya no tenemos que seguir buscando otros nuevos, por lo que salimos del método.
		return;
	    }

	    //Buscamos el elemento muml del fichero para ir accediendo a otros nuevos.
	    NodeList nlSongs = (NodeList)xpath.evaluate("Music/Album/Song", doc, XPathConstants.NODESET);
	    
	    //Recorremos las canciones buscando el elemento Muml.
	    for(int i = 0; i < nlSongs.getLength(); i++) {

		Element compElemMuml = (Element)nlSongs.item(i);
		NodeList nlMuml = compElemMuml.getElementsByTagName("MuML");	    
		
		//Primero comprobamos que la canción actual tiene el elemento MuMl.
		if(nlMuml.getLength() != 0) {

		    Element elemMuml = (Element)nlMuml.item(0);
		    String nuevoMuml = elemMuml.getTextContent().trim();
  
		    String url = "http://alberto.gil.webs.uvigo.es/SINT/22-23/"+nuevoMuml;
		    
		    //System.out.println(url);

		    //Si todo es correcto volvemos a llamar al método pasándole el nuevo fichero y se repite el proceso.
		    Parser(url, db);
		
		}//FIN IF
		 
	    }//FIN FOR
	     
	}//FIN TRY
	
	catch(IOException i) {}
	catch(SAXException s) {

	    //Añadimos los errores fatales capturados por la excepción en el ArrayList de errores fatales.
	    erroresFatales.add(nuevoFichero);
	    

	}//FIN CATCH SAXException

	catch(XPathExpressionException e) {}
    } 


    //METODOS PARA LA CONSULTA 1.
    
    //Método que organiza los países del ArrayList en orden alfabético inverso.
    public static ArrayList<String> getQ1Countries() throws XPathExpressionException {
	
	//Lista en la que se van a guardar los paises ya ordenados.
	ArrayList<String> paisesOrdenados = new ArrayList<String>();

	//Creamos un mapa para almacenar los países y que no aparezcan duplicados.
	HashSet<String> noDuplicados = new HashSet<String>();
	
	XPath xpath = XPathFactory.newInstance().newXPath();
	
	NodeList nlAlbums = null;
	NodeList nlCountries = null;

	Iterator<String> it = documents.keySet().iterator();

	//Recorremos el mapa en el que se almacenan los ficheros que se van a leer. (Esto se hace previamente en el parser).
        while(it.hasNext()) {

	    String clave = it.next();
	    Document docu = documents.get(clave);

	    //Direcciones a las que se tiene que acceder para obtener el elemento deseado.
	    nlAlbums = (NodeList)xpath.evaluate("/Music/Album", docu, XPathConstants.NODESET);
	    nlCountries = (NodeList)xpath.evaluate("/Music/Album/Country", docu, XPathConstants.NODESET);

	    //Recorremos el NodeList que hace referencia a los países del fichero que se está leyendo en ese momento.
	    for(int i = 0; i < nlAlbums.getLength(); i++) {
	    
		Element elemCountry = (Element)nlAlbums.item(i);
		NodeList nlCountryName = elemCountry.getElementsByTagName("Country");
	    
		//Solo hay un país por cada álbum por lo que cogemos el elemento 0.
		Element elemCountryName = (Element)nlCountryName.item(0);
		String Country = elemCountryName.getTextContent().trim();

		//Añadimos el país al HashSet de forma que, si alguno aparece duplicado, no se guarde.
		noDuplicados.add(Country); 
	    }
	}
	
	//Almacenamos todos los países en el ArrayList.
	paisesOrdenados.addAll(noDuplicados);	
	//Ordenamos los países por orden alfabético (por defecto).
	Collections.sort(paisesOrdenados);
	//Invertimos el orden.
	Collections.reverse(paisesOrdenados);

	return paisesOrdenados;
    }

    //Método que organiza los álbumes de un determinado país. Se ordenan por año (en orden creciente) y a igualdad de año por orden alfabético del nombre del álbum.
    public static ArrayList<Album> getQ1Albums(String country) throws XPathExpressionException {

	//Lista en la que almacenamos los álbumes leídos.
	ArrayList<Album> albumes = new ArrayList<Album>();

	XPath xpath = XPathFactory.newInstance().newXPath();	
	Iterator<String> it = documents.keySet().iterator();

	//Recorremos el mapa en el que se almacenan los ficheros que se van a leer.
        while(it.hasNext()) {

	    String clave = it.next();
	    Document docu = documents.get(clave);

	    int year_entero = 0;
	 
	    //Direcciones a las que se tiene que acceder para obtener el elemento deseado.
	    NodeList  nlAlbums = (NodeList)xpath.evaluate("Music/Album/Country", docu, XPathConstants.NODESET);
	    NodeList nlNames = (NodeList)xpath.evaluate("/Music/Album/Name", docu, XPathConstants.NODESET);
	    NodeList nlInterpreter = (NodeList)xpath.evaluate("/Music/Album", docu, XPathConstants.NODESET);

	    //Recorremos el NodeList que hace referencia a los álbumes del fichero que se está leyendo en ese momento.
	    for(int i = 0; i < nlAlbums.getLength(); i++) {

		Element elemCountry = (Element)nlAlbums.item(i);
		String compCountry = elemCountry.getTextContent().trim();

		//Comprobamos si el país que recibe el método es el mismo que el que se está leyendo en ese momento, ya que nos interesa usar el recibido, y el resto descartarlos.
		if(compCountry.equals(country)) {
		   
		    //1. NOMBRE ALBUM
		    Element elemAlbum = (Element)nlNames.item(i);
		    String nombreAlbum = elemAlbum.getTextContent().trim();

		    //2. INTERPRETE
		    Element elemInterpreter = (Element)nlInterpreter.item(i);
		    NodeList nlSinger = elemInterpreter.getElementsByTagName("Singer");
		    NodeList nlGroup = elemInterpreter.getElementsByTagName("Group");

		    //3. CRITICA
		    NodeList nlReview = elemInterpreter.getChildNodes();
		    String comentario = "";
		    for(int j = 0; j < nlReview.getLength(); j++) {

			Node review = nlReview.item(j);
			if(review.getNodeType() == org.w3c.dom.Node.TEXT_NODE) {

			    comentario = comentario + review.getNodeValue();
			}
			comentario = comentario.trim();
		    }

		    //4. ID
		    Element elemAid = (Element)nlInterpreter.item(i);
		    String albId = elemAid.getAttribute("aid");

		    //Comprobamos si el álbum tiene como intérprete Singer o Group:
		    //Si el NodeList de singer es distinto de 0 significa que existe.
		    if(nlSinger.getLength() != 0) {
			//Cogemos el elemento 0, ya que solo tiene uno.
			Element elemSinger = (Element)nlSinger.item(0);
			String nombreInterprete = elemSinger.getTextContent().trim();

			//convertimos el año a entero para poder inicializarlo en el constructor.
			year_entero = Integer.parseInt(clave);
			
			//Creamos un álbum y lo inicializamos con los datos obtenidos.
			Album album = new Album(nombreAlbum, null, nombreInterprete, null, null, comentario, year_entero, albId);
			albumes.add(album);
			
		    } else {
			//Si el NodeList de singer es igual a 0 entonces el intérprete es un grupo.
			Element elemGroup = (Element)nlGroup.item(0);
			String  nombreGrupo = elemGroup.getTextContent().trim();

			year_entero = Integer.parseInt(clave);
			
			Album albumAux = new Album(nombreAlbum, null, nombreGrupo, null, null, comentario, year_entero, albId);
			albumes.add(albumAux);
		    }		
		} else {}	
	    }

	    //Ordenamos los álbumes almacenados en la lista según el criterio establecido en la clase Album.
	    Collections.sort(albumes);
	}
	return albumes;
    }
    

    //Método que organiza las diferentes canciones de un determinado álbum. (Se ordenan primero según las que tengan menos géneros y, a igualdad de géneros, por orden alfabético del título).
    public static ArrayList<Song> getQ1Songs(String country, String album) throws XPathExpressionException{

	//Lista en la que almacenamos las canciones obtenidas tras leer el fichero.
	ArrayList<Song> canciones = new ArrayList<Song>();

	XPath xpath = XPathFactory.newInstance().newXPath();
	Iterator<String> it = documents.keySet().iterator();

	//Recorremos el mapa en el que se almacenan los ficheros que se van a leer.
        while(it.hasNext()) {

	    String clave = it.next();
	    Document docu = documents.get(clave);

	    //Dirección en la que leer la info. de los álbumes.
	    NodeList nlAlbums = (NodeList)xpath.evaluate("Music/Album", docu, XPathConstants.NODESET);
	    NodeList nlSongs = (NodeList)xpath.evaluate("Music/Album[@aid='"+album+"']/Song", docu, XPathConstants.NODESET);
	    
	    //Recorremos los álbumes quedándonos solo con el que coincide con el país y el id recibido.
	    for(int i = 0; i < nlAlbums.getLength(); i++) {

		Element elemCountry = (Element)nlAlbums.item(i);
		NodeList nlCountry = elemCountry.getElementsByTagName("Country");
		Element elemCountry2 = (Element)nlCountry.item(0);
		String compCountry = elemCountry2.getTextContent().trim();

		//Primero comprobamos el páis.
		if(compCountry.equals(country)) {
		    
		    Element elemAid = (Element)nlAlbums.item(i); 
		    String compAlbumId = elemAid.getAttribute("aid");

		    //Después comprobamos el id.
		    if(compAlbumId.equals(album)) {

			//Recorremos las canciones y nos quedamos con la del álbum seleccionado.
			for(int j = 0; j < nlSongs.getLength(); j++) {

			    //1. NOMBRE CANCION
			    Element elemName = (Element)nlSongs.item(j);
			    NodeList nlName = elemName.getElementsByTagName("Title");
			    Element elemName2 = (Element)nlName.item(0);
			    String titulo = elemName2.getTextContent().trim();

			    //2. IDIOMA
			    Element elemLang = (Element)nlSongs.item(j);
			    String idioma = elemLang.getAttribute("lang");

			    //3. COMPOSITOR
			    Element elemComposer = (Element)nlSongs.item(j);
			    NodeList nlComposer = elemComposer.getElementsByTagName("Composer");
			    Element elemComposer2 = (Element)nlComposer.item(0);
			    String compositor = elemComposer2.getTextContent().trim();
			    
			    //4. GENEROS

			    //Primero obtenemos el id de las canciones del álbum que estamos leyendo.
			    Element elemSid = (Element)nlSongs.item(j);
			    String sid = elemSid.getAttribute("sid");

			    //Definimos la dirección que nos lleva a los géneros de la canción con el id leído.
			    NodeList nlGenresAux = (NodeList)xpath.evaluate("Music/Album[@aid='"+album+"']/Song[@sid='"+sid+"']/Genre", docu, XPathConstants.NODESET);

			    //Lista que guarda los géneros de una canción.
			    ArrayList<String> generos = new ArrayList<String>();
			    
			    //Recorremos la lista de géneros y descartamos todas las canciones que no sean pop.
			    for(int x = 0; x < nlGenresAux.getLength(); x++) {
		        			
				Element elemGenre = (Element)nlGenresAux.item(x);        
				String genero = elemGenre.getTextContent().trim();
      	
				generos.add(genero);
					
			    }//FIN FOR GENEROS


			    //Recorremos el array de géneros y comprobamos si Pop está entre ellos, de ser el caso creamos la canción y la añadimos al array de canciones.
			    for(int x = 0; x < generos.size(); x++) {
	
				if((generos.get(x)).equals("Pop")) {

				    //System.out.println(titulo +" "+ compositor +" "+ idioma +" "+ generos);
				    
				    Song song = new Song(titulo, 0, compositor, null, idioma, generos);
				    
				    canciones.add(song);
				}	
				
			    }//FIN FOR ARRAY GENEROS		
			    
			}//FIN FOR CANCIONES
			
		    }//FIN IF ID
		    
		}//FIN IF PAISES
		
	    }//FIN FOR ALBUMES
	    
	    //Ordenamos las canciones según el criterio establecido en la clase Song.
	    Collections.sort(canciones);
	    
	}//FIN WHILE

	return canciones;
	
    }//FIN GETQ1SONGS



    //Constructor de la clase.
    public DataModel(ArrayList<String> errores, ArrayList<String> erroresFatales) {

	this.errores = errores;
	this.erroresFatales = erroresFatales;
	
    }
    

    //Get Set
    public static ArrayList<String> getErrores() {

	return errores;
	
    }

    public void setErrores(ArrayList<String> errores) {

	this.errores = errores;
    }


    //
    public static ArrayList<String> getErroresFatales() {

	return erroresFatales;
	
    }

    public void setErroresFatales(ArrayList<String> erroresFatales) {

	this.erroresFatales = erroresFatales;
    }

    
    
    
}//FIN DATAMODEL









