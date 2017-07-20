package br.com.vitta.model;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Territory {
	private int id;
	private String name;
	private Position start;
	private Position end;
	private int area;
	private int painted_area;
	private ArrayList<Position> painted_squares;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Position getStart() {
		return start;
	}

	public void setStart(Position start) {
		this.start = start;
	}

	public Position getEnd() {
		return end;
	}

	public void setEnd(Position end) {
		this.end = end;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public int getPainted_area() {
		return painted_area;
	}

	public void setPainted_area(int painted_area) {
		this.painted_area = painted_area;
	}
	
	public ArrayList<Position> getPainted_squares() {
		return painted_squares;
	}

	public void setPainted_squares(ArrayList<Position> painted_squares) {
		this.painted_squares = painted_squares;
	}

	public static class Position {
		private int x;
		private int y;
		
		public Position() {
			this.x = 0;
			this.y = 0;
		}

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
	}
	
	public static class Square {
		private int x;
		private int y;
		boolean painted;

		public Square(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public boolean isPainted() {
			return painted;
		}

		public void setPainted(boolean painted) {
			this.painted = painted;
		}
	}
}
