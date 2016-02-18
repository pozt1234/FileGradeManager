import java.util.*;
import java.io.*;

class Management{
	private HashMap<Integer, Student> studentsMap;
	private ArrayList<Student> studentsList;
	private FileIO fio = new FileIO();
	private String openFileName;
	private boolean hasFile;
	private boolean isChange;

	public Scanner scan = new Scanner(System.in);

	public Management(){ 
		hasFile = false;
	}

	public void showList(){
		System.out.println("\n\n\t  ������������������������������������");
		System.out.println("\t  ��    �л� ���� ���� ���α׷�     ��");
		System.out.println("\t  ������������������������������������");
		System.out.println("\t  ��\t1. �л� ���� ���\t    ��");
		System.out.println("\t  ��\t2. ������ �л� ���� ��ȸ    ��");
		System.out.println("\t  ��\t3. ��ȣ�� �л� ���� ��ȸ    ��");	
		System.out.println("\t  ��\t4. �л� �� ��ȸ\t    ��");
		System.out.println("\t  ��\t5. �л� ���� ����\t    ��");
		System.out.println("\t  ��\t6. ���� ���� ����\t    ��");
		System.out.println("\t  ��\t7. ���� ���� ��ü\t    ��");
		System.out.println("\t  ��\t8. ����\t\t\t    ��");
		System.out.println("\t  ������������������������������������");
		if(hasFile)
			System.out.println("\t  ��\t �Էµ� ���� : " +  openFileName + " \t    ��");
		else
			System.out.println("\t  ��\t �Էµ� ������ �����ϴ�.    ��");
		System.out.println("\t  ������������������������������������");
		System.out.println();
	}

	public int selectList(){
		int input=0;
		try{
			System.out.print("\t\t- ���� : ");
			input = scan.nextInt();
			scan.nextLine();
		}catch(InputMismatchException ime){
			input = selectList();
		}
		return input;
	}


	// �л� ���� �Է�
	public void inputMenu() throws WrongExtensionException{
		if(hasFile && !isChange){
			inputStudentInfoByKeyboard();
		} else{
			showFileList();
			String[] extension = fio.confirmExtension();
			if(extension[1].equals("txt")){
				studentsMap = fio.inputStudentInfoByTxt();
			} else if(extension[1].equals("ser")){
				studentsMap = fio.inputStudentInfoBySer();
			} else{
				throw new WrongExtensionException();
			}
			studentsList = new ArrayList<Student>(studentsMap.values());
			openFileName = fio.getOpenFileName();
			hasFile = fio.getHasFile();
			isChange = false;
		}

	}

	public void inputStudentInfoByKeyboard() throws InputMismatchException{
		int userInputSize;

		System.out.print("\n\t\t- �Է��� �л��� �� : ");
		userInputSize = scan.nextInt(); 
		for(int i=0; i<userInputSize; i++){
			scan.nextLine();
			addStudent();
		}
	}

	public void addStudent() throws S{
		try{
			System.out.print("\t\t- ��ȣ : ");
			int number = scan.nextInt();
			existNumber(number);

			scan.nextLine();
			System.out.print("\t\t- �̸� : ");
			String name = scan.nextLine();
			System.out.print("\t\t- ���� : ");
			int kor = scan.nextInt();
			System.out.print("\t\t- ���� : ");
			int eng = scan.nextInt();
			System.out.print("\t\t- ���� : ");
			int math = scan.nextInt();


			
			studentsMap.put(number, new Student(number, name, kor, eng, math));
			studentsList.add(studentsMap.get(number));
		}catch(InputMismatchException ime){
			printInputMismatchException();
			addStudent();	
		}
	}

	public int existNumber(int number){
		while(studentsMap.containsKey(number)){
			System.out.println("\t\t�̹� �����ϴ� ��ȣ�Դϴ�.");
			System.out.print("\t\t�����ðڽ��ϱ�? [1:Yes / 2:No]");
			int input = scan.nextInt();
			if(input == 1){
				break;
			}else if(input == 2){
				System.out.print("\t\t- �ٸ� ��ȣ : ");
				number = scan.nextInt();
			} else{
				System.out.println("\n\t\t�߸��� �����Դϴ�!\n");
			}
		}
		return number;
	}

	public void showFileList(){
		File file = new File("./InputInfo");
		File[] files = file.listFiles();
		System.out.println("\n\t\t<InputInfo�� ���� ���>");
		for(File tempFile : files){
			System.out.println("\t\t   - "+tempFile.getName() + "\t"+ String.format("%1$tY-%1$tm-%1$te",  new Date(tempFile.lastModified())));
		}
		System.out.println();
	}

