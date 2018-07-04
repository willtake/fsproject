package com.txp.fs.common;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Ignore;
import org.junit.Test;

public class JsoupTest {

	@Test
	@Ignore
	public void test() {
        String url = "http://www.38.co.kr/html/forum/com_list/?menu=nostock";
        Document document = null;
		try {
			document = Jsoup.connect(url).get();
//			System.out.println(document.html());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		String[] items = new String[] {"시장구분",	"종목코드", "종목명", "업종", "자본금", "수정(등록)일"};
		
//        Elements rows = document.select("table");
//        for(Element row : rows) {
//        	System.out.println(row.html());
//        	Iterator<Element> iterElem = row.getElementsByTag("td").iterator();
//	        StringBuilder builder = new StringBuilder();
//	        for (String item : items) {
//	          builder.append(item + ": " + iterElem.next().text() + "   \t");
//	        }
//	        System.out.println(builder.toString());
//        }
    	
	}

}
