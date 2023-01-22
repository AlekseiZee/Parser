package ru.azee.parser.jpa.entity;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;


/**
 * The persistent class for the instance database table.
 * 
 */
@Entity
@NamedQuery(name="Instance.findAll", query="SELECT i FROM Instance i")
public class Instance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String author;

	@Column(name="creation_date")
	private Timestamp creationDate;

	private String label;

	//bi-directional many-to-one association to Point
	@OneToMany(mappedBy="instance", cascade = CascadeType.PERSIST)
	private List<Point> points = new ArrayList<>();

	public Instance() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Point> getPoints() {
		return this.points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public Point addPoint(Point point) {
		getPoints().add(point);
		point.setInstance(this);

		return point;
	}

	public Point removePoint(Point point) {
		getPoints().remove(point);
		point.setInstance(null);

		return point;
	}

	@Override
	public String toString() {
		return "Instance [id=" + id + ", author=" + author + ", creationDate=" + creationDate + ", label=" + label
				+ ", points=" + points + "]";
	}
}