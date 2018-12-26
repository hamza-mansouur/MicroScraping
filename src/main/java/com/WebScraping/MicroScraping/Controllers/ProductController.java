package com.WebScraping.MicroScraping.Controllers;

import com.WebScraping.MicroScraping.Business.BalouchiScarping;
import com.WebScraping.MicroScraping.Business.ForsaScraping;
import com.WebScraping.MicroScraping.Models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @GetMapping(value = "/Produits/balouchi/{productName}")
    public List<Product>  afficherUnProduitBalouchi(@PathVariable String productName) {
           BalouchiScarping balouchiScarping = new BalouchiScarping();
        return balouchiScarping.searchProduct(productName);
    }
    @GetMapping(value = "/Produits/forsa/{productName}")
    public List<Product>  afficherUnProduitForsa(@PathVariable String productName) {
        ForsaScraping forsaScarping = new ForsaScraping();
        return forsaScarping.searchProduct(productName);
    }
}
