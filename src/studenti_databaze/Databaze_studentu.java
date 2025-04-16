package studenti_databaze;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors

public class Databaze_studentu {
	private HashMap<Integer,Student> studenti=new HashMap<Integer, Student>();
	
	void pridat_studenta_dialog() {
		Scanner Dsc=new Scanner(System.in);
		String jmeno,prijmeni,input;
		int cislo_oboru;
		boolean spatne=false;
		LocalDate narozeni = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		
		System.out.println("Zadejte jmeno studenta");
		jmeno=Dsc.nextLine();
		System.out.println("Zadejte prijmeni studenta");
		prijmeni=Dsc.nextLine();
		do {
			spatne=false;
			System.out.println("Zadejte datum narozeni studenta (v tomto formatu dd.MM.yyyy)");
			try {
				input=Dsc.nextLine();
				System.out.println(input);
			narozeni=LocalDate.parse(input, formatter);
			}
			catch(DateTimeParseException e){
				spatne=true;
			}
		}while(spatne);
		
		do {
		System.out.println("Zadejte skupinu studenta\n1] telekomunikace\n2] kyberbezpečnosti");

		cislo_oboru=Dsc.nextInt();
		}while(cislo_oboru!=1&&cislo_oboru!=2);
		
		Student s = new Student(jmeno,prijmeni,studenti.size(),narozeni,cislo_oboru);
		
		if(this.pridat_studenta(s)) {
			System.out.printf("Student %s %s s datem narozeni:%s a ID:%d byl uspesne pridan",jmeno,prijmeni,narozeni.toString(),s.getID());
		};
	}
	boolean pridat_studenta(Student student) {
		try {
			int ID_studenta;
			if(student.getID()==-1) {
				ID_studenta=studenti.size();
			}
			else {
				ID_studenta=student.getID();
			}
			student.setID(ID_studenta);
			studenti.put(ID_studenta,student);
			return true;
		}
		catch(Exception e) {
			System.err.println(e);
			return false;
		}
	}
	
	boolean odebrat_studenta(int ID) {
			if(studenti.remove(ID) != null) {
				return true;
			}
			else{
				
				return false;
			}
			


	}
	void celkový_prumer() {
		double k_prumer,i_prumer;
		int k_celkem=0,i_celkem=0,i_i=0,k_i=0;
		for(Student student:studenti.values()) {
			
			for(Integer znamka:student.znamky) {
				if(student.getObor()==1) {
					System.out.println(znamka);
					i_celkem+=znamka;
					i_i++;
				}
					else {
						//System.out.println(znamka);
					k_celkem+=znamka;
					k_i++;
					}

			}
		}
		System.out.println(i_celkem);
		System.out.println(k_celkem);
		k_prumer=(double)k_celkem/(double)k_i;
		i_prumer=(double)i_celkem/(double)i_i;
		System.out.println(i_prumer);
		System.out.println("Celkový průměr\nkybernetická bezpečnost: "+k_prumer+"\ninformační bezpečnost: "+i_prumer);
		}
	void pocet_studentu() {
		int i_pocet=0,k_pocet=0;
		for(Student student:studenti.values()) {
			if(student.getObor()==1) {
				i_pocet++;
			}
			else {
				k_pocet++;
			}
		}
		System.out.println("Počet studentů\n"+"kybernetická bezpečnost: "+k_pocet+"\ninformační bezpečnost: "+i_pocet);
	}
	
	String vypsat_studenty() {
		Student[] serazeni= new Student[studenti.size()];
		String tele="telekomunikace:\n",kyber="kyberbezpecnost:\n";
		int i=0;
		for(Student student:studenti.values()) {
			serazeni[i]=student;
			i++;
		}
		Arrays.sort(serazeni, (o1, o2) -> o1.getPrijmeni().compareTo(o2.getPrijmeni()));

		for(int i1=0, n=serazeni.length;i1<n;i1++) {
			if(serazeni[i1].getObor()==1) {
				tele=tele+serazeni[i1].toString()+"\n";
			}
			else {
				kyber=kyber+serazeni[i1].toString()+"\n";
			}
		}
		return tele+kyber;
		
	}
	Student vypsat_studenta(int ID) {
		try {
		return studenti.get(ID);
		}
		catch (NullPointerException e) {
			System.err.println("Student s timto ID neexistuje");
			return null;
		}
	}
	
