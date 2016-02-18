import java.util.*;

public class AverageComparator implements Comparator<Student>{
	public int compare(Student student, Student anotherStudent){
		return (student.getAverage() < anotherStudent.getAverage())? 1 : (student.getAverage() == anotherStudent.getAverage()? 0 : -1 );
	}

}