package io.hectorduenas.explorecali.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import io.hectorduenas.explorecali.domain.TourRating;

@RepositoryRestResource(exported=false)
public interface TourRatingRepository extends CrudRepository<TourRating, Integer>{
	
	List<TourRating> findByTourId(Integer tourId);
	
	Optional<TourRating> findByTourIdAndCustomerId(Integer tourId, Integer customerId);
	
	Page<TourRating> findByTourId(Integer tourId, Pageable pageable);
}
