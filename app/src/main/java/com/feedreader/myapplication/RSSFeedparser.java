package com.feedreader.myapplication;

import android.graphics.drawable.Drawable;
import android.media.Image;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RSSFeedparser  {

    public static ArrayList<RSSElement> getRSSfeedFromUrl(String url){
        ArrayList<RSSElement> RSSelements=new ArrayList<>();
        String xml=null;
        try {
            //Read String from website
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpPost = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity);
            //convert String to doc
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));
            //get imformation
            NodeList nodeList = doc.getElementsByTagName("channel");
            Element e = (Element) nodeList.item(0);
            NodeList items = e.getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); i++) {
                Element e1 = (Element) items.item(i);
                RSSElement rssElement = new RSSElement();
                NodeList titles=e1.getElementsByTagName("title");
                rssElement.title=titles.item(0).getFirstChild().getTextContent();
                NodeList links=e1.getElementsByTagName("link");
                rssElement.link=links.item(0).getFirstChild().getTextContent();
                NodeList pubdates=e1.getElementsByTagName("pubDate");
                rssElement.pubdate=pubdates.item(0).getFirstChild().getTextContent();
                NodeList categorys=e1.getElementsByTagName("category");
                if(categorys.getLength()!=0)
                rssElement.category=categorys.item(0).getFirstChild().getTextContent();
                RSSelements.add(rssElement);
            }

        }catch (Exception e) {
            System.out.println("Rss Feed Parser Catch exception = " + e);
            return null;
        }
        return RSSelements;
    }

}
