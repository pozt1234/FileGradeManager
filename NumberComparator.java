import java.util.*;

public class NumberComparator implements Comparator<Student>{
	public int compare(Student student, Student anotherStudent){		
		Integer key = Integer.valueOf(student.getNumber());
		Integer anotherKey = Integer.valueOf(anotherStudent.getNumber());
		return key.compareTo(anotherKey);
	}

}