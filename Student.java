/*
# Student Ŭ���� #

- �л��� ���� (��ȣ, �̸�, ��������, ����, ���, ����)�� ���� �ϴ� Ŭ����
- �޼ҵ� : �л��� ������ ����, �ٸ� Ŭ������ ������ ��ȯ���ִ� ���
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

	// ��ȣ
	int getNumber(){
		return number;
	}
	void setNumber(int number){
		this.number = number;
	}

	// �̸�, ����, ����, ����
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

	// ����
	int getTotal(){
		return total;
	}

	// ���
	double getAverage(){
		return average;
	}

	// ����
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
