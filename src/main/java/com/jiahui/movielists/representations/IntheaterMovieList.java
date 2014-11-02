package com.jiahui.movielists.representations;

import java.util.List;

public class IntheaterMovieList {
	private Integer total;
	private List<Movie> movies;
	
	public IntheaterMovieList() {
		
	}
	public IntheaterMovieList (Integer total, List<Movie> movies) {
		this.total = total;
		this.movies = movies;
	}
	
	public Integer getTotal() {
		return total;
	}
	
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public List<Movie> getMovies() {
		return movies;
	}
	
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
	
	@Override
	public int hashCode() {
		int result = 23;
		result += total != null?total.hashCode():0;
		result += movies != null? movies.hashCode():0;
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if(o == null||o.getClass() != getClass()) return false;
		IntheaterMovieList in = (IntheaterMovieList)o;
		if((total != null? !total.equals(in.total):in.total != null)
			||(movies != null? !movies.equals(in.movies):in.movies != null))			
			return false;
		return true;
	}
}
