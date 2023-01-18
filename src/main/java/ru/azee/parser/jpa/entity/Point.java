package ru.azee.parser.jpa.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;


/**
 * The persistent class for the point database table.
 * 
 */
@Entity
@NamedQuery(name="Point.findAll", query="SELECT p FROM Point p")
public class Point implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String code;

	private double distance;

	private double hangle;

	private double vangle;

	//bi-directional many-to-one association to Anglepair
	@OneToMany(mappedBy="point", cascade = CascadeType.PERSIST)
	private List<Anglepair> anglepairs = new ArrayList<>();

	//bi-directional many-to-one association to Instance
	@ManyToOne
	@JoinColumn(name="Id_instance")
	private Instance instance;

	public Point() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getDistance() {
		return this.distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
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

	public List<Anglepair> getAnglepairs() {
		return this.anglepairs;
	}

	public void setAnglepairs(List<Anglepair> anglepairs) {
		this.anglepairs = anglepairs;
	}

	public Anglepair addAnglepair(Anglepair anglepair) {
		getAnglepairs().add(anglepair);
		anglepair.setPoint(this);

		return anglepair;
	}

	public Anglepair removeAnglepair(Anglepair anglepair) {
		getAnglepairs().remove(anglepair);
		anglepair.setPoint(null);

		return anglepair;
	}

	public Instance getInstance() {
		return this.instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

}