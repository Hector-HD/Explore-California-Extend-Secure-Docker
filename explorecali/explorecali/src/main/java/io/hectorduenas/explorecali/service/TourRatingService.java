package io.hectorduenas.explorecali.service;

import io.hectorduenas.explorecali.domain.Tour;
import io.hectorduenas.explorecali.domain.TourRating;
import io.hectorduenas.explorecali.repo.TourRatingRepository;
import io.hectorduenas.explorecali.repo.TourRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;

import javax.transaction.Transactional;

@Service
@Transactional
public class TourRatingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TourRatingService.class);
    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;

   
    @Autowired
    public TourRatingService(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    public void createNew(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
    	LOGGER.info("Create new rating for tour {} of customer {}", tourId, customerId);
        tourRatingRepository.save(new TourRating(verifyTour(tourId), customerId,
                score, comment));
    }

    
    public Page<TourRating> lookupRatings(int tourId, Pageable pageable) throws NoSuchElementException  {
    	LOGGER.info("Lookup rating for tour {}" , tourId);
        return tourRatingRepository.findByTourId(verifyTour(tourId).getId(), pageable);
    }

   
    public TourRating update(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
    	LOGGER.info("Update rating for tour {} of customer {}", tourId , customerId);
        TourRating rating = verifyTourRating(tourId, customerId);
        rating.setScore(score);
        rating.setComment(comment);
        return tourRatingRepository.save(rating);
    }

   
    public TourRating updateSome(int tourId, Integer customerId, Integer score, String comment)
            throws NoSuchElementException {
        TourRating rating = verifyTourRating(tourId, customerId);
        if (score != null) {
            rating.setScore(score);
        }
        if (comment!= null) {
            rating.setComment(comment);
        }
        return tourRatingRepository.save(rating);
    }

    /**
     * Delete a Tour Rating.
     *
     * @param tourId tour identifier
     * @param customerId customer identifier
     * @throws NoSuchElementException if no Tour found.
     */
    public void delete(int tourId, Integer customerId) throws NoSuchElementException {
        TourRating rating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }
    /**
     * Get the average score of a tour.
     *
     * @param tourId tour identifier
     * @return average score as a Double.
     * @throws NoSuchElementException
     */
    public Double getAverageScore(int tourId)  throws NoSuchElementException  {
        List<TourRating> ratings = tourRatingRepository.findByTourId(verifyTour(tourId).getId());
        OptionalDouble average = ratings.stream().mapToInt((rating) -> rating.getScore()).average();
        return average.isPresent() ? average.getAsDouble():null;
    }
    /**
     * Service for many customers to give the same score for a service
     *
     * @param tourId
     * @param score
     * @param customers
     */
   
    public void rateMany(int tourId,  int score, Integer [] customers) {
    	LOGGER.info("Rate tour {} by customers {}", tourId, Arrays.asList(customers).toString());
        tourRepository.findById(tourId).ifPresent(tour -> {
            for (Integer c : customers) {
            	LOGGER.debug("Attempt to create Tour Rating for customer {}", c);
                tourRatingRepository.save(new TourRating(tour, c, score));
            }
        });
    }

    /**
     * Verify and return the Tour given a tourId.
     *
     * @param tourId
     * @return the found Tour
     * @throws NoSuchElementException if no Tour found.
     */
    private Tour verifyTour(int tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(() ->
                new NoSuchElementException("Tour does not exist " + tourId)
        );
    }


    /**
     * Verify and return the TourRating for a particular tourId and Customer
     * @param tourId
     * @param customerId
     * @return the found TourRating
     * @throws NoSuchElementException if no TourRating found
     */
    private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId).orElseThrow(() ->
                new NoSuchElementException("Tour-Rating pair for request("
                        + tourId + " for customer" + customerId));
    }


}
