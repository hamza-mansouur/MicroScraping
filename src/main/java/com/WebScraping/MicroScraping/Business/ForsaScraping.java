package com.WebScraping.MicroScraping.Business;

import com.WebScraping.MicroScraping.Models.Product;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForsaScraping {

        List<Product> searchedProduct=new ArrayList<>();





        public  List<Product> searchProduct(String productName) {

            HtmlPage currentPage;
            //String category = "pour-la-maison-et-jardin";



//            try {
//
//
//
//            } catch (Exception e) {
//
//                System.out.println("Could not open browser window");
//
//                e.printStackTrace();
//
//                return null;
//
//            }
//
//            System.out.println("Simulated browser opened.");



            try {

                //	System.out.println("this is my page"+doc.getElementsByClass("search-result-col").html());
                Document doc = Jsoup.connect("http://www.ahaya.tn/tunisie-annonces/Toute-la-Tunisie/?q="+productName).get();

                List<Element> produits=new ArrayList() ;  ;
                Elements a = doc.getElementsByTag("a");
                System.out.println(a.size());
                for(Element k : a) {
                    if(!k.getElementsByClass("search-result").isEmpty()) {
                        produits.add(k);
                    }

                }

                int i =0 ;
                for(Element e : produits) {
                    String name="";
                    String description="";
                    String prix="";
                    String image="";
                    String url="";
                    String date="";
                    //System.out.println("produit num :" + i);
                    name = e.getElementsByTag("h3").text();
                    //System.out.println("name "+ e.getElementsByTag("h3").text());
                    prix = e.getElementsByClass("price search-result-col").first().text();
                    if(e.getElementsByTag("img").size()>0) {
                        image=e.getElementsByTag("img").get(0).attr("abs:data-original");
                       // System.out.println("image "+e.getElementsByTag("img").get(0).attr("abs:data-original"));
                    }
                    date = e.getElementsByClass("search-result-col").get(3).text();
                    url = e.getElementsByTag("a").attr("abs:href");
                    //System.out.println("price " + e.getElementsByClass("price search-result-col").first().text());
//

                   // System.out.println("url "+e.getElementsByTag("a").attr("abs:href"));
                    Product p = new Product(name, description, prix, image, url);
                    searchedProduct.add(p);

                    //}

                }

//                System.out.println("hohoho"+testNode.getByXPath("//a[@class='tooltip_link']").get(3));
//                    Product p = new Product(name, description, prix, image, url);
//                    if(p.getName()!="")
//                        this.searchedProduct.add(p);
//
//                }


              //  System.out.println("arrays Items: " + list.getChildElements().toString());

                return this.searchedProduct;

            } catch (Exception e) {

                System.out.println("Could not search");

                e.printStackTrace();

                return null;

            }

        }



        public static boolean containsPattern(String string, String regex) {

            Pattern pattern = Pattern.compile(regex);



            // Check for the existence of the pattern

            Matcher matcher = pattern.matcher(string);

            return matcher.find();

        }



}
