import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CirclePointRelation {
    public static void main(String[] args) {
        // Проверяем, переданы ли аргументы командной строки
        if (args.length < 2) {
            System.out.println("Необходимо указать пути к файлам: <файл с окружностью> <файл с точками>");
            return;
        }

        String circleFilePath = args[0]; // Путь к файлу с окружностью
        String pointsFilePath = args[1];  // Путь к файлу с точками

        double circleX = 0, circleY = 0, radius = 0;

        // Чтение файла с окружностью
        try (BufferedReader br = new BufferedReader(new FileReader(circleFilePath))) {
            String[] circleData = br.readLine().split(" ");
            circleX = Double.parseDouble(circleData[0]);
            circleY = Double.parseDouble(circleData[1]);
            radius = Double.parseDouble(br.readLine());
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла с окружностью: " + e.getMessage());
            return;
        }

        // Чтение файла с точками
        try (BufferedReader br = new BufferedReader(new FileReader(pointsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] pointData = line.split(" ");
                double pointX = Double.parseDouble(pointData[0]);
                double pointY = Double.parseDouble(pointData[1]);

                // Расчет положения точки относительно окружности
                double distanceSquared = Math.pow(pointX - circleX, 2) + Math.pow(pointY - circleY, 2);
                double radiusSquared = Math.pow(radius, 2);

                if (distanceSquared < radiusSquared) {
                    System.out.println(1); // Точка внутри окружности
                } else if (distanceSquared == radiusSquared) {
                    System.out.println(0); // Точка на окружности
                } else {
                    System.out.println(2); // Точка снаружи окружности
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла с точками: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Ошибка при преобразовании координат: " + e.getMessage());
        }
    }
}
