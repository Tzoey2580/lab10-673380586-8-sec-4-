
package com.example.lab10.controller;
 
import com.example.lab10.model.Product;
import com.example.lab10.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
 
@RestController
@RequestMapping("/products")
public class ProductController {
 
    @Autowired
    private ProductService service;
 
    // ✅ ทำแล้ว (ตัวอย่างจากเทมเพลต) — GET /products/{id}
    @GetMapping("/{id}")
    public Mono<Product> getById(@PathVariable String id) {
        return service.getById(id);
    }
 
    // 1) GET /products -> Flux<Product>
    @GetMapping
    public Flux<Product> getAll() {
        return service.getAll();
    }
 
    // 2) POST /products -> Mono<Product>
    // ทดสอบด้วย Postman: Body -> raw -> JSON
    // { "id": "P005", "name": "Test Product", "price": 1000, "category": "Electronics", "discount": 0 }
    @PostMapping
    public Mono<Product> create(@RequestBody Product product) {
        return service.create(product);
    }
 
    // 3) DELETE /products/{id} -> Mono<Void>
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return service.delete(id);
    }
 
    // 4) GET /products/category/{cat} -> Flux<Product>
    @GetMapping("/category/{cat}")
    public Flux<Product> getByCategory(@PathVariable String cat) {
        return service.getByCategory(cat);
    }
 
    // 5) GET /products/{id}/price -> Mono<Double>  (ราคาหลังหักส่วนลด)
    @GetMapping("/{id}/price")
    public Mono<Double> getPrice(@PathVariable String id) {
        return service.getDiscountedPrice(id);
    }
}
 
