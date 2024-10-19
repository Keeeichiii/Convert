package example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

class ExchangeRate {

    protected final Scanner input = new Scanner(System.in);
    protected final Map<String, Double> rates = new HashMap<>();

    public void loadRates() {
        try {
            String jsonValue = Files.readString(Paths.get("resource/data.json"));
            jsonValue = jsonValue.trim().replaceAll("[{}]", "");
            for (String pair : jsonValue.split(",")) {
                String[] keyValue = pair.split(":");
                rates.put(keyValue[0].trim().replace("\"", ""), Double.parseDouble(keyValue[1].trim().replace("\"", "")));
            }
        } catch (IOException e) {
            System.out.println("Ошибка:" + e.getMessage());
        }
    }

    public void CurrencyInputAndCalculationLogic() {
        loadRates();
        System.out.println("Доступные валюты: " + rates.keySet());
        String fromCurr = getCurr("Введите исходную валюту:");
        String toCurr = getCurr("Введите целевую валюту:");
        double amount = getAmount();
        double result = amount * rates.get(toCurr) / rates.get(fromCurr);
        System.out.printf("Ваша валюта в %s с учётом курса 1 %s = %s %s:\n",toCurr,fromCurr,rates.get(toCurr),toCurr);
        System.out.printf("%.2f %s = %.2f %s%n", amount, fromCurr, result, toCurr);

    }

    public String getCurr(String message) {
        String curr;
        do
        {
            System.out.println(message);
            curr = input.next().toUpperCase();
            if(!rates.containsKey(curr)){
                System.out.println("Такого кода валюты нету в списке! Повторите снова.");
            }
        }
        while (!rates.containsKey(curr));
        return curr;
    }

    public double getAmount() {
        double amount;
        do {
            System.out.println("Введите сумму для конвертации:");
            amount = input.nextDouble();

        } while (amount <= 0);
        return amount;
    }
}