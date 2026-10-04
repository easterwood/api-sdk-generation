package com.example.catalog.server;

import java.util.Map;

import com.example.catalog.api.DefaultApi;
import com.example.catalog.api.model.CatalogItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalog/items")
public class CatalogController implements DefaultApi {
    @GetMapping("/{id}")
    public ResponseEntity<CatalogItem> getCatalogItem(@PathVariable String id) {
        CatalogItem catalogItem = new CatalogItem();
        return "demo".equals(id)
                ? ResponseEntity.ok(catalogItem)
                : ResponseEntity.notFound().build();
    }
}
