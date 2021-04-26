package io.hectorduenas.explorecali.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import io.hectorduenas.explorecali.domain.TourRating;
import io.hectorduenas.explorecali.domain.TourRatingPK;

@RepositoryRestResource(exported=false)
public interface TourRatingRepository extends CrudRepository<TourRating, TourRatingPK>{
	
	List<TourRating> findByPkTourId(Integer tourId);
	
	Optional<TourRating> findByPkTourIdAndPkCustomerId(Integer tourId, Integer customerId);
	
	Page<TourRating> findByPkTourId(Integer tourId, Pageable pageable);
}
