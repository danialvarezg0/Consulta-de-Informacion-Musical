package p2;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.xml.xpath.XPathExpressionException;

import java.util.ArrayList;
    
public class FrontEnd {
    
    //static PrintWriter out;

    //Métodos para procesar la contraseña (el parametro 'p').
    
    //Si no se recibe el parametro 'p':

    //Modo auto.
    public static void missingPauto(boolean auto, HttpServletResponse res) {
	try {
	    
	    res.setCharacterEncoding("utf-8");
	    res.setContentType("text/html");
	    
	    PrintWriter out = res.getWriter();

	    out.println("<?xml version='1.0' encoding='utf-8'?>");
	    out.println("<wrongRequest>no passwd</wrongRequest>");
	} catch(Exception e) {}
    }

    //Modo browser.
    public static void missingPbrowser(boolean auto, HttpServletResponse res) {
	try {
	    
	    res.setCharacterEncoding("utf-8");
	    res.setContentType("text/html");
	    
	    PrintWriter out = res.getWriter();

	    out.println("<meta charset=\"UTF-8\">");
	    out.println("<html><body>");
	    out.println("no passwd");
	    out.println("</body></html>");
	    
	} catch(Exception e) {}
    }

    //Si el parametro 'p' no es correcto:

    //Modo auto.
    public static void wrongPauto(boolean auto, HttpServletResponse res) {
	try {
	    
	    res.setCharacterEncoding("utf-8");
	    res.setContentType("text/html");
	    
	    PrintWriter out = res.getWriter();
	    
	    out.println("<?xml version='1.0' encoding='utf-8'?>");
	    out.println("<wrongRequest>bad passwd</wrongRequest>");
	    
	} catch(Exception e) {}
    }

    //Modo browser.
    public static void wrongPbrowser(boolean auto, HttpServletResponse res) {
	try {
	    
	    res.setCharacterEncoding("utf-8");
	    res.setContentType("text/html");
	    
	    PrintWriter out = res.getWriter();
	   
	    out.println("<meta charset=\"UTF-8\">");
	    out.println("<html><body>");
	    out.println("bad passwd");
	    out.println("</body></html>");
	       
	} catch(Exception e) {}
    }

    //Metodo en caso de que no se reciba algun parametro obligatorio.

    
    public static void noParamPcountryAuto(boolean auto, HttpServletResponse response, String pcountry, String paid, String pphase) {
	try {
		
	    PrintWriter out = response.getWriter();
	  	    
	    out.println("<?xml version='1.0' encoding='utf-8'?>");
	    out.println("<wrongRequest>no param:pcountry</wrongRequest>");
		    
	} catch(Exception e) {}
    }

    
    public static void noParamPcountryBrowser(boolean auto, HttpServletResponse response, String pcountry, String paid, String pphase) {
	try {

	    PrintWriter out = response.getWriter();
	    
	    out.println("<meta charset=\"UTF-8\">");
	    out.println("<html><body>");
	    out.println("no param:pcountry");
	    out.println("</body></html>");
	    
	} catch(Exception e) {}
    }	    

    
    public static void noParamPaidAuto(boolean auto, HttpServletResponse response, String pcountry, String paid, String pphase) {
	try {

	    PrintWriter out = response.getWriter();
	       
	    out.println("<?xml version='1.0' encoding='utf-8'?>");
	    out.println("<wrongRequest>no param:paid</wrongRequest>");
		    
	}catch(Exception e) {}
    }

	    
    public static void noParamPaidBrowser(boolean auto, HttpServletResponse response, String pcountry, String paid, String pphase) {
	try {
	    
	    PrintWriter out = response.getWriter();
		
	    out.println("<meta charset=\"UTF-8\">");
	    out.println("<html><body>");
	    out.println("no param:paid");
	    out.println("</body></html>");
        
	}catch(Exception e) {}
    }
		    
     
    //Métodos para mostrar las fases.

    //Fase 01: Muestra la pantalla inicial del servicio.
   
    //Modo auto.
    public static void doGetFase01auto(boolean auto, HttpServletResponse response, String p) {
	try {	    
	    PrintWriter out = response.getWriter();

	    out.println("<?xml version='1.0' encoding='utf-8'?>");
	    out.println("<service>");
	    out.println("<status>OK</status>");
	    out.println("</service>");

	} catch(Exception e) {}
    }

