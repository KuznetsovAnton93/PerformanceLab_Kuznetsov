import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MinMoves {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide the input file path.");
            return;
        }

        String filePath = args[0];
        List<Integer> nums = readNumbersFromFile(filePath);

        if (nums.isEmpty()) {
            System.out.println("No numbers found in the file.");
            return;
        }

        int minMoves = calculateMinMoves(nums);
        System.out.println("Минимальное количество ходов: " + minMoves);
    }

    private static List<Integer> readNumbersFromFile(String filePath) {
        List<Integer> nums = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                nums.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return nums;
    }

    private static int calculateMinMoves(List<Integer> nums) {
        int totalMoves = 0;
        int target = findMedian(nums);

        for (int num : nums) {
            totalMoves += Math.abs(num - target);
        }

        return totalMoves;
    }

    private static int findMedian(List<Integer> nums) {
        nums.sort(Integer::compareTo);
        int n = nums.size();
        return nums.get(n / 2);  // Возвращаем медиану
    }
}