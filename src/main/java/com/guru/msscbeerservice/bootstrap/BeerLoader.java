package com.guru.msscbeerservice.bootstrap;

import com.guru.msscbeerservice.repositories.BeerRepository;
import com.guru.msscbeerservice.web.domain.Beer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if(beerRepository.count() == 0) {
            beerRepository.save(Beer.builder()
                    .beerName("Mango Bobs")
                    .beerStyle("IPA")
                    .minOnHand(12)
                    .qualityToBeer(200)
                    .upc(39837890988L)
                    .price(new BigDecimal("12.75"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("PALE_ALE")
                    .minOnHand(12)
                    .qualityToBeer(200)
                    .upc(39837890648L)
                    .price(new BigDecimal("15.75"))
                    .build());
        }

        System.out.println("Loaded Beers: " + beerRepository.count());
    }
}