    //Modo browser.
    public static void doGetFase01browser(boolean auto, HttpServletResponse response, String p) {  
	try{

	    PrintWriter out = response.getWriter();
		
	    out.println("<!DOCTYPE html>");
	    out.println("<html lang=\"es\">");

	    out.println("<head>");
	    out.println("<meta charset=\"UTF-8\">");

	    out.println("<link rel=\"stylesheet\" href=\"p2/styleP2.css\">");

	    out.println("</head>");
	    out.println("<body>");

	    out.println("<h1 id=\"Title\">Servicio de consulta de información musical</h1>");

	    out.println("<div>");
	    
	    out.println("<h2>Bienvenido a este servicio</h2>");	 
	    
	    out.println("<h3>Selecciona una consulta:</h3>");

	    out.println("<hr>");

	    out.println("<li><a href=\"?pphase=02&auto=false&p="+p+"\">Ver los ficheros erroneos</a></li>");
	    out.println("<br>");
		
	    out.println("<li><a href=\"?pphase=11&auto=false&p="+p+"\">Consulta 1: canciones pop de un Album de un Country</a></li><br />");

	    out.println("<hr>");

	    out.println("<h4 id=\"Signature\">&copy; Daniel Álvarez (2022-2023)</h4>");
		 
	    out.println("</div>");
	    
	    out.println("</body>");
	    out.println("</html>");	
	    
	} catch(Exception e) {}
    }

    //Fase 02: Muestra la pantalla con los ficheros erróneos.

    //Modo auto.
    public static void doGetFase02auto(boolean auto, HttpServletResponse response, String p, ArrayList<String> errores, ArrayList<String> erroresFatales) {
	try {

	    //Almacena en la lista errors y fatalErrors las direcciones de los ficheros erróneos leídos en DataModel.
	    //errors = DataModel.getErrores();
	    //fatalErrors = DataModel.erroresFatales;
	    
	    int numberOfErrors = errores.size();
	    int numberOfFatalErrors = erroresFatales.size();
		
	    PrintWriter out = response.getWriter();

	    out.println("<?xml version='1.0' encoding='utf-8'?>");
	    out.println("<wrongDocs>");

	    //Los que no se encuentran dentro del rango establecido.
	    out.println("<errors>");
		
	    for(int i=0; i<numberOfErrors; i++) {

		out.println("<error><file>" + errores.get(i) + "</file></error>");
        
	    }
		
	    out.println("</errors>");

	    //Los que no son Well-Formed.
	    out.println("<fatalerrors>");
	
	    for(int i=0; i<numberOfFatalErrors; i++) {

		out.println("<fatalerror><file>" + erroresFatales.get(i) + "</file></fatalerror>");
	
	    }
		
	    out.println("</fatalerrors>");
	    out.println("</wrongDocs>");
		
	} catch(Exception e) {}
    }

    //Modo browser.
    public static void doGetFase02browser(boolean auto, HttpServletResponse response, String p, ArrayList<String> errores, ArrayList<String> erroresFatales) {
	try {

	    //errors = DataModel.getErrores();
	    //fatalErrors =DataModel.erroresFatales;
	    
	    int numberOfErrors = errores.size();
	    int numberOfFatalErrors = erroresFatales.size();

	    PrintWriter out = response.getWriter();
		
	    out.println("<!DOCTYPE html>");
	    out.println("<html lang=\"es\">");

	    out.println("<head>");
	    out.println("<meta charset=\"UTF-8\">");
	    out.println("<title>fase02</title>");

	    out.println("<link rel=\"stylesheet\" href=\"p2/styleP2.css\">");

	    out.println("</head>");
	    out.println("<body>");

	    out.println("<h1 id=\"Title\">Servicio de consulta de información musical</h1>");

	    out.println("<form>");

	    out.println("<div>");
	
	    out.println("<h2 class=\"Encabezados\">Ficheros con errores: " + numberOfErrors + "</h2>");
	
	    for(int i=0; i<numberOfErrors; i++) {

		//Para que se representen como una lista.
		out.println("<ul>");
		      
		out.println(errores.get(i));
		      
		out.println("</ul>");
        
	    }
	       
	    out.println("<hr>");
		
	    out.println("<br>");

		
	    out.println("<h3 class=\"Encabezados\">Ficheros con errores fatales: " + numberOfFatalErrors + "</h3>");
		
	    for(int i=0; i<numberOfFatalErrors; i++) {
        
		out.println("<ul>");

		out.println(erroresFatales.get(i));
		    
		out.println("</ul>");
	
	    }

	    out.println("<hr>");

	    //Instrucciones para poder volver a la fase inicial a traves de un botón:
		
	    //Fase a la que queremos volver.
	    out.println("<input type=\"hidden\" value=\"01\" name=\"pphase\">");
	    //Añade la contraseña a la url.
	    out.println("<input type=\"hidden\" value='"+p+"' name=\"p\">");
	    //Botón para enviar.
	    out.println("<input class=\"boton\" type=\"submit\" value=\"Atrás\" onclick=\"form.pphase.value=\"01\"\">");
		
	    out.println("</form>");
		
	    out.println("<h4 id=\"Signature\">&copy; Daniel Álvarez (2022-2023)</h4>");

	    out.println("</div>");
	    
	    out.println("</body>");
	    out.println("</html>");		
		
	} catch(Exception e) {}
    }


    //Fase 11: Muestra la lista de los países de los diferentes álbumes.
    
    //Modo auto.
    public static void doGetFase11auto(boolean auto, HttpServletResponse response, String p, ArrayList<String> countries) {
	try {

	    //Almacena en la lista countries los diferentes países obtenidos tras la lectura del fichero en DataModel.
	    int numberOfCountries = countries.size();
		
	    PrintWriter out = response.getWriter();

	    out.println("<?xml version='1.0' encoding='utf-8'?>");
	    out.println("<countries>");

	    //Recorremos el ArrayList de países.
	    for(int i=0; i<numberOfCountries; i++) {
        
		out.println("<country>" + countries.get(i) + "</country>");
	    }
		 
	    out.println("</countries>");
		
	} catch(Exception e) {}
    }

    //Modo browser.
    public static void doGetFase11browser(boolean auto, HttpServletResponse response, String p, ArrayList<String> countries) {
	try {
	    
	    int numberOfCountries = countries.size();
	    
	    PrintWriter out = response.getWriter();
		
	    out.println("<!DOCTYPE html>");
	    out.println("<html lang=\"es\">");

	    out.println("<head>");
	    out.println("<meta charset=\"UTF-8\">");
	    out.println("<title>fase11</title>");

	    out.println("<link rel=\"stylesheet\" href=\"p2/styleP2.css\">");

	    out.println("</head>");
	    out.println("<body>");

	    out.println("<h1 id=\"Title\">Servicio de consulta de información musical</h1>");

	    out.println("<div>");

	    out.println("<h2>Consulta 1: Fase 1</h2>");

	    out.println("<h3>Selecciona un Country:</h3>");

	    out.println("<hr>");

	    out.println("<ol>");
	    
	    for(int i=0; i<numberOfCountries; i++) {
		   
		out.println("<li>");
		out.println("<a href=\"?pphase=12&auto=false&p="+p+"&pcountry=" + countries.get(i) + "\"/>" +countries.get(i) + "</a>");
		out.println("<br>");
		out.println("</li>");
		   
	    }
	    out.println("</ol>");

	    //Instrucciones para poder volver a la fase inicial a traves de un botón:
		
	    out.println("<form>");
	    //Fase a la que queremos volver.
	    out.println("<input type=\"hidden\" value=\"01\" name=\"pphase\">");
	    //Añade la contraseña a la url.
	    out.println("<input type=\"hidden\" value='"+p+"' name=\"p\">");
	    //Botón para enviar.
	    out.println("<input class=\"boton\" type=\"submit\" value=\"Inicio\" onclick=\"form.pphase.value=\"01\"\">");	
	    out.println("</form>");

	    out.println("<hr>");
		
	    out.println("<h4 id=\"Signature\">&copy; Daniel Álvarez (2022-2023)</h4>");

	    out.println("</div>");
	    
	    out.println("</body>");
	    out.println("</html>");		
	    
	}catch(Exception e){}
    }

