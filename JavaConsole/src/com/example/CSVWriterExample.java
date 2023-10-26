package com.example;

import java.io.FileWriter;
import java.io.IOException;

public class CSVWriterExample {
	public static void main(String[] args) {
		String csvFilePath = "data.csv";

		try (FileWriter writer = new FileWriter(csvFilePath)) {
			// Dữ liệu mẫu với ô chứa hai dòng
			String cellData = "Dòng 1\nDòng 2";

			// Xử lý ô có hai dòng bằng cách đặt trong dấu nháy kép và sử dụng ký tự xuống
			// dòng \n
//			String formattedCellData = "\"" + cellData.replace("\n", "\\n") + "\"";

			// Ghi dữ liệu vào tệp CSV
			writer.append(cellData.replace("\n", "\\n"));

			System.out.println("Tệp CSV đã được tạo thành công!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