	public void showGradeByRank(){
		if(studentsMap == null)
		throw	new NullPointerException();
		
		countRank();

		System.out.println("\n\n\n\t\t   <������ �л� ���� ��ȸ>");
		printStudentInfo();
	}

	public void showGradeByNum(){
		if(studentsMap == null)
			throw new  NullPointerException();

		countRank();
		Collections.sort(studentsList, new NumberComparator());

		System.out.println("\n\n\n\t\t   <��ȣ�� �л� ���� ��ȸ>");
		printStudentInfo();
	}

	private void countRank(){
		Collections.sort(studentsList, new AverageComparator());

		Iterator it = studentsList.iterator();
		int numOfSameScore = 0;
		int prevRank = 0;
		double prevAverage = 0;
		while(it.hasNext()){
			Student student = (Student)it.next();
			if(student.getAverage() < prevAverage){
				student.setRank(prevRank + 1 + numOfSameScore);
				numOfSameScore = 0;
			} else if(student.getAverage() == prevAverage){
				student.setRank(prevRank);
				numOfSameScore++;
			}
			prevRank = student.getRank();
			prevAverage = student.getAverage();
		}
	}

	private void printStudentInfo(){
		System.out.println("��ȣ\t�̸�\t����\t����\t����\t����\t���\t����");
		for(Student student : studentsList){
			System.out.println(student);
		}
	}

	public void showGradeInDetail(){
		if(studentsMap == null)
			throw new NullPointerException();
		System.out.println("\t\t\t- 1 : �̸� ��ȸ");
		System.out.println("\t\t\t- 2 : ��ȣ ��ȸ");
		try{
			System.out.print("\t\t\t\t- ���� : ");
			int input = scan.nextInt();

			if(input == 1){
				searchToName();
			} else if(input == 2){
				searchToNumber();
			} else{
				System.out.println("\n\t\t\t\t�߸� �����߽��ϴ�!");
			}
		}catch(InputMismatchException ime){
			printInputMismatchException();
			showGradeInDetail();
		}
	}

	private void searchToName(){		
		scan.nextLine();
		System.out.print("\t\t\t\t- ��ȸ�� �̸� : ");
		String name = scan.nextLine();

		Set<Integer> keys = studentsMap.keySet();
		boolean hasName = false;		
		for(Integer key : keys){
			Student student = studentsMap.get(key);
			if(student.getName().equals(name) && !hasName){
				System.out.println("��ȣ\t�̸�\t����\t����\t����\t����\t���\t����");
				System.out.println(student);
				hasName = true;
			} else if(student.getName().equals(name)) {
				System.out.println(student);
			}
		}
		if(!hasName){
			System.out.println("\t\t\t\t��ġ�ϴ� �̸��� �����ϴ�!");
		}
	}

	private void searchToNumber() throws InputMismatchException{
		System.out.print("\t\t\t\t- ��ȸ�� ��ȣ : ");
		int number = scan.nextInt();

		if(studentsMap.containsKey(number)){
			Student student = studentsMap.get(number);
			System.out.println("��ȣ\t�̸�\t����\t����\t����\t����\t���\t����");
			System.out.println(student);
		}else{		
			System.out.println("\t\t\t\t��ġ�ϴ� ��ȣ�� �����ϴ�!");
		}
	}

	public void showDeleteMenu() throws InputMismatchException{
		System.out.println("\t\t\t- 1. ���� ����");
		System.out.println("\t\t\t- 2. ��ü ����");
		System.out.println("\t\t\t- 3. ó������");
		System.out.print("\t\t\t - ���� : ");
		int input = scan.nextInt();

		if(input == 1){
			deleteSelectedValue();
		} else if(input == 2){
			studentsMap.clear();
			System.out.println("\t\t\t��� �������!!");
		} else if(input == 3){
			System.out.println("\t\t\tó������!");
			return;
		}

	}

	private void deleteSelectedValue() throws InputMismatchException{
		System.out.print("\t\t\t\t - ������ ��ȣ �Է� : ");
		int number = scan.nextInt();
		if(studentsMap.containsKey(number)){
			System.out.println("\t\t\t\t"+number+ "��° ����!!");
			studentsMap.remove(number);
		} else{
			System.out.println("\n\t\t\t\t���� ��ȣ�Դϴ�!!");
		}
	}

	public void writeFile(){
		fio.inputSaveFileName();
	}
	
	public void changeFile() throws WrongExtensionException{
		isChange = true;
		inputMenu();
	}

	public void printInputMismatchException(){
		System.out.println("\n\t\t\t�߸� �Է��߽��ϴ�!\n");
		scan.nextLine();
	}
}