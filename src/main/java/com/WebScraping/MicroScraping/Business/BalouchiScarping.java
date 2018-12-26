package com.WebScraping.MicroScraping.Business;

import com.WebScraping.MicroScraping.Models.Product;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BalouchiScarping {

     final WebClient browser;
      List<Product>searchedProduct=new ArrayList<>();


      public BalouchiScarping(){
          browser = new WebClient(BrowserVersion.CHROME);
          browser.getOptions().setJavaScriptEnabled(false);

          browser.getOptions().setJavaScriptEnabled(false);
          browser.getOptions().setThrowExceptionOnScriptError(false);
          browser.getOptions().setCssEnabled(false);
          browser.getOptions().setRedirectEnabled(false);
         // browser.setAjaxController(new NicelyResynchronizingAjaxController());
//          //final IncorrectnessListener il = new MyIncorrectnessListener(); // it just quiets errors down
          //browser.setIncorrectnessListener(il);
          final SilentCssErrorHandler eh = new SilentCssErrorHandler();
          browser.setCssErrorHandler(eh);
          browser.getOptions().setUseInsecureSSL(true);
          browser.getOptions().setCssEnabled(false);
          browser.getOptions().setPopupBlockerEnabled(false);
          browser.getOptions().setRedirectEnabled(true);
          browser.getOptions().setJavaScriptEnabled(true);
          CookieManager cm = new CookieManager();
          browser.setCookieManager(cm);
          browser.setJavaScriptTimeout(3600);
          browser.getOptions().setTimeout(9000);


      }


    public  List<Product> searchProduct(String productName) {

        HtmlPage currentPage;
        //String category = "pour-la-maison-et-jardin";



        try {

            currentPage = (HtmlPage) browser.getPage("https://www.ballouchi.com/");///c/"+category+"/"+textToSearch);


        } catch (Exception e) {

            System.out.println("Could not open browser window");

            e.printStackTrace();

            return null;

        }

        System.out.println("Simulated browser opened.");



        try {

            HtmlTextInput input = (HtmlTextInput) currentPage.getElementById("rech");
            System.out.println("input: " + input);

            input.setValueAttribute(productName);

            final HtmlButton button = (HtmlButton) currentPage.getElementByName("Connexion");
            System.out.println("button: " + button);
            System.out.println("page1: " + currentPage.getUrl());//.asText()); //asText());

            currentPage = button.click();

            DomElement list = currentPage.getElementById("product_list") ;

            Iterator<DomElement> iter = list.getChildElements().iterator();//currentPage.getByXPath("//li[@class='ajax_block_product bordercolor  preload_img']");
            while (iter.hasNext()) {
                final HtmlListItem testNode=(HtmlListItem)iter.next();
                String name="";
                String description="";
                String prix="";
                String image="";
                String url="";

                if(!testNode.getElementsByTagName("img").isEmpty())
                    image=testNode.getElementsByTagName("img").get(0).getAttribute("src").toString();
                if(!testNode.getElementsByTagName("h3").isEmpty()) {
                    name= testNode.getElementsByTagName("h3").get(0).getElementsByTagName("a").get(0).getElementsByTagName("span").get(0).getTextContent();
                    url=testNode.getElementsByTagName("h3").get(0).getElementsByTagName("a").get(0).getAttribute("href").toString();
                    description=testNode.getElementsByTagName("div").get(0).getElementsByTagName("p").get(0).getElementsByTagName("a")
                            .get(0).getElementsByTagName("span").get(0).getTextContent(); //.getElementsByTagName("a").get(0).getElementsByTagName("span").get(0).getTextContent());
                  //  if(!testNode.getElementsByTagName("div").get(1).getElementsByTagName("dl").get(0).getElementsByTagName("span").get(0).isValid())
                  //      prix=testNode.getElementsByTagName("div").get(1).getElementsByTagName("dl").get(0).getElementsByTagName("span").get(0).getTextContent();
                    //.getElementsByTagName("dd").get(0).getElementsByTagName("span").get(0).getTextContent());
                }
                System.out.println("hohoho"+testNode.getByXPath("//a[@class='tooltip_link']").get(3));
                Product p = new Product(name, description, prix, image, url);
                if(p.getName()!="")
                     this.searchedProduct.add(p);

            }


            System.out.println("arrays Items: " + list.getChildElements().toString());

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
