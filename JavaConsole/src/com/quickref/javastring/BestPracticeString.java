package com.quickref.javastring;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BestPracticeString {
	
	/*
	 * Vì String trong Java là immutable (không thể thay đổi), việc thay đổi nội
	 * dung của chuỗi sẽ tạo ra một đối tượng String mới. Điều này có thể làm tăng
	 * đáng kể độ phức tạp của code, đặc biệt là khi bạn cần thay đổi nội dung chuỗi
	 * nhiều lần. Trong trường hợp này, nên sử dụng StringBuilder để tạo ra một
	 * chuỗi có thể thay đổi.
	 */
	public static void usingStringBuilder() {
		StringBuilder sb = new StringBuilder();
		sb.append("Hello");
		sb.append(" ");
		sb.append("World");
		String result = sb.toString(); // result = "Hello World"
		System.out.println(result);
	}
	
	/*
	 * Khi bạn cần tạo ra một chuỗi có định dạng, hãy sử dụng phương thức format()
	 * của lớp String. Phương thức này cho phép bạn tạo ra một chuỗi có định dạng
	 * thông qua việc kết hợp các chuỗi và các đối tượng khác với một chuỗi định
	 * dạng.
	 */
	public static void usingFormat() {
		String name = "John";
		int age = 30;
		String result = String.format("My name is %s and I am %d years old.", name, age);
		// result = "My name is John and I am 30 years old."
		System.out.println(result);
	}
	
	/*
	 * Khi kiểm tra một chuỗi có rỗng hay không, nên sử dụng phương thức isEmpty()
	 * thay vì phương thức length(). Phương thức isEmpty() trả về true nếu chuỗi
	 * rỗng và false nếu không.
	 */
	public static void usingIsEmpty() {
		String str = "";
		boolean isEmpty = str.isEmpty(); // isEmpty = true
		System.out.println(isEmpty);
	}
	
	/*
	 * NumberFormat định dạng các số theo kiểu số tiền tệ, phần trăm, số nguyên, số
	 * thập phân, vv. Nó cung cấp các phương thức để định dạng các số thành chuỗi
	 * định dạng phù hợp và phân tích các chuỗi định dạng để chuyển đổi thành các
	 * giá trị số.
	 */
	public static void usingNumberFormat () throws ParseException {
		// Định dạng số theo kiểu tiền tệ
		double num = 1234567.89;
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String formattedNum = formatter.format(num); // formattedNum = "$1,234,567.89"
		System.out.println(formattedNum);
		
		// Định dạng số theo kiểu phần trăm
		num = 0.75;
		formatter = NumberFormat.getPercentInstance();
		formattedNum = formatter.format(num); // formattedNum = "75%"
		System.out.println(formattedNum);
		
		// Định dạng số theo kiểu số nguyên
		int num1 = 1234567;
		formatter = NumberFormat.getIntegerInstance();
		formattedNum = formatter.format(num1); // formattedNum = "1,234,567"
		System.out.println(formattedNum);
		
		// Định dạng số theo kiểu số thập phân
		num = 1234567.89;
		formatter = NumberFormat.getNumberInstance();
		formattedNum = formatter.format(num); // formattedNum = "1,234,567.89"
		System.out.println(formattedNum);
		
		// Phân tích một chuỗi định dạng thành một giá trị số
		String numString = "1,234,567.89";
		formatter = NumberFormat.getNumberInstance();
		Number num2 = formatter.parse(numString);
		double doubleNum = num2.doubleValue(); // doubleNum = 1234567.89
		System.out.println(doubleNum);

	}
	
	/*
	 * DecimalFormat là một lớp con của NumberFormat trong Java, cung cấp cho chúng
	 * ta các phương thức để định dạng số theo một định dạng cụ thể. Định dạng số
	 * bao gồm số lượng chữ số thập phân, ký tự phân cách thập phân, ký tự phân cách
	 * hàng nghìn, vv.
	 */
	public static void usingDecimalFormat () throws ParseException {
		// Định dạng số thập phân với số lượng chữ số thập phân cố định
		double num = 1234567.89;
		DecimalFormat df = new DecimalFormat("#0.00");
		String formattedNum = df.format(num); // formattedNum = "1234567.89"
		System.out.println(formattedNum);
		
		// Định dạng số thập phân với phân cách hàng nghìn
		num = 1234567.89;
		df = new DecimalFormat("#,##0.00");
		formattedNum = df.format(num); // formattedNum = "1,234,567.89"
		System.out.println(formattedNum);
		
		// Định dạng số thập phân với ký tự phân cách thập phân là dấu phẩy
		num = 1234567.89;
		df = new DecimalFormat("#0.00");
		df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY));
		formattedNum = df.format(num); // formattedNum = "1234567,89"
		System.out.println(formattedNum);
		
		// Định dạng số thập phân với ký tự phân cách thập phân và hàng nghìn được tùy chỉnh
		num = 1234567.89;
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		symbols.setGroupingSeparator(',');
		df = new DecimalFormat("#,##0.00", symbols);
		formattedNum = df.format(num); // formattedNum = "1,234,567.89"
		System.out.println(formattedNum);
		
	}
	
	/*
	 * DateFormat là một lớp trong Java được sử dụng để định dạng ngày tháng theo
	 * một kiểu cụ thể. Lớp DateFormat cung cấp cho chúng ta các phương thức để định
	 * dạng ngày tháng dưới dạng chuỗi và phân tích chuỗi ngày tháng thành các đối
	 * tượng ngày tháng. Lớp DateFormat có thể được sử dụng để định dạng ngày tháng
	 * theo các kiểu khác nhau, bao gồm kiểu ngắn, kiểu dài, kiểu ngày tháng, kiểu
	 * giờ phút, kiểu đếm thời gian, vv.
	 */
	public static void usingDateFormat () throws ParseException {
		// Định dạng ngày tháng dưới dạng chuỗi
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate = df.format(date);
		System.out.println(formattedDate);
		
		// Phân tích chuỗi ngày tháng thành đối tượng ngày tháng
		String strDate = "25/03/2023";
		df = new SimpleDateFormat("dd/MM/yyyy");
		date = df.parse(strDate); // date = "Fri Mar 25 00:00:00 GMT 2023"
		System.out.println(date);
		
		// Định dạng ngày tháng theo kiểu ngắn
		date = new Date();
		df = DateFormat.getDateInstance(DateFormat.SHORT);
		formattedDate = df.format(date);
		System.out.println(formattedDate);
		
		// Định dạng ngày tháng theo kiểu dài
		date = new Date();
		df = DateFormat.getDateInstance(DateFormat.LONG);
		formattedDate = df.format(date);
		System.out.println(formattedDate);
		
	}
	
	public static void main(String[] args) throws ParseException {
//		usingStringBuilder();
//		usingFormat();
//		usingIsEmpty();
//		usingNumberFormat();
//		usingDecimalFormat();
		usingDateFormat();
	}
	
}
