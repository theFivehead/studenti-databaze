package studenti_databaze;

import java.util.NoSuchElementException;
import java.util.Scanner;


public class main {
	static int id_interakce(Scanner id_sc) {
		try {
    	System.out.println("Zadejte ID studenta");
    	return Integer.parseInt(id_sc.nextLine());
		}
		catch (Exception e) {
			System.err.println("ID se nepodařilo načíst");
			return -1;
		}
	}

    public static void main(String[] args) {
    	Databaze_studentu databaze;
    	boolean bezet=true,znovu=false,vypsat=true;
    	String odpoved;
    	Scanner sc=new Scanner(System.in);

    	System.out.println("Chete načíst informace ze SQL souboru?\ny]\nn]");
    	odpoved=sc.nextLine();
    	System.out.println(odpoved);
    	if(odpoved.compareToIgnoreCase("y")==0) {
    	do {
			znovu=false;
    		System.out.println("zadejte nazev databaze");
    		String nazev_databaze=sc.nextLine();
    		databaze=SqliteDatabaze.nacist_data(nazev_databaze);
    		if(databaze==null) {

    			System.out.println("data ze souboru "+nazev_databaze+".db se nepodařilo načíst");
    			System.out.println("chcete to zkusit znovu?\ny]\nn]");
    			odpoved=sc.nextLine();
    			if(odpoved.compareToIgnoreCase("y")==0) {
    				znovu=true;
    			}
    			else {
    	    		System.out.println("vytvářím novou databázi");
    	        	databaze = new Databaze_studentu();
    	        	znovu=false;
    			}
    		}
    	}while(znovu);
    	}
    	else {
    		System.out.println("vytvářím novou databázi");
        	databaze = new Databaze_studentu();
    	}

    	while(bezet) {
    		if(vypsat) {
    		System.out.print("1)Přidat nového studenta\n2)zadat studentovi znamku\n"
    				+ "3)Propustit studenta\n4)Vypsat informace o studentovy\n"
    				+ "5)Spustit dovednost studenta\n6)Vypsat studenty\n7)Vypsat obecný studijní průměr\n"
    				+ "8)Vypsat celkový počet studentů v oborech\n9)načíst studenty ze souboru(csv) \n10)smazat/propustit studenty dle souboru\n"
    				+ "11)ukončit program\n");
    		}
    		else {
    			vypsat=true;
    		}

    		try {
    			switch (Integer.parseInt(sc.nextLine())) {
    		    case 1: {

    		        databaze.pridat_studenta_dialog();
    		        break;
    		    }
    		    case 2: {

    		        int id, znamka;
    		        id = id_interakce(sc);
    		        System.out.println("Zadejte známku (1-5)");
    		        znamka = Integer.parseInt(sc.nextLine());
    		        System.out.println(znamka);
    		        if(databaze.pridat_znamku(id, znamka)) {
    		        	System.out.println("Znamka byla přidána");
    		        }
    		        else {
    		        	System.out.println("Znamka je mimo rozsah 1-5");
    		        }
    		        break;
    		    }
    		    case 3: {
    
    		        int id = id_interakce(sc);
    		        if (databaze.odebrat_studenta(id)) {
    		            System.out.println("student byl odebrán");
    		        } else {
    		            System.out.printf("student s ID:%d neexistuje\n", id);
    		        }
    		        break;
    		    }
    		    case 4: {
    	
    		        System.out.println(databaze.vypsat_studenta(id_interakce(sc)).toString());
    		        break;
    		    }
    		    case 5: {
    	
    		        System.out.println(databaze.vypsat_studenta(id_interakce(sc)).dovednost());
    		        break;
    		    }
    		    case 6: {
 
    		        System.out.println("Výpis studentů:");
    		        System.out.println(databaze.vypsat_studenty());
    		        break;
    		    }
    		    case 7: {
    
    		        databaze.celkový_prumer();
    		        break;
    		    }
    		    case 8: {
    
    		        databaze.pocet_studentu();
    		        break;
    		    }
    		    case 9: {
    
    		        String nazev_souboru;
    		        System.out.println("Zadejte název souboru");
    		        nazev_souboru = sc.nextLine();
    		        if (databaze.nacist_studenty_soubor(nazev_souboru)) {
    		            System.out.println("Studenti byly uspěšně načteni");
    		        } else {
    		            System.out.println("Studenty nebylo možno, ze souboru načíst");
    		        }
    		        break;
    		    }
    		    case 10: {
    
    		        String nazev_souboru;
    		        boolean dleID=false;
    		        System.out.println("Zadejte název souboru");
    		        nazev_souboru = sc.nextLine();
    		        System.out.println("chcete mazat dle ID?\ny]\nn]");
    		        if(sc.nextLine().compareToIgnoreCase("y")==0) dleID=true;
    		        databaze.smazat_studenty_soubor(nazev_souboru, dleID);
    		        break;
    		    }
    		    case 11: {
    	
    		        bezet = false;
    		        System.out.println("Zadejte nazev databaze do, ktere se studenti ulozi");
    		        SqliteDatabaze.ulozit_data(sc.nextLine(), databaze);
    		        break;
    		    }
    		    default: {
    		        System.out.println("Neplatná volba, zkuste to prosím znovu.");
    		        break;
    		    }
    		}

    		}
    		catch(NumberFormatException e) {
    			System.err.println("musíte zadat číslo");
    			vypsat=false;
    		}
    		catch(NoSuchElementException e) {
    			System.err.println(e);
    			vypsat=false;
    		}
    		catch (NullPointerException e) {
				System.err.println("Student s tímto ID neexistuje");
				vypsat=false;
			}
    		System.out.println();
    	}
    	 System.out.println("Program byl ukončen");
    	sc.close();
    }
}

