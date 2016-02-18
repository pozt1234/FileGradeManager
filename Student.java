/*
# Student 클래스 #

- 학생의 성적 (번호, 이름, 과목점수, 총점, 평균, 순위)을 저장 하는 클래스
- 메소드 : 학생의 성적을 변경, 다른 클래스로 성적을 반환해주는 기능
*/

class Student implements Cloneable, java.io.Serializable{
	private int number;

	private String name;
	private int kor;
	private int eng;
	private int math;

	private int total;
	private double average;

	private int rank;

	Student(){

	}

	Student(int number, String name, int kor, int eng, int math){
		this.number = number;
		this.name = name;
 		this.kor = kor;
 		this.eng = eng;
 		this.math = math;

 		rank = 1;
 		total = (kor+eng+math);
 		average = Math.round(((double)total/3)*100d)/100d;
	}

	// 번호
	int getNumber(){
		return number;
	}
	void setNumber(int number){
		this.number = number;
	}

	// 이름, 국어, 영어, 수학
	String getName(){
		return name;
	}
	int getKor(){
		return kor;
	}
	int getEng(){
		return eng;
	}
	int getMath(){
		return math;
	}

	// 총점
	int getTotal(){
		return total;
	}

	// 평균
	double getAverage(){
		return average;
	}

	// 순위
	int getRank(){
		return rank;
	}
	void setRank(int rank){
		this.rank = rank;
	}

	public String toString(){
		return " " + number + "\t" + name + "\t" + kor+ "\t" +eng + "\t" + math + "\t" + total + "\t" + average + "\t" + rank;
	}

}
