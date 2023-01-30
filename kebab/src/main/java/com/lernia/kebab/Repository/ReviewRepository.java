package com.lernia.kebab.Repository;

import java.util.List;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.lernia.kebab.Document.Review;

public interface ReviewRepository extends MongoRepository<Review, Long>{

  
  // @Query(value="{}",  fields="{ location : 1 }")
  // List<Review> findAllWithOnlyLocation();

  @Query(value="{}",  fields="{ location : 1 }")
  List<Review> findAllWithOnlyLocation();

  @Query(value="{ location: ?0 } ")
  List<Review> findAllReviewsForLocation(GeoJsonPoint point);
  
}
