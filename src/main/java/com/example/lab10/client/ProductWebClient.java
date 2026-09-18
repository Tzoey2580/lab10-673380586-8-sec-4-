package com.example.lab10.client;

import com.example.lab10.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Component นี้ "เรียก" API ของ Controller ตัวเอง แบบ non-blocking
 * สร้าง WebClient เองตรงๆ ในคลาสนี้ (ไม่ต้องพึ่ง bean จาก AppConfig
 * เพราะ AppConfig ไม่ได้ประกาศ WebClient bean ไว้ให้)
 */
@Component
public class ProductWebClient {

    private final WebClient webClient = WebClient.create("http://localhost:8080");

    // ✅ ทำแล้ว (ตัวอย่างจากเทมเพลต)
    public Mono<Product> getProductById(String id) {
        return webClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(Product.class)
                .defaultIfEmpty(new Product());
    }

    // 1) getAllProducts -> เรียก GET /products -> Flux<Product>
    public Flux<Product> getAllProducts() {
        return webClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(Product.class);
    }

    // 2) createProduct -> เรียก POST /products -> Mono<Product>
    public Mono<Product> createProduct(Product product) {
        return webClient.post()
                .uri("/products")
                .bodyValue(product)
                .retrieve()
                .bodyToMono(Product.class);
    }

    // 3) deleteProduct -> เรียก DELETE /products/{id} -> Mono<Void>
    public Mono<Void> deleteProduct(String id) {
        return webClient.delete()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    // 4) getProductsByCategory -> เรียก GET /products/category/{cat} -> Flux<Product>
    public Flux<Product> getProductsByCategory(String category) {
        return webClient.get()
                .uri("/products/category/{cat}", category)
                .retrieve()
                .bodyToFlux(Product.class);
    }

    // 5) getProductPrice -> chain .map().defaultIfEmpty().subscribe() ต่อทันทีตามที่โจทย์ขอ
    public void getProductPrice(String id) {
        webClient.get()
                .uri("/products/{id}/price", id)
                .retrieve()
                .bodyToMono(Double.class)
                .map(price -> "ราคาสินค้า " + id + " หลังหักส่วนลด: " + price + " บาท")
                .defaultIfEmpty("ไม่พบสินค้ารหัส " + id)
                .subscribe(System.out::println);
    }
}