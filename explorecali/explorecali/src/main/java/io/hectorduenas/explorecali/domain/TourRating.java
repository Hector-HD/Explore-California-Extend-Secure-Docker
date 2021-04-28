package io.hectorduenas.explorecali.domain;

import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name="tour_rating")
public class TourRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Column(name="customer_id")
    private Integer customerId;
	
	@Column(nullable = false)
	private Integer score;
	
	@Column
	private String comment;
	
	protected TourRating() {}

	public TourRating(Tour tour, Integer customerId, Integer score) {
		super();
		this.tour = tour;
		this.customerId = customerId;
		this.score = score;
	}

	public TourRating(Tour tour, Integer customerId, Integer score, String comment) {
		super();
		this.tour = tour;
		this.customerId = customerId;
		this.score = score;
		this.comment = toComment(score);
	}

	private String toComment(Integer score) {
        switch (score) {
            case 1:return "Terrible";
            case 2:return "Poor";
            case 3:return "Fair";
            case 4:return "Good";
            case 5:return "Great";
            default: return score.toString();
        }
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

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

	@Override
	public String toString() {
		return "TourRating [id=" + id + ", tour=" + tour + ", customerId=" + customerId + ", score=" + score
				+ ", comment=" + comment + "]";
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourRating that = (TourRating) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(tour, that.tour) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(score, that.score) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tour, customerId, score, comment);
    }
}
