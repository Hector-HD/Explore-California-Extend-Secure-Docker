package io.hectorduenas.explorecali.web;

import java.util.AbstractMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.hectorduenas.explorecali.domain.TourRating;
import io.hectorduenas.explorecali.service.TourRatingService;

@RestController
@RequestMapping(path="/tours/{tourId}/ratings")
public class TourRatingController {
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(TourRatingController.class);
	private TourRatingService tourRatingService;

    @Autowired
    public TourRatingController(TourRatingService tourRatingService) {
        this.tourRatingService = tourRatingService;
    }

    protected TourRatingController() {

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
    	LOGGER.info("POST /tours/{}/ratings", tourId);
        tourRatingService.createNew(tourId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment());
    }

    @PostMapping("/{score}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createManyTourRatings(@PathVariable(value = "tourId") int tourId,
                                      @PathVariable(value = "score") int score,
                                      @RequestParam("customers") Integer customers[]) {
    	LOGGER.info("POST /tours/{}/ratings/{}",tourId,score);
        tourRatingService.rateMany(tourId, score, customers);
    }

    @GetMapping
    public Page<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId, Pageable pageable) {
    	LOGGER.info("GET /tours/{}/ratings", tourId);
        Page<TourRating> tourRatingPage = tourRatingService.lookupRatings(tourId, pageable);
        List<RatingDto> ratingDtoList = tourRatingPage.getContent()
                .stream().map(tourRating -> toDto(tourRating)).collect(Collectors.toList());
        return new PageImpl<RatingDto>(ratingDtoList, pageable, tourRatingPage.getTotalPages());
    }

    @GetMapping("/average")
    public AbstractMap.SimpleEntry<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {
    	LOGGER.info("GET /tours/{}/ratings/average");
        return new AbstractMap.SimpleEntry<String, Double>("average", tourRatingService.getAverageScore(tourId));
    }

    @PutMapping
    public RatingDto updateWithPut(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
    	LOGGER.info("PUT /tours/{}/ratings",tourId);
         return toDto(tourRatingService.update(tourId, ratingDto.getCustomerId(),
                 ratingDto.getScore(), ratingDto.getComment()));
    }

    @PatchMapping
    public RatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
    	LOGGER.info("PATCH /tours/{}/ratings",tourId,ratingDto.getCustomerId());
         return toDto(tourRatingService.updateSome(tourId, ratingDto.getCustomerId(),
                 ratingDto.getScore(), ratingDto.getComment()));
    }

    @DeleteMapping("/{customerId}")
    public void delete(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "customerId") int customerId) {
    	LOGGER.info("DELETE /tours/{}/ratings/{}", tourId,customerId);
        tourRatingService.delete(tourId, customerId);
    }
    
    private RatingDto toDto(TourRating tourRating) {
        return new RatingDto(tourRating.getScore(), tourRating.getComment(), tourRating.getCustomerId());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
    	LOGGER.info("Unable to complete transaction", ex);
        return ex.getMessage();

    }
}
