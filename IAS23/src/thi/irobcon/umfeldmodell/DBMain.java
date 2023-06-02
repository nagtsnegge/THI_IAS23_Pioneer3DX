package thi.irobcon.umfeldmodell;

public class DBMain {

	public static void main(String[] args) {

		DB db = new DB();
		db.open();
//		db.insertMesswert(3, 1, 1999-9, 110-0);
		db.updateMessicherheit("LaserScanner", 0.6);
		db.selectMesswerte(1);
		db.deleteMessung(1);
		db.close();
	}

}
