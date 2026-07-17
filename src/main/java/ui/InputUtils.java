package ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtils {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static LocalDateTime getLocalDateTime(Scanner scanner) {
        while (true) {
            System.out.print("Ingrese fecha y hora (dd/MM/yyyy HH:mm): ");
            String input = scanner.nextLine().trim();

            try {
                return LocalDateTime.parse(input, DATE_TIME_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println(
                        "Fecha u hora inválida. Ejemplo válido: 25/07/2026 14:30"
                );
            }
        }
    }

    public static int getInt(Scanner input, String msg) {
        while (true) {
            System.out.print(msg);
            if (input.hasNextInt()) {
                int value = input.nextInt();
                input.nextLine();
                return value;
            } else {
                input.next();
                System.out.println("Invalid number, try again.");
            }
        }
    }

    public static double getDouble(Scanner input, String msg) {
        while (true) {
            System.out.print(msg);
            if (input.hasNextDouble()) {
                double value = input.nextDouble();
                input.nextLine();
                return value;
            } else {
                input.next();
                System.out.println("Invalid number, try again.");
            }
        }
    }

    public static String getString(Scanner input, String msg) {
        System.out.print(msg);
        return input.nextLine().trim().toLowerCase();
    }

    public static String getNonEmptyString(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("El campo no puede estar vacío.");
        }
    }


}
