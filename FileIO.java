import java.util.*;
import java.io.*;

public class FileIO{
	private HashMap<Integer, Student> studentsMap;
	private ArrayList<Student> studentsList;
	private String openFileName;
	private String[] extension;
	private boolean hasFile;
	Scanner scan = new Scanner(System.in);

	public FileIO(){ }

	public String[] confirmExtension(){
		System.out.print("\t\t불러올 파일 : ");
		openFileName = scan.nextLine();
		extension = openFileName.split("[.]");
		return extension;
	}

	public HashMap<Integer, Student> inputStudentInfoByTxt(){		
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader("./InputInfo/"+openFileName));
			String line = null;
			String[] splitLine = null;

			while((line = br.readLine()) != null){
				splitLine = line.split(" ");

				Student tempStu = new Student(Integer.parseInt(splitLine[0]), splitLine[1], Integer.parseInt(splitLine[2]), Integer.parseInt(splitLine[3]), Integer.parseInt(splitLine[4]));
				studentsMap.put(tempStu.getNumber(), tempStu);
			}
			hasFile = true;
		}catch(IOException ie){
			ie.printStackTrace();
		}finally{
			try{
				if(br != null)
					br.close();
			}catch(IOException ie){
				ie.printStackTrace();
			}
		}
		return studentsMap;
	}

	public HashMap inputStudentInfoBySer(){
		ObjectInputStream ois = null;
		try{
			ois = new ObjectInputStream(new FileInputStream("./InputInfo/"+openFileName));

			studentsMap = (HashMap)ois.readObject();			
		}catch(Exception ie){
			ie.printStackTrace();
		}finally{
			try{
				if(ois != null)
					ois.close();
			}catch(IOException ie){
				ie.printStackTrace();
			}
		}
		hasFile = true;
		return studentsMap;
	}

	public void inputSaveFileName(){
		System.out.print("저장할 파일 이름 : ");
		String fileName = scan.nextLine();
		extension = fileName.split("[.]");
		if(extension[1].equals("txt")){
			writeStudentInfoByTxt(fileName);
		} else if(extension[1].equals("ser")){
			writeStudentInfoBySer(fileName);
		}
	}


	public void writeStudentInfoByTxt(String fileName){
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter("./"+fileName));
			String[] splitLine = null;
			String line = null;
			Set<Integer> keys = studentsMap.keySet(); 
			for(Integer key : keys){
				Student tempStu = studentsMap.get(key);
				line = tempStu.getNumber() + " " + tempStu.getName() + " " + tempStu.getKor() + " " + tempStu.getEng() + " " + tempStu.getMath();
				bw.write(line);
			}
		}catch(IOException ie){
			ie.printStackTrace();
		}finally{
			try{
				if(bw != null)
					bw.close();
			}catch(IOException ie){
				ie.printStackTrace();
			}
		}
		System.out.println(fileName + "을 저장했습니다");
	}

	public void writeStudentInfoBySer(String fileName){
		ObjectOutputStream oos = null;
		try{
			oos = new ObjectOutputStream(new FileOutputStream("./InputInfo/"+fileName));

			oos.writeObject(studentsMap);
		}catch(IOException ie){
			ie.printStackTrace();
		}finally{
			try{
				if(oos != null)
					oos.close();
			}catch(IOException ie){
				ie.printStackTrace();
			}
		}
		System.out.println(fileName + "을 저장했습니다");
	}

	public String getOpenFileName(){
		return openFileName;
	}

	public boolean getHasFile(){
		return hasFile;
	}
}