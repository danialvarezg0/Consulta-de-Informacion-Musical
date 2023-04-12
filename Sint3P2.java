package p2;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

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

//@WebServlet ("/P2Mu")
public class Sint3P2 extends HttpServlet {

    private final static String FICHERO_INICIAL = "http://alberto.gil.webs.uvigo.es/SINT/22-23/muml2001.xml";
    
    //Método inicial. Crea el parser de la clase DataModel.
    public void init() {
	try {

	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();

	    //Llamamos al parser y le pasamos como parámetros el primer fichero que se va a leer así como el db.
	    DataModel.Parser(FICHERO_INICIAL, db);

	}catch(ParserConfigurationException p) {}
    }
    

    //Método que identifica las diferentes fases del servicio.
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {

	    boolean auto;
	    String autoEnCadena = null;
	    String password = "password12";

	    //Parámetros obligatorios en determinadas fases. (Se especifica en cuáles).
	    String pcountry = req.getParameter("pcountry");
	    String paid = req.getParameter("paid");
	    
	    //Fase solicitada al servidor.
	    String pphase = req.getParameter("pphase");
	    //Asignamos el valor de la contaseña a la variable passwd.
	    String passwd = req.getParameter("p");
  


	    //Comprobamos en que fase nos encontramos, si no se especifica será en la primera.
	    if(pphase == null) pphase = "01";
	    
	    //Asignamos el valor de auto a autoS.
	    autoEnCadena = req.getParameter("auto");
	    //Comprobamos si efectivamente, esta en modo auto o no. POR DEFECTO AUTO ES FALSE.
	    if(autoEnCadena == null) {
		auto = false;
	    }else {
		auto = false;
		if(autoEnCadena.equals("true")) auto = true;
	    }


	    //------------------- 
	    //En función a los parámetros recibidos se ejecuta según qué fase.
	    //Si la consulta se realiza en modo auto se ejecuta el if, si se realiza en modo browser, el else.
	    //-------------------
	    
	    
	    //Comprobamos si la solicitud lleva el parámetro 'p', es decir, si lleva contraseña.
	    if(passwd == null) {
        
		if(auto) {
		    
		    FrontEnd.missingPauto(auto, res);
		    
		} else {
		    
		    FrontEnd.missingPbrowser(auto, res);
		}
		
		return;
	    }

	    //Comprobamos que la contraseña recibida coincida con la real. 
	    if(!password.equals(passwd)) {
        
		if(auto) {
		    
		    FrontEnd.wrongPauto(auto, res);
		    
		} else {
		    
		    FrontEnd.wrongPbrowser(auto, res);
		}
		
		return;
	    }
	    
	    //Fases de la consulta.
	    switch(pphase) {

		//Pantalla principal de la consulta.
	    case "01":

		if(auto) {
		    
		    FrontEnd.doGetFase01auto(auto, res, passwd);
		    
		} else {
		    
		    FrontEnd.doGetFase01browser(auto, res, passwd);
		}
		
		break;
		
		//Pantalla con la lista de errores.
	    case "02":

		if(auto) {
		    
		    FrontEnd.doGetFase02auto(auto, res, passwd, DataModel.getErrores(), DataModel.getErroresFatales());

		} else {

		    FrontEnd.doGetFase02browser(auto, res, passwd, DataModel.getErrores(), DataModel.getErroresFatales());
		    
		}
		
		break;
		
		//Pantalla con la lista de países.
	    case "11":
		
		if(auto) {
		    
		    FrontEnd.doGetFase11auto(auto, res, passwd, DataModel.getQ1Countries());

		} else {

		    FrontEnd.doGetFase11browser(auto, res, passwd, DataModel.getQ1Countries());
		    
		}
		
		break;
		
		//Pantalla con la lista de álbumes. 
	    case "12":
		//Antes de llamar al método se comprueba si el parámetro obligatorios no se recibe. (En este caso, pcountry).

		if(pcountry == null) {

		    if(auto) {
			
			FrontEnd.noParamPcountryAuto(auto, res, pcountry, paid, pphase);
		    }
		    else {
			
			FrontEnd.noParamPcountryBrowser(auto, res, pcountry, paid, pphase);
		    }

		} else { 
		    
		    if(auto) {
		    
			FrontEnd.doGetFase12auto(auto, res, passwd, pcountry, DataModel.getQ1Albums(pcountry));

		    } else {

			FrontEnd.doGetFase12browser(auto, res, passwd, pcountry, DataModel.getQ1Albums(pcountry));
		    
		    }
		}
		
		break;
		
		//Pantalla con la lista de canciones. También se comprueba si falta alguno de los parámetros obligatorios. (Para este caso, pcountry y paid).
	    case "13":
		
		if(pcountry == null) {

		    if(auto) {

			FrontEnd.noParamPcountryAuto(auto, res, pcountry, paid, pphase);
			
		    } else {

			FrontEnd.noParamPcountryBrowser(auto, res, pcountry, paid, pphase);
		    } 
		} else if(paid == null){

		    if(auto) {

			FrontEnd.noParamPaidAuto(auto, res, pcountry, paid, pphase);
			
		    } else {

			FrontEnd.noParamPaidBrowser(auto, res, pcountry, paid, pphase);
			
		    }
		    
		} else {

		    if(auto) {
		    
			FrontEnd.doGetFase13auto(auto, res, passwd, pcountry, paid, DataModel.getQ1Songs(pcountry, paid));

		    } else {

			FrontEnd.doGetFase13browser(auto, res, passwd, pcountry, paid, DataModel.getQ1Songs(pcountry, paid));
		    
		    }
		    
		}

		break;
		
	    default:
		FrontEnd.doGetFase01browser(auto, res, passwd);

		break;	
	    }
        }
        catch (Exception ex) {
            System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
        }
    }    
}
