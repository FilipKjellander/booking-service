package com.example.bookingservice.client;

import com.example.bookingservice.dto.ReviewDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;

@Service
public class ReviewClient {

    private final RestClient restClient;

    public ReviewClient(RestClient reviewRestClient) {
        this.restClient = reviewRestClient;
    }

    public void createReview(final ReviewDto reviewDto) {
        restClient.post()
                .uri("/api/reviews")
                .body(reviewDto)
                .retrieve()
                .toBodilessEntity();

    }

    public List<ReviewDto> getAllReviews() {
        return restClient.get()
                .uri("/api/reviews")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ReviewDto>>() {
                });
    }
}