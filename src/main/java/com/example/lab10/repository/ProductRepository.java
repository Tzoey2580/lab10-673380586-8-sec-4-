package com.example.lab10.repository;

import com.example.lab10.model.Product;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {

    private final Map<String, Product> db = new ConcurrentHashMap<>();

    public ProductRepository() {
        // constructor จริงคือ: Product(id, name, category, brand, stock, price, discountType)
        save(new Product("P001", "Laptop Dell XPS13 - ธนนันต์ สาวิกัน 673380586-8", "Electronics", "Dell", 10, 35000.0, "MEMBER")).subscribe();
        save(new Product("P002", "Wireless Mouse", "Electronics", "Logitech", 50, 590.0, "NONE")).subscribe();
        save(new Product("P003", "Office Chair", "Furniture", "IKEA", 8, 4200.0, "SEASONAL")).subscribe();
        save(new Product("P004", "Mechanical Keyboard", "Electronics", "Keychron", 20, 2500.0, "NONE")).subscribe();
    }

    public Flux<Product> findAll() {
        return Flux.fromIterable(db.values());
    }

    public Mono<Product> findById(String id) {
        return Mono.justOrEmpty(db.get(id));
    }

    public Mono<Product> save(Product product) {
        db.put(product.getId(), product);
        return Mono.just(product);
    }

    public Mono<Void> deleteById(String id) {
        db.remove(id);
        return Mono.empty();
    }

    public Flux<Product> findByCategory(String category) {
        List<Product> result = db.values().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
        return Flux.fromIterable(result);
    }
}