package studenti_databaze;

import java.time.LocalDate;


public class main {

    public static void main(String[] args) {
    	Databaze_studentu databaze = new Databaze_studentu();
    	
        Student s1 = new Student("Jan", "Novák", 1001, LocalDate.of(2004, 3, 15), 1);
        Student s2 = new Student("Eva", "Dvořáková", 1002, LocalDate.of(2003, 7, 22), 2);
        Student s3 = new Student("Petr", "Svoboda", 1003, LocalDate.of(2005, 1, 10), 1);
        Student s4 = new Student("Lucie", "Králová", 1004, LocalDate.of(2004, 11, 5), 2);
        Student s5 = new Student("Tomáš", "Horák", 1005, LocalDate.of(2003, 6, 30), 1);
        Student s6 = new Student("Jan", "Arnost", 1001, LocalDate.of(2002, 3, 15), 1);
        Student s7 = new Student("Jan", "Zebra", 1001, LocalDate.of(2003, 3, 15), 1);
    	
    	
    	s2.pridat_znamku(2);
    	
    	databaze.pridat_studenta(s1);
    	databaze.pridat_studenta(s2);
    	databaze.pridat_studenta(s3);
    	databaze.pridat_studenta(s4);
    	databaze.pridat_studenta(s7);
    	databaze.pridat_studenta(s5);
    	databaze.pridat_studenta(s6);
    	//System.out.println(databaze.vypsat_studenta(4));
    	databaze.pridat_znamku(1,2);
    	databaze.pridat_znamku(1,3);
    	databaze.pridat_znamku(3,4);
	    	s1.pridat_znamku(3);
	    	s1.pridat_znamku(5);
	    	s1.pridat_znamku(2);
	    	s2.pridat_znamku(2);
	    	s2.pridat_znamku(4);
	    	s2.pridat_znamku(1);
	    	s3.pridat_znamku(4);
	    	s3.pridat_znamku(3);
	    	s3.pridat_znamku(5);
	    	s4.pridat_znamku(1);
	    	s4.pridat_znamku(2);
	    	s4.pridat_znamku(3);
	    	s5.pridat_znamku(5);
	    	s5.pridat_znamku(2);
	    	s5.pridat_znamku(4);
	    	s6.pridat_znamku(3);
	    	s6.pridat_znamku(1);
	    	s6.pridat_znamku(5);
	    	s7.pridat_znamku(4);
	    	s7.pridat_znamku(2);
	    	s7.pridat_znamku(3);



    	databaze.vypsat_studenty();
    	//System.out.println(databaze.odebrat_studenta(2));
    	System.out.println(s2.dovednost());
    	System.out.println(s5.dovednost());
    	databaze.celkový_prumer();
    	databaze.pocet_studentu();
    	databaze.nacist_studenty_soubor("studenti.csv");
    	databaze.vypsat_studenty();
    	System.out.println(databaze.vypsat_studenta(8));
    	databaze.smazat_studenty_soubor("studenti_v.csv", false);
    	databaze.vypsat_studenty();
    }
}