	boolean pridat_znamku(int ID,int znamka) {
		try {
			studenti.get(ID).pridat_znamku(znamka);
			return true;
		}
		catch (NullPointerException e) {
			System.err.println("student ID:"+ID+" neexistuje");
			return false;
		}
	}
	boolean nacist_studenty_soubor(String cesta_soubor) {
		try {
			File sStudenti=new File(cesta_soubor);
			Scanner sSc=new Scanner(sStudenti);
			LocalDate datum;
			int obor;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		      while (sSc.hasNextLine()) {
		          String data = sSc.nextLine();
		          String[] csv_data = data.split(",");
		          if(csv_data.length!=4) continue;
		          try {
		          datum=LocalDate.parse(csv_data[2], formatter);
		          obor=Integer.parseInt(csv_data[3]);
		          }
		          catch (java.lang.NumberFormatException e) {
		        	  System.out.println("obor musi byt cislo");
					continue;
				 }
		          catch (java.time.format.DateTimeParseException e) {
		        	  System.out.println("datum musi byt ve formatu dd.MM.yyyy");
		        	  continue;
				}
		          if(obor!=1&&obor!=2) {
		        	  System.out.println("obor je 1 nebo 2");
		        	  continue;
		          }
		          this.pridat_studenta(new Student(csv_data[0],csv_data[1],datum,obor));

		        }
		      sSc.close();
		}
		catch(FileNotFoundException e){
			System.err.println(e);
			return false;
		}

		return true;
	}
	boolean smazat_studenty_soubor(String cesta_soubor,boolean dle_ID) {
		try {
			File sStudenti=new File(cesta_soubor);
			Scanner sSc=new Scanner(sStudenti);
			LocalDate datum;
			int obor;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		      while (sSc.hasNextLine()) {
		          String data = sSc.nextLine();
		          String[] csv_data = data.split(",");
		  		if(dle_ID) {
		          if(csv_data.length==1) {
		        	  try {
		        	  this.odebrat_studenta(Integer.parseInt(csv_data[0]));
		        	  }
		        	  catch (NumberFormatException e) {
						System.err.println("ID musi byt cislo");
						continue;
					}
		          }
		  		}
		  		else {
		  			if(csv_data.length==4) {
		  			String jmeno=csv_data[0],prijmeni=csv_data[1];
			          try {
			          datum=LocalDate.parse(csv_data[2], formatter);
			          obor=Integer.parseInt(csv_data[3]);
			          }
			          catch (NumberFormatException e) {
			        	  System.out.println("obor musi byt cislo");
						continue;
					 }
			          catch (DateTimeParseException e) {
			        	  System.out.println("datum musi byt ve formatu dd.MM.yyyy");
			        	  continue;
					}
			          if(obor!=1&&obor!=2) {
			        	  System.out.println("obor je 1 nebo 2");
			        	  continue;
			          }
			          
			          List<Integer> studentiNaSmazani = new ArrayList<>();

			          for(Student student:studenti.values()) {
			        	  if(student.getJmeno().equals(jmeno) && student.getPrijmeni().equals(prijmeni) && student.getDatum_narozeni().equals(datum)&&student.getObor()==obor){
			        		  studentiNaSmazani.add(student.getID());
			        	  }
			          }
			          for(int i=0,n=studentiNaSmazani.size();i<n;i++) {
			        	  this.odebrat_studenta(studentiNaSmazani.get(i)); 
			          }

		  		}
		  		}
		        }
		      sSc.close();
		}
		catch(FileNotFoundException e){
			System.err.println(e);
			return false;
		}

		return true;
	}
	Integer[] getIDs() {
	    return studenti.keySet().toArray(new Integer[studenti.size()]);
	}
	
}
