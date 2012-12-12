package dsa.tomalgo.service.handlers;

import java.io.IOException;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dsa.tomalgo.service.ServiceServlet;

public class HandlerFactory {
	private static HandlerFactory instance = null;
	private Hashtable<String, HandlerInfo> handlerList = null;

	private HandlerFactory() {
		handlerList = new Hashtable<String, HandlerInfo>();
		try {
			loadHandlers();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static HandlerFactory newInstance() {
		if (instance == null)
			instance = new HandlerFactory();
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public Handler createHandler(String action) throws HandlerException, InstantiationException, IllegalAccessException{
		HandlerInfo hInfo = handlerList.get(action);
		if(hInfo == null ) throw new HandlerException(401, "Action not found: '" +  action + "'");
		
		Class<Handler> handler;
		try {
			handler = (Class<Handler>) Class.forName(hInfo.getHandlerClass());
		} catch (ClassNotFoundException e) {
			throw new HandlerException(401, "Handler class not found: " + action);
		}
		return handler.newInstance();
	}

	private void loadHandlers() throws ParserConfigurationException,
			SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(ServiceServlet.class
				.getResourceAsStream("/handlers.xml"));
		
		doc.getDocumentElement().normalize();
		
		NodeList nList = doc.getDocumentElement().getChildNodes();
		
		Node node = nList.item(0);
		while((node = node.getNextSibling()) != null)
		if(node.getNodeType() == Node.ELEMENT_NODE)
		{
			Element nElement = (Element) node;
			String hAction = nElement.getElementsByTagName("action").item(0).getTextContent();
			HandlerInfo hInfo = new HandlerInfo(
					hAction,
					"dsa.tomalgo.service.handlers.actions." + nElement.getElementsByTagName("handler-class").item(0).getTextContent(),
					nElement.getAttribute("auth").equals("true")
			);
			
			handlerList.put(hAction, hInfo);
		}
	}
}