    //Fase 12: Muestra la lista de álbumes del país seleccionado.

    //Modo auto.
    public static void doGetFase12auto(boolean auto, HttpServletResponse response, String p, String pcountry, ArrayList<Album> albums){		
	try {

	    //Almacena en la lista albums los diferentes álbumes obtenidos tras la lectura de los ficheros en DataModel.
	    //  albums = DataModel.getQ1Albums(pcountry);
	    int numberOfAlbums = albums.size();	  
	    	
	    PrintWriter out = response.getWriter();

	    out.println("<?xml version='1.0' encoding='utf-8'?>");
	    out.println("<albums>");
		
	    for(int i=0; i<numberOfAlbums; i++) {
		
		out.println("<album year='"+albums.get(i).getYear()+"' performer='"+albums.get(i).getInterpreter()+"' review='"+albums.get(i).getReview()+"'>"+albums.get(i).getName()+"</album>");
	    }
		       
	    out.println("</albums>");
			
	} catch(Exception e) {}
    }

    //Modo browser.
    public static void doGetFase12browser(boolean auto, HttpServletResponse response, String p, String pcountry, ArrayList<Album> albums){		
	try {

	    //  albums = DataModel.getQ1Albums(pcountry);
	    int numberOfAlbums = albums.size();	  
	   
	    PrintWriter out = response.getWriter();
		
	    out.println("<!DOCTYPE html>");
	    out.println("<html lang=\"es\">");

	    out.println("<meta charset=\"UTF-8\">");
        
	    out.println("<link rel=\"stylesheet\" href=\"p2/styleP2.css\">");
	  
	    out.println("<body>");

	    out.println("<h1 id=\"Title\">Servicio de consulta de información musical</h1>");
	   
	    out.println("<div>");
	    
	    out.println("<h2>Consulta 1: Fase 2(Country = "+pcountry+")</h2>");

	    out.println("<h3>Selecciona un Album:</h3>");

	    out.println("<hr>");

	    //Lista numerada.
	    out.println("<ol>");
		
	    for(int i=0; i<numberOfAlbums; i++) {
        
		out.println("<li>");

		out.println("<a href=\"?pphase=13&auto=false&p="+p+"&pcountry="+pcountry+"&paid="+albums.get(i).getAid()+"\"/>Álbum ='"+albums.get(i).getName()+"</a>'--- Año ='"+albums.get(i).getYear()+"---Intérprete ='"+albums.get(i).getInterpreter()+"'--- Review ='"+albums.get(i).getReview()+"'");
		  
		out.println("</li>");       
	    }

	    out.println("</ol>");
	        
	    //Instrucciones para poder volver a la fase inicial a traves de un botón:
	    out.println("<form>");	
	    //Fase a la que queremos volver.
	    out.println("<input type=\"hidden\" value=\"01\"' name=\"pphase\">");
	    //Añade la contraseña a la url.
	    out.println("<input type=\"hidden\" value='"+p+"' name=\"p\">");
	    //Botón para enviar.
	    out.println("<input class=\"boton\" type=\"submit\" value=\"Inicio\" onclick=\"form.pphase.value=\"01\"\">");
	    out.println("</form>");
	        
	    //Instrucciones para poder volver a la fase anterior a traves de un botón:
	    out.println("<form>");
	    //Fase a la que queremos volver.
	    out.println("<input type=\"hidden\" value=\"11\"' name=\"pphase\">");
	    //Añade la contraseña a la url.
	    out.println("<input type=\"hidden\" value='"+p+"' name=\"p\">");
	    //Botón para enviar.
	    out.println("<input class=\"boton\" type=\"submit\" value=\"Atrás\" onclick=\"form.pphase.value=\"11\"\">");

	    out.println("</form>");

	    out.println("<hr>");
		
	    out.println("<h4 id=\"Signature\">&copy; Daniel Álvarez (2022-2023)</h4>");

	    out.println("</div>");
	    
	    out.println("</body>");
	    out.println("</html>");	
	    
	}catch(Exception e){}
    }

    //Fase 13: Muestra la lista de canciones del álbum seleccionado.

