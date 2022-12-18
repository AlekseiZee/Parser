package ru.azee.parser.jpa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;


/**
 * The persistent class for the anglepair database table.
 * 
 */
@Entity
@NamedQuery(name="Anglepair.findAll", query="SELECT a FROM Anglepair a")
public class Anglepair implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	//bi-directional many-to-one association to Angle
	@OneToMany(mappedBy="anglepair")
	private List<Angle> angles = new ArrayList<>();

	//bi-directional many-to-one association to Point
	@ManyToOne
	@JoinColumn(name="Id_Point")
	private Point point;

	public Anglepair() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Angle> getAngles() {
		return this.angles;
	}

	public void setAngles(List<Angle> angles) {
		this.angles = angles;
	}

	public Angle addAngle(Angle angle) {
		getAngles().add(angle);
		angle.setAnglepair(this);

		return angle;
	}

	public Angle removeAngle(Angle angle) {
		getAngles().remove(angle);
		angle.setAnglepair(null);

		return angle;
	}

	public Point getPoint() {
		return this.point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	@Override
	public String toString() {
		return "Anglepair [id=" + id + ", angles=" + angles + ", point.id=" + point.getId() + "]";
	}
}