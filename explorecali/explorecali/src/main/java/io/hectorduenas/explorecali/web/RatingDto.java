package io.hectorduenas.explorecali.web;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.hectorduenas.explorecali.domain.TourRating;

public class RatingDto {
	@Min(0)
	@Max(5)
	private Integer score;
	
	@Size(max=255)
	private String comment;
	
	@NotNull
	private Integer customerId;
	
	public RatingDto(TourRating tourRating) {
		this(tourRating.getScore(), tourRating.getComment(), tourRating.getPk().getCustomerId());
	}

	public RatingDto(@Min(0) @Max(5) Integer score, @Size(max = 255) String comment, @NotNull Integer customerId) {
		super();
		this.score = score;
		this.comment = comment;
		this.customerId = customerId;
	}
	
	protected RatingDto() {};

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	
}