    //Modo auto.
    public static void doGetFase13auto(boolean auto, HttpServletResponse response, String p, String pcountry, String paid, ArrayList<Song> songs) {	
	try {

	    //Almacena en la lista songs las diferentes canciones obtenidas tras la lectura de los ficheros en DataModel.
	    //   songs = DataModel.getQ1Songs(pcountry, paid);
	    int numberOfSongs = songs.size();
		
	    PrintWriter out = response.getWriter();

	    out.println("<?xml version='1.0' encoding='utf-8' ?>");
	    out.println("<songs>");
        
	    for(int i=0; i<numberOfSongs; i++) {
		      
		out.println("<song lang='"+songs.get(i).getLang()+"' genres='"+songs.get(i).getGenre()+"' composer= '"+songs.get(i).getComposer()+"'>"+songs.get(i).getTitle()+"</song>");
	    }
	     
	    out.println("</songs>");
		
	} catch(Exception e) {}
    }

    //Modo browser.
    public static void doGetFase13browser(boolean auto, HttpServletResponse response, String p, String pcountry, String paid, ArrayList<Song> songs) {	
	try {

	    // songs = DataModel.getQ1Songs(pcountry, paid);
	    int numberOfSongs = songs.size();
		
	    PrintWriter out = response.getWriter();
		
	    out.println("<!DOCTYPE html>");
	    out.println("<html lang=\"es\">");
	    out.println("<meta charset=\"UTF-8\">");
        
	    out.println("<link rel=\"stylesheet\" href=\"p2/styleP2.css\">");
	
	    out.println("<body>");
		
	    out.println("<h1 id=\"Title\">Servicio de consulta de información musical</h1>");
		
	    //   out.println("<br>");
	    out.println("<div>");
		
	    out.println("<h2>Consulta 1: Fase 3(Country = "+pcountry+", Album = "+paid+")</h2>");
	
	    // out.println("<br>");

	    out.println("<h3>Este es el resultado de la consulta:</h3>");

	    out.println("<hr>");

	    out.println("<ol>");
			  
	    for(int i=0; i<numberOfSongs; i++) {

		out.println("<li>");

		out.println("Título ='"+songs.get(i).getTitle()+"'---Idioma ='"+songs.get(i).getLang()+"'---Géneros ='"+songs.get(i).getGenre()+"'---Compositor ='"+songs.get(i).getComposer()+"'");
		  		  
		out.println("</li>");
	    }

	    out.println("</ol>");
		
	    //Instrucciones para poder volver a la fase inicial a traves de un botón:
		
	    out.println("<form>");
	    //Fase a la que queremos volver.
	    out.println("<input type=\"hidden\" value=\"01\" name=\"pphase\">");
	    //Anade la contraseña a la url.
	    out.println("<input type=\"hidden\" value='"+p+"' name=\"p\">");
	    //Botón para enviar.
	    out.println("<input class=\"boton\" type=\"submit\" value=\"Inicio\" onclick=\"form.pphase.value=\"01\"\">");
	    out.println("</form>");

		
	    //Instrucciones para poder volver a la fase anterior a traves de un boton:
		
	    out.println("<form>");
	    //Fase a la que queremos volver.
	    out.println("<input type=\"hidden\" value=\"12\" name=\"pphase\">");
	    //Añade la contraseña a la url.
	    out.println("<input type=\"hidden\" value='"+p+"' name=\"p\">");
	    //Añade el país a la url.
	    out.println("<input type=\"hidden\" value='"+pcountry+"' name=\"pcountry\">");
	    //Añade el id a la url.
	    out.println("<input type=\"hidden\" value='"+paid+"' name=\"paid\">");
	    //Botón para enviar.
	    out.println("<input class=\"boton\" type=\"submit\" value=\"Atrás\" onclick=\"form.pphase.value=\"12\"\">");
	    out.println("</form>");

	    out.println("<hr>");
		
	    out.println("<h4 id=\"Signature\">&copy; Daniel Álvarez (2022-2023)</h4>");

	    out.println("</div>");
	    
	    out.println("</body>");
	    out.println("</html>");
		
	}catch(Exception e) {}   
    }
}




