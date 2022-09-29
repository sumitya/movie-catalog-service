package com.example.moviecatalogservice.resources;

import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.Movie;
import com.example.moviecatalogservice.models.Rating;
import com.example.moviecatalogservice.models.UserRating;
import com.example.moviecatalogservice.services.MovieInfo;
import com.example.moviecatalogservice.services.UserRatingInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private MovieInfo movieInfo;

    @Autowired
    private UserRatingInfo userRatingInfo;


    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        /*List<Rating> ratingsList = Arrays.asList(
                new Rating("123",4),
                new Rating("1235",3)
        );*/

        UserRating userRating = userRatingInfo.getUserRating(userId);

        return userRating.getRatings().stream()

                .map(rating -> {

                    return movieInfo.getCatalogItem(rating);

                })
                .collect(Collectors.toList());

        /*
            Alternative WebClient way
            Movie movie = webClientBuilder.build()
                          .get()
                          .uri("http://localhost:8082/movies/"+ rating.getMovieId())
                          .retrieve()
                          .bodyToMono(Movie.class)
                          .block();
*/

    }




}
