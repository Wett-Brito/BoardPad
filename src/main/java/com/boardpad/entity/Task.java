package com.boardpad.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.boardpad.enuns.StatusTask;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_tasks")
public class Task implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String description;
	
	private String category;
	
	private StatusTask status;
	
	@ManyToOne()
	@JoinColumn(name = "board_id")
	private Board board;

	public Task() {
		
	}
	
	public Task(Long id, String title, String category, String description, StatusTask status, Board board) {
		super();
		this.id = id;
		this.title = title;
		this.category = category;
		this.description = description;
		this.status = status;
		this.board = board;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public StatusTask getStatus() {
		return status;
	}

	public void setStatus(StatusTask status) {
		this.status = status;
	}

	@JsonIgnore
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return Objects.equals(id, other.id);
	}

	
	
	
}
