package com.daytodayhealth.rating.config;

import com.daytodayhealth.rating.entity.PlatformUser;
import com.daytodayhealth.rating.entity.ProductEntity;
import com.daytodayhealth.rating.entity.RatingEntity;
import com.daytodayhealth.rating.repository.ProductRepository;
import com.daytodayhealth.rating.repository.RatingRepository;
import com.daytodayhealth.rating.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.time.Instant;
import java.util.*;

@Configuration
@Slf4j
public class DatabaseConfig {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    UserRepository userRepository;

    //This is a one time setup to add data, not for prod
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("Initializing DB");
        List<String> productList = Arrays.asList("Lab Test Order", "Mindfulness", "CarePlan", "WellBeing", "COVID-19");
        List<ProductEntity> productEntities = new ArrayList<>();
        productList.forEach(product -> {
            productEntities.add(new ProductEntity(product, 0.0f, 0L));
            productRepository.saveAllAndFlush(productEntities);
        });

        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setRating(3);
        ratingEntity.setMessage("good");
        ratingEntity.setProductId(productEntities.get(0));
        ratingEntity.setPostedAt(Date.from(Instant.now()));
        ratingRepository.save(ratingEntity);

        ratingEntity = new RatingEntity();
        ratingEntity.setRating(2);
        ratingEntity.setMessage("not good, not bad");
        ratingEntity.setProductId(productEntities.get(0));
        ratingEntity.setPostedAt(Date.from(Instant.now()));
        ratingRepository.save(ratingEntity);

        ratingEntity = new RatingEntity();
        ratingEntity.setRating(2);
        ratingEntity.setMessage("ok, not bad");
        ratingEntity.setProductId(productEntities.get(0));
        ratingEntity.setPostedAt(Date.from(Instant.now()));
        ratingRepository.save(ratingEntity);

        PlatformUser user = new PlatformUser();
        user.setName("User1");
        user.setPassword("shouldITellu");
        user.setRole("USER");
        user.setUsername("normalUser");

        PlatformUser admin = new PlatformUser();
        admin.setName("Admin1");
        admin.setPassword("whyShouldITellu");
        admin.setRole("ADMIN");
        admin.setUsername("abc@gmail.com");

        userRepository.save(user);
        userRepository.save(admin);
    }
}
