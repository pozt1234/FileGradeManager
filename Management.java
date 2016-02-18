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
		System.out.println("\n\n\t  ┌────────────────┐");
		System.out.println("\t  │    학생 성적 관리 프로그램     │");
		System.out.println("\t  ├────────────────┤");
		System.out.println("\t  │\t1. 학생 성적 등록\t    │");
		System.out.println("\t  │\t2. 순위별 학생 성적 조회    │");
		System.out.println("\t  │\t3. 번호별 학생 성적 조회    │");	
		System.out.println("\t  │\t4. 학생 상세 조회\t    │");
		System.out.println("\t  │\t5. 학생 성적 삭제\t    │");
		System.out.println("\t  │\t6. 성적 파일 저장\t    │");
		System.out.println("\t  │\t7. 성적 파일 교체\t    │");
		System.out.println("\t  │\t8. 종료\t\t\t    │");
		System.out.println("\t  ├────────────────┤");
		if(hasFile)
			System.out.println("\t  │\t 입력된 파일 : " +  openFileName + " \t    │");
		else
			System.out.println("\t  │\t 입력된 파일이 없습니다.    │");
		System.out.println("\t  └────────────────┘");
		System.out.println();
	}

	public int selectList(){
		int input=0;
		try{
			System.out.print("\t\t- 선택 : ");
			input = scan.nextInt();
			scan.nextLine();
		}catch(InputMismatchException ime){
			input = selectList();
		}
		return input;
	}


	// 학생 정보 입력
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

		System.out.print("\n\t\t- 입력할 학생의 수 : ");
		userInputSize = scan.nextInt(); 
		for(int i=0; i<userInputSize; i++){
			scan.nextLine();
			addStudent();
		}
	}

	public void addStudent() throws S{
		try{
			System.out.print("\t\t- 번호 : ");
			int number = scan.nextInt();
			existNumber(number);

			scan.nextLine();
			System.out.print("\t\t- 이름 : ");
			String name = scan.nextLine();
			System.out.print("\t\t- 국어 : ");
			int kor = scan.nextInt();
			System.out.print("\t\t- 영어 : ");
			int eng = scan.nextInt();
			System.out.print("\t\t- 수학 : ");
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
			System.out.println("\t\t이미 존재하는 번호입니다.");
			System.out.print("\t\t덮어씌우시겠습니까? [1:Yes / 2:No]");
			int input = scan.nextInt();
			if(input == 1){
				break;
			}else if(input == 2){
				System.out.print("\t\t- 다른 번호 : ");
				number = scan.nextInt();
			} else{
				System.out.println("\n\t\t잘못된 선택입니다!\n");
			}
		}
		return number;
	}

	public void showFileList(){
		File file = new File("./InputInfo");
		File[] files = file.listFiles();
		System.out.println("\n\t\t<InputInfo의 파일 목록>");
		for(File tempFile : files){
			System.out.println("\t\t   - "+tempFile.getName() + "\t"+ String.format("%1$tY-%1$tm-%1$te",  new Date(tempFile.lastModified())));
		}
		System.out.println();
	}

	public void showGradeByRank(){
		if(studentsMap == null)
		throw	new NullPointerException();
		
		countRank();

		System.out.println("\n\n\n\t\t   <순위별 학생 성적 조회>");
		printStudentInfo();
	}

	public void showGradeByNum(){
		if(studentsMap == null)
			throw new  NullPointerException();

		countRank();
		Collections.sort(studentsList, new NumberComparator());

		System.out.println("\n\n\n\t\t   <번호별 학생 성적 조회>");
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
		System.out.println("번호\t이름\t국어\t영어\t수학\t총점\t평균\t순위");
		for(Student student : studentsList){
			System.out.println(student);
		}
	}

	public void showGradeInDetail(){
		if(studentsMap == null)
			throw new NullPointerException();
		System.out.println("\t\t\t- 1 : 이름 조회");
		System.out.println("\t\t\t- 2 : 번호 조회");
		try{
			System.out.print("\t\t\t\t- 선택 : ");
			int input = scan.nextInt();

			if(input == 1){
				searchToName();
			} else if(input == 2){
				searchToNumber();
			} else{
				System.out.println("\n\t\t\t\t잘못 선택했습니다!");
			}
		}catch(InputMismatchException ime){
			printInputMismatchException();
			showGradeInDetail();
		}
	}

	private void searchToName(){		
		scan.nextLine();
		System.out.print("\t\t\t\t- 조회할 이름 : ");
		String name = scan.nextLine();

		Set<Integer> keys = studentsMap.keySet();
		boolean hasName = false;		
		for(Integer key : keys){
			Student student = studentsMap.get(key);
			if(student.getName().equals(name) && !hasName){
				System.out.println("번호\t이름\t국어\t영어\t수학\t총점\t평균\t순위");
				System.out.println(student);
				hasName = true;
			} else if(student.getName().equals(name)) {
				System.out.println(student);
			}
		}
		if(!hasName){
			System.out.println("\t\t\t\t일치하는 이름이 없습니다!");
		}
	}

	private void searchToNumber() throws InputMismatchException{
		System.out.print("\t\t\t\t- 조회할 번호 : ");
		int number = scan.nextInt();

		if(studentsMap.containsKey(number)){
			Student student = studentsMap.get(number);
			System.out.println("번호\t이름\t국어\t영어\t수학\t총점\t평균\t순위");
			System.out.println(student);
		}else{		
			System.out.println("\t\t\t\t일치하는 번호가 없습니다!");
		}
	}

	public void showDeleteMenu() throws InputMismatchException{
		System.out.println("\t\t\t- 1. 선택 삭제");
		System.out.println("\t\t\t- 2. 전체 삭제");
		System.out.println("\t\t\t- 3. 처음으로");
		System.out.print("\t\t\t - 선택 : ");
		int input = scan.nextInt();

		if(input == 1){
			deleteSelectedValue();
		} else if(input == 2){
			studentsMap.clear();
			System.out.println("\t\t\t모든 내용삭제!!");
		} else if(input == 3){
			System.out.println("\t\t\t처음으로!");
			return;
		}

	}

	private void deleteSelectedValue() throws InputMismatchException{
		System.out.print("\t\t\t\t - 삭제할 번호 입력 : ");
		int number = scan.nextInt();
		if(studentsMap.containsKey(number)){
			System.out.println("\t\t\t\t"+number+ "번째 삭제!!");
			studentsMap.remove(number);
		} else{
			System.out.println("\n\t\t\t\t없는 번호입니다!!");
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
		System.out.println("\n\t\t\t잘못 입력했습니다!\n");
		scan.nextLine();
	}
}