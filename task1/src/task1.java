import java.util.Scanner;

public class task1 {
    public static void main(String[] args) {
        // Создаем объект Scanner для считывания данных из терминала
        Scanner scanner = new Scanner(System.in);

        // Запрашиваем у пользователя размер массива n
        System.out.print("Введите размер массива (n): ");
        int n = scanner.nextInt();

        // Запрашиваем у пользователя шаг интервала m
        System.out.print("Введите длину шага (m): ");
        int m = scanner.nextInt();

        // Проверка корректности ввода
        if (n <= 0 || m <= 0) {
            System.out.println("n и m должны быть положительными целыми числами.");
            return;
        }

        // Инициализируем круговой массив (для примера, числа от 1 до n)
        int[] circularArray = new int[n];
        for (int i = 0; i < n; i++) {
            circularArray[i] = i + 1;
        }

        // Переменная для текущей позиции
        int currentIndex = 0;

        // Запоминаем путь
        StringBuilder path = new StringBuilder();
        path.append(circularArray[currentIndex]); // Начало пути - первый элемент

        // Собираем путь с учетом шага m
        for (int i = 1; i < n; i++) {
            // Сдвигаем текущий индекс на шаг длиной m
            currentIndex = (currentIndex + m - 1) % n;

            // Если вернулись в начальную точку, останавливаемся
            if (currentIndex == 0) {
                break;
            }

            // Добавляем в путь первый элемент нового интервала
            path.append(circularArray[currentIndex]);
        }

        // Выводим путь
        System.out.println("Полученный путь: " + path.toString());

        // Закрываем Scanner
        scanner.close();
    }
}
