package cl.ey.model;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Phones {

	@Id
    @GeneratedValue
    @UuidGenerator
    private UUID phoneId;

	private int number;
	private int cityCode;
	private int contryCode;

    public Phones() {
    	
    }

	public Phones(int number, int cityCode, int contryCode) {
		super();
		this.number = number;
		this.cityCode = cityCode;
		this.contryCode = contryCode;
	}

	public UUID getIdPhone() {
		return phoneId;
	}
	public void setIdPhone(UUID phoneId) {
		this.phoneId = phoneId;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getCityCode() {
		return cityCode;
	}
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	public int getCountriCode() {
		return contryCode;
	}
	public void setCountriCode(int contriCode) {
		this.contryCode = contriCode;
	}

	@Override
	public String toString() {
		return "Phones [phoneId=" + phoneId + ", number=" + number + ", cityCode=" + cityCode + ", countriCode="
				+ contryCode + "]";
	}


}
