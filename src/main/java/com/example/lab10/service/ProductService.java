package com.example.lab10.service;

import com.example.lab10.model.Product;
import com.example.lab10.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service layer: จุดที่ใส่ "logic ทางธุรกิจ" (business logic)
 * Controller ไม่ควรเรียก Repository ตรงๆ — ให้ผ่าน Service เสมอ
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    // 1) getById — ส่งต่อ Mono จาก repository ตรงๆ
    public Mono<Product> getById(String id) {
        return repository.findById(id);
    }

    // 2) getAll — ส่งต่อ Flux จาก repository ตรงๆ
    public Flux<Product> getAll() {
        return repository.findAll();
    }

    // 3) create — บันทึกสินค้าใหม่
    public Mono<Product> create(Product product) {
        return repository.save(product);
    }

    // 4) delete — ลบสินค้า
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }

    // 5) getByCategory — ค้นหาตาม category
    public Flux<Product> getByCategory(String category) {
        return repository.findByCategory(category);
    }

    // 6) getDiscountedPrice — ตัวอย่างการใช้ operator "map" (sync transform)
    //    รับ Product มาแล้วแปลง (map) ให้เหลือแค่ราคาหลังหักส่วนลด
    //    ถ้าไม่เจอสินค้า (Mono ว่าง) ให้ fallback เป็น 0.0 ด้วย defaultIfEmpty
    public Mono<Double> getDiscountedPrice(String id) {
        return repository.findById(id)
        .map(Product::getDiscountedPrice)
        .defaultIfEmpty(0.0);
    }
}