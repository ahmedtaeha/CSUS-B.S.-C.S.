package com.csus.csc133;

public class LectureHall extends Building {
	private String name;
	private Lecture lecture;
	
	public LectureHall()
	{
		super();
		name = "Lecture Hall";
		lecture = new Lecture();
	}
	
	
	public LectureHall(String name)
	{
		super();
		this.name = name;
		lecture = new Lecture();
	}

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void move(int elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleCollide(Student s) {
		// TODO Auto-generated method stub
		
		if (s instanceof StudentPlayer)
		{
			// ends lecture immediately
			lecture.setTime(0);
		}
	}
	
	public String toString()
	{
		if(lecture == null)
		{
			return name+", "+super.toString()+" Current Lecture : null";			
		}
		return name+", "+super.toString()+" Current Lecture : Lecture now, " + lecture.toString();
	}
}
