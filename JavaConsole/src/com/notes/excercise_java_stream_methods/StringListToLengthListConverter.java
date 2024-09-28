import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Tạo ArrayList để lưu trữ 1000 chuỗi ngẫu nhiên
        ArrayList<String> possibleStrings = new ArrayList<>(1000);

        // Tạo một danh sách để lưu trữ thời gian trung bình
        List<MethodTime> methodTimes = new ArrayList<>();

        // Lặp lại 1000 lần
        for (int i = 0; i < 1000; i++) {
            // Gọi phương thức để tạo chuỗi ngẫu nhiên với độ dài cũng ngẫu nhiên
            possibleStrings.clear(); // Xóa dữ liệu trước đó
            for (int j = 0; j < 1000; j++) {
                int randomLength = generateRandomLength(); // Độ dài ngẫu nhiên
                possibleStrings.add(generateRandomString(randomLength)); // Thêm chuỗi ngẫu nhiên vào ArrayList
            }

            // Đo thời gian cho cách sử dụng Stream
            long startTimeStream = System.nanoTime();
            List<Integer> lengthsStream = possibleStrings.stream()
                    .map(String::length)
                    .collect(Collectors.toList());
            long endTimeStream = System.nanoTime();
            long durationStream = endTimeStream - startTimeStream;

            // Đo thời gian cho cách sử dụng vòng lặp truyền thống
            long startTimeLoop = System.nanoTime();
            List<Integer> lengthsLoop = new ArrayList<>(possibleStrings.size());
            for (String s : possibleStrings) {
                lengthsLoop.add(s.length());
            }
            long endTimeLoop = System.nanoTime();
            long durationLoop = endTimeLoop - startTimeLoop;

            // Lưu thời gian vào danh sách
            methodTimes.add(new MethodTime("Stream", durationStream));
            methodTimes.add(new MethodTime("Vòng lặp", durationLoop));
        }

        // Tính toán thời gian trung bình
        List<AverageTime> averageTimes = new ArrayList<>();
        for (String method : new String[]{"Stream", "Vòng lặp"}) {
            long totalDuration = methodTimes.stream()
                    .filter(mt -> mt.method.equals(method))
                    .mapToLong(mt -> mt.duration)
                    .sum();
            averageTimes.add(new AverageTime(method, totalDuration / 50)); // Tính trung bình
        }

        // Sắp xếp theo thời gian trung bình
        Collections.sort(averageTimes, Comparator.comparingLong(a -> a.averageDuration));

        // In ra kết quả
        System.out.println("\nThời gian trung bình thực hiện:");
        for (AverageTime avgTime : averageTimes) {
            System.out.println(avgTime.method + ": " + formatNumber(avgTime.averageDuration) + " nanoseconds");
        }
    }

    // Hàm tạo chuỗi ngẫu nhiên với độ dài cố định
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // Bộ ký tự dùng để tạo chuỗi
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex)); // Thêm ký tự ngẫu nhiên vào chuỗi
        }

        return sb.toString();
    }

    // Hàm tạo độ dài ngẫu nhiên từ 1 đến characters.length()
    public static int generateRandomLength() {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // Bộ ký tự có sẵn
        return random.nextInt(characters.length()) + 1; // Độ dài ngẫu nhiên từ 1 đến characters.length()
    }

    // Hàm định dạng số theo kiểu Việt Nam với dấu phẩy
    public static String formatNumber(long number) {
        // Sử dụng NumberFormat để định dạng số
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        return numberFormat.format(number);
    }

    // Lớp lưu trữ thông tin về thời gian từng phương pháp
    static class MethodTime {
        String method;
        long duration;

        MethodTime(String method, long duration) {
            this.method = method;
            this.duration = duration;
        }
    }

    // Lớp lưu trữ thông tin về thời gian trung bình
    static class AverageTime {
        String method;
        long averageDuration;

        AverageTime(String method, long averageDuration) {
            this.method = method;
            this.averageDuration = averageDuration;
        }
    }
}
