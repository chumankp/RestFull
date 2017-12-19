package com.tp.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tp.dto.BookPackage;
import com.tp.dto.PackageInfo;

@Path(value = "terval")
public class Tervals {
	private Map<String, PackageInfo> packageMap;
	public Tervals() {
		packageMap=new ConcurrentHashMap<>();
	}

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public StreamingOutput newPackage(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {
		String orderId = UUID.randomUUID().toString();
		BookPackage bookPackage = null;
		PackageInfo packageInfo = null;
		DataOutPutStreming outPutStreming = null;
		bookPackage = buildBookPackage(inputStream);
		packageInfo = new PackageInfo();
		packageInfo.setOrderId(orderId);
		packageInfo.setPackageName(bookPackage.getPackageName());
		packageInfo.setAmmount(25300);
		packageInfo.setStatus("Active");
		packageMap.put(orderId, packageInfo);
		outPutStreming = new DataOutPutStreming(packageConveter(packageInfo));
		return outPutStreming;

	}

	public BookPackage buildBookPackage(InputStream inStream)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory builderFactory = null;
		DocumentBuilder builder = null;
		Document document = null;
		BookPackage bookPackage = null;
		builderFactory = DocumentBuilderFactory.newInstance();
		builder = builderFactory.newDocumentBuilder();
		document = builder.parse(inStream);
		bookPackage = new BookPackage();

		NodeList childNode = document.getFirstChild().getChildNodes();
		for (int i = 0; i < childNode.getLength(); i++) {
			Node child = childNode.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				String nodeValue = child.getFirstChild().getNodeValue();
				if (child.getNodeName().equals("package-name")) {
					bookPackage.setPackageName(nodeValue);
				} else if (child.getNodeName().equals("name")) {
					bookPackage.setName(nodeValue);
				} else if (child.getNodeName().equals("mobile")) {
					bookPackage.setMobile(nodeValue);
				} else if (child.getNodeName().equals("email")) {
					bookPackage.setEmail(nodeValue);
				}
			}
		}
		return bookPackage;
	}

	private String packageConveter(PackageInfo packageInfo) {
		StringBuilder builder = null;
		builder = new StringBuilder();
		if (packageInfo != null) {
			builder.append("<package-info>").append("<order-id>").append(packageInfo.getOrderId()).append("</order-id>")
					.append("<package-name>").append(packageInfo.getPackageName()).append("</package-name>")
					.append("<ammount>").append(packageInfo.getAmmount()).append("</ammount>").append("<status>")
					.append(packageInfo.getStatus()).append("</status>").append("</package-info>");
		}
		return builder.toString();
	}

	public class DataOutPutStreming implements StreamingOutput {
		private String data;

		public DataOutPutStreming(String data) {
			this.data = data;
		}

		@Override
		public void write(OutputStream outputStream) throws IOException, WebApplicationException {
			PrintWriter printWriter = new PrintWriter(outputStream);
			printWriter.println(data);
			printWriter.close();
			outputStream.close();
		}

	}
}
