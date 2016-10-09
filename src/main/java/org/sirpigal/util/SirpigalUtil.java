package org.sirpigal.util;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SirpigalUtil {

	public static Document getXMLFromUrl(String url) {
		Document doc = null;
		try {

			URLConnection connection = new URL(url).openConnection();
			doc = parseXML(connection.getInputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}

	public static Document parseXML(InputStream stream) throws Exception {
		DocumentBuilderFactory objDocumentBuilderFactory = null;
		DocumentBuilder objDocumentBuilder = null;
		Document doc = null;
		try {
			objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

			doc = objDocumentBuilder.parse(stream);
		} catch (Exception ex) {
			throw ex;
		}

		return doc;
	}

	public static String getSirpigalContactDetails() {
		StringBuilder details = new StringBuilder();
		details.append("```");
		details.append("SIRPIGAL FOUNDATION").append("\n");
		details.append("Reg.No:21/2012,").append("\n");
		details.append("New Bus Stand").append("\n");
		details.append("Ramanathapuram").append("\n");
		details.append("Tamil Nadu- 623501").append("\n");
		details.append("Email - ").append("sirpigal2012@gmail.com").append("\n");
		details.append("Official Website - ").append("http://sirpigal.org").append("\n");
		details.append("Google+ - ").append("http://profiles.google.com/107980947687514804882").append("\n");
		details.append("Facebook Page - ").append("http://www.facebook.com/Sirpigal-Foundation-370965289599599/")
				.append("\n");
		details.append("Twitter - ").append("http://twitter.com/sirpigal").append("\n");
		details.append("VCF - ").append("http://en.gravatar.com/sirpigal2012.vcf").append("\n");
		details.append("```");
		return details.toString();
	}

	public static String getTopNew(Document document) {
		String topNews = null;
		Element root = document.getDocumentElement();
		if (root.hasChildNodes()) {
			Node node = root.getFirstChild();
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				topNews = setLatestNewsFromNode((Element) node);
			}
		}
		return topNews;
	}

	public static String setLatestNewsFromNode(Element e) {
		StringBuilder s = new StringBuilder();
		s.append("```");
		s.append(e.getElementsByTagName("title").item(0).getTextContent()).append("\n\n");
		s.append(e.getElementsByTagName("content").item(0).getTextContent()).append("\n");
		s.append("<i>Date Updated ").append(e.getElementsByTagName("date").item(0).getTextContent()).append("</i>\n\n");

		s.append("For more news : http://www.sirpigal.org/#post")
				.append(e.getElementsByTagName("postno").item(0).getTextContent());
		s.append("```");
		return s.toString();
	}

}
