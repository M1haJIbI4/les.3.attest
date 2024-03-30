import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserDataParser {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные в формате: Фамилия Имя Отчество дата_рождения номер_телефона пол");

        String input = scanner.nextLine();
        String[] data = input.split(" ");

        if (data.length != 6) {
            System.out.println("Ошибка! Введено недостаточное или избыточное количество данных.");
            return;
        }

        String surname = data[0];
        String name = data[1];
        String patronymic = data[2];
        String dateOfBirthString = data[3];
        long phoneNumber;
        char gender;

        try {
            phoneNumber = Long.parseLong(data[4]);
            gender = data[5].charAt(0);

            if (gender != 'f' && gender != 'm') {
                throw new IllegalArgumentException("Неверно указан пол. Допустимые значения: f - женский, m - мужской.");
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            System.out.println("Ошибка! Неверный формат данных: " + e.getMessage());
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date dateOfBirth;
        try {
            dateOfBirth = new SimpleDateFormat("dd.MM.yyyy").parse(dateOfBirthString);
        } catch (ParseException e) {
            System.out.println("Ошибка! Неверный формат даты. Используйте формат dd.mm.yyyy");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(surname + ".txt"))) {
            String output = surname + " " + name + " " + patronymic + " " + dateFormat.format(dateOfBirth) + " " + phoneNumber + " " + gender;
            writer.write(output);
            System.out.println("Данные успешно записаны в файл " + surname + ".txt.");
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}