package com.spring5.webflux.controller;

import com.spring5.webflux.domain.Category;
import com.spring5.webflux.domain.Vendor;
import com.spring5.webflux.repository.CategoryRepository;
import com.spring5.webflux.repository.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
public class VendorController {
    VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }
    @GetMapping("/api/v1/vendors")
     Flux<Vendor> getVendors(){
        return vendorRepository.findAll();
    }
    @GetMapping("/api/v1/vendor/{id}")
    Mono<Vendor> getVendor(@PathVariable String id){
        return vendorRepository.findById(id);
    }

    @PostMapping("/api/v1/vendors")
    Mono<Void> update(@RequestBody Publisher<Vendor> vendorStream){
        return vendorRepository.saveAll(vendorStream).then();
    }
    @PutMapping("/api/v1/vendor/{id}")
    Mono<Vendor> edit(@PathVariable String id,@RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("/api/v1/vendor/{id}")
    Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {
        Mono<Vendor> foundVendor = vendorRepository.findById(id);
        return foundVendor
                .filter(found -> !Objects.equals(found.getFirstName(), vendor.getFirstName()))
                .flatMap(f -> {
                    f.setFirstName(vendor.getFirstName());
                    return vendorRepository.save(f);
                }).switchIfEmpty(foundVendor);
    }

}
