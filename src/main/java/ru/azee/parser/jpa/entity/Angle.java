package ru.azee.parser.jpa.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;


/**
 * The persistent class for the angle database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Angle.findAll", query="SELECT a FROM Angle a"),
	@NamedQuery(name="Angle.deleteById", query="DELETE FROM Angle a where a.id = :id")
})
public class Angle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private double hangle;

	private double vangle;

	//bi-directional many-to-one association to Anglepair
	@ManyToOne
	@JoinColumn(name="Id_AnglePair")
	private Anglepair anglepair;

	public Angle() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getHangle() {
		return this.hangle;
	}

	public void setHangle(double hangle) {
		this.hangle = hangle;
	}

	public double getVangle() {
		return this.vangle;
	}

	public void setVangle(double vangle) {
		this.vangle = vangle;
	}

	public Anglepair getAnglepair() {
		return this.anglepair;
	}

	public void setAnglepair(Anglepair anglepair) {
		this.anglepair = anglepair;
	}

	@Override
	public String toString() {
		return "Angle [id=" + id + ", hangle=" + hangle + ", vangle=" + vangle + ", anglepair.id=" + anglepair.getId() + "]";
	}
}