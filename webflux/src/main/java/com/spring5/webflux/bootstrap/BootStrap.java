package com.spring5.webflux.bootstrap;

import com.spring5.webflux.domain.Category;
import com.spring5.webflux.domain.Vendor;
import com.spring5.webflux.repository.CategoryRepository;
import com.spring5.webflux.repository.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BootStrap implements CommandLineRunner {
    CategoryRepository categoryRepository;
    VendorRepository vendorRepository;

    public BootStrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        categoryRepository.deleteAll().block();
        vendorRepository.deleteAll().block();
        if(categoryRepository.count().block()==0){

            log.info("Loading Category Data on Startup as there is no existing data");

            categoryRepository.save(Category.builder()
                    .description("Fruits").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Nuts").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Breads").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Meats").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Eggs").build()).block();

           log.info("Loaded Categories Total = "+categoryRepository.count().block());

            log.info("Loading Vendor Data on Startup as there is no existing data");

            vendorRepository.save(Vendor.builder().firstName("Joe").lastName("Buck").build()).block();
            vendorRepository.save(Vendor.builder()
                    .firstName("Micheal")
                    .lastName("Weston").build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Jessie")
                    .lastName("Waters").build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Bill")
                    .lastName("Nershi").build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Jimmy")
                    .lastName("Buffett").build()).block();

            log.info("Loaded Vendors: " + vendorRepository.count().block());

        }
    }
}
