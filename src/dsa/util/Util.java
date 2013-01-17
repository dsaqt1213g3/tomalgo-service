package dsa.util;

import java.util.GregorianCalendar;

public class Util {

	private Util() {
		// TODO Auto-generated constructor stub
	}

	public final static String toHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(toHex(bytes[i] >> 4));
			sb.append(toHex(bytes[i]));
		}

		return sb.toString();
	}

	private final static char toHex(int nibble) {
		final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'A', 'B', 'C', 'D', 'E', 'F' };
		return hexDigit[nibble & 0xF];
	}
	
	public static String getNow() {	
		GregorianCalendar calendar = new GregorianCalendar();		
		return calendar.get(GregorianCalendar.YEAR) + "-" + calendar.get(GregorianCalendar.MONTH) + "-" + 
				calendar.get(GregorianCalendar.DAY_OF_MONTH) + " " +
				calendar.get(GregorianCalendar.HOUR_OF_DAY) + ":" + calendar.get(GregorianCalendar.MINUTE);
	}
}
