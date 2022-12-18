package ru.azee.parser.jpa.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;


/**
 * The persistent class for the object database table.
 * 
 */
@Entity
@NamedQuery(name="Object.findAll", query="SELECT o FROM Object o")
public class Object implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	//bi-directional many-to-one association to Point
	@OneToMany(mappedBy="object")
	private List<Point> points;

	public Object() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Point> getPoints() {
		return this.points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public Point addPoint(Point point) {
		getPoints().add(point);
		point.setObject(this);

		return point;
	}

	public Point removePoint(Point point) {
		getPoints().remove(point);
		point.setObject(null);

		return point;
	}

	@Override
	public String toString() {
		return "Object [id=" + id + ", points=" + points + "]";
	}
}