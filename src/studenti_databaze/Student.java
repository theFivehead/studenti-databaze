package studenti_databaze;
import java.util.ArrayList;
import java.util.HashMap;
import byte_konvertor.Konvertor;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;


public class Student {
	private String jmeno, prijmeni;
	private int ID=-1;
	private LocalDate datum_narozeni;
	private int  obor = 0;//telekomunikace-1,kyberbezpecnost-2
	ArrayList<Integer> znamky=new ArrayList<Integer>();


	
	public Student(String jmeno, String prijmeni, int iD, LocalDate datum_narozeni, int cislo_oboru) {
		super();
		this.jmeno = jmeno;
		this.prijmeni = prijmeni;
		ID = iD;
		this.datum_narozeni = datum_narozeni;
		this.obor = cislo_oboru;
	}
	public Student(String jmeno, String prijmeni, LocalDate datum_narozeni, int cislo_oboru) {
		super();
		this.jmeno = jmeno;
		this.prijmeni = prijmeni;
		this.datum_narozeni = datum_narozeni;
		this.obor = cislo_oboru;
	}
	public Student() {}

	public double getPrumer() {
		int celkem=0,i=0;
		for(Integer znamka:this.znamky) {
			celkem+=znamka;
			i++;
		}
		if(i==0) {
			return 0;
		}
		else {
		return (double)celkem/(double)i;
		}
	}
	public int getObor() {
		return obor;
	}
	public String getJmeno() {
		return jmeno;
	}
	public void setJmeno(String jmeno) {
		this.jmeno = jmeno;
	}
	public String getPrijmeni() {
		return prijmeni;
	}
	public void setPrijmeni(String prijmeni) {
		this.prijmeni = prijmeni;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public LocalDate getDatum_narozeni() {
		return datum_narozeni;
	}
	public boolean pridat_znamku(int znamka) {
		if(znamka>=1&&znamka<=5) {
			this.znamky.add(znamka);
			return true;
		}
		else {
			return false;
		}
	}
	public String dovednost() {
		String kombinace=this.jmeno+this.prijmeni;
		switch(this.obor) {
		case 1:{
			try {
			HashMap<Character, String> morseMap = new HashMap<>();
			String encodedMorse="";
	        // Anglická abeceda
	        morseMap.put('A', ".-");
	        morseMap.put('B', "-...");
	        morseMap.put('C', "-.-.");
	        morseMap.put('D', "-..");
	        morseMap.put('E', ".");
	        morseMap.put('F', "..-.");
	        morseMap.put('G', "--.");
	        morseMap.put('H', "....");
	        morseMap.put('I', "..");
	        morseMap.put('J', ".---");
	        morseMap.put('K', "-.-");
	        morseMap.put('L', ".-..");
	        morseMap.put('M', "--");
	        morseMap.put('N', "-.");
	        morseMap.put('O', "---");
	        morseMap.put('P', ".--.");
	        morseMap.put('Q', "--.-");
	        morseMap.put('R', ".-.");
	        morseMap.put('S', "...");
	        morseMap.put('T', "-");
	        morseMap.put('U', "..-");
	        morseMap.put('V', "...-");
	        morseMap.put('W', ".--");
	        morseMap.put('X', "-..-");
	        morseMap.put('Y', "-.--");
	        morseMap.put('Z', "--..");

	        // Čísla (volitelné)
	        morseMap.put('0', "-----");
	        morseMap.put('1', ".----");
	        morseMap.put('2', "..---");
	        morseMap.put('3', "...--");
	        morseMap.put('4', "....-");
	        morseMap.put('5', ".....");
	        morseMap.put('6', "-....");
	        morseMap.put('7', "--...");
	        morseMap.put('8', "---..");
	        morseMap.put('9', "----.");

	        // Česká diakritická písmena (používají se rozšíření Morseovy abecedy)
	        morseMap.put('Á', ".--.-");
	        morseMap.put('Č', "----");
	        morseMap.put('Ď', "-..-.");
	        morseMap.put('É', "..-..");
	        morseMap.put('Ě', ".-..-");
	        morseMap.put('Í', "..--");
	        morseMap.put('Ň', "--.--");
	        morseMap.put('Ó', "---.");
	        morseMap.put('Ř', ".-.-");
	        morseMap.put('Š', "----");
	        morseMap.put('Ť', "-.-.-");
	        morseMap.put('Ú', "..--");
	        morseMap.put('Ů', "..--"); // stejné jako Ú
	        morseMap.put('Ý', "-.--");
	        morseMap.put('Ž', "--..-");
	        
	        for(int i=0,n=kombinace.length();i<n;i++) {
	        	encodedMorse=encodedMorse+morseMap.get(Character.toUpperCase(kombinace.charAt(i)))+"/";
	        }

	        return encodedMorse;
			}
			catch(NullPointerException e){
				System.err.println("pismeno ve studentove jmenu nebo prijmeni neni v morseove abecede");
			}
		}
		case 2:{
			try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(
					kombinace.getBytes(StandardCharsets.UTF_8));
			
			return Konvertor.bytesToHex(encodedhash);
			
			}
			catch (NoSuchAlgorithmException e) {
				System.err.println("SHA-256 nebylo mozno nacist");
			}
			
		}
	}
		return "";

	}
	public void setDatum_narozeni(LocalDate datum_narozeni) {
		this.datum_narozeni = datum_narozeni;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ID:"+this.ID+" jmeno:"+this.jmeno+" "+this.prijmeni+" narozen:"+datum_narozeni.toString()+" prumer:"+this.getPrumer();
	}



}
