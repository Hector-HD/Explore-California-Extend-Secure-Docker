package io.hectorduenas.explorecali.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Table(name="tour_package")
@Entity
public class TourPackage implements Serializable{
	
	@Id
	private String code;
	
	@Column
	private String name;
	
	protected TourPackage() {}

	public TourPackage(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "TourPackage [code=" + code + ", name=" + name + "]";
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourPackage that = (TourPackage) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }
}
