package com.txp.fs.component;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class CroJSoup {
	
	public Document getHtmlData(String url) throws IOException
	{
		Document doc =  Jsoup.connect(url).timeout(30*1000).get();
		return doc;
	}
}
