package com.lernia.kebab.Controller;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lernia.kebab.Document.Review;
import com.lernia.kebab.Repository.ReviewRepository;

@RestController
@RequestMapping("/api")
public class ReviewController {

  private final ReviewRepository reviewRepository;

  public ReviewController(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  @GetMapping("/reviews")
  public List<Review> getAllReviews() {
    return reviewRepository.findAll();
  }

  @GetMapping("/reviews/{id}")
  public Optional<Review> getOneReviewWithId(@PathVariable("id") Long id)  {
    return reviewRepository.findById(id);
  }

  @GetMapping("/locations")
  public Set<GeoJsonPoint> getAllReviewsLocations() {
     var res = reviewRepository.findAll();
     final Set<GeoJsonPoint> payload = new HashSet<>();
     res.forEach(review -> {
      payload.add(review.getLocation());
     });
     return payload;
  }

  @GetMapping("/locations/reviews")
  public List<Review> getAllReviewsForLocation(@RequestParam("lat") Double latitud, @RequestParam("lng") Double longitud){
    return reviewRepository.findAllReviewsForLocation(new GeoJsonPoint(latitud,longitud));

  }
  // Denna funkar, ska testa med query params också
  // @GetMapping("/locations/reviews")
  // public List<Review> getAllReviewsForLocation(@RequestBody GeoJsonPoint point)  {
  //   return reviewRepository.findAllReviewsForLocation(point);
  // }
  @PostMapping("/reviews")
  public ResponseEntity<Review> addNewReview(@RequestBody Review review) {
    Review newReview = reviewRepository.save(review);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newReview.getId()).toUri();
    return ResponseEntity.created(location).body(newReview);
  }
  
  
}
