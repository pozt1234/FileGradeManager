import java.util.*;

public class GradeManager{
	public static void main(String[] args) {
		Management m = new Management();
		int input = 0;
		while(true){

			try{
				m.showList();
				switch(m.selectList()){
					case 1:
					m.inputMenu();
					break;					

					case 2:
					m.showGradeByRank();
					break;

					case 3:
					m.showGradeByNum();
					break;

					case 4:
					m.showGradeInDetail();
					break;

					case 5:
					m.showDeleteMenu();
					break;

					case 6:
					m.writeFile();
					break;

					case 7:
					m.changeFile();
					break;

					case 8:
					return;

					default:
					
				}
			} catch(InputMismatchException ime){
			 	m.printInputMismatchException();
			} catch(WrongExtensionException we){
				we.getMessage();
			}
		}
	}
}