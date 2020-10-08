package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //get input
        try {

            var sourceRadix = scanner.nextInt();
            String no = scanner.next();
            var targetRadix = scanner.nextInt();

            //start error checks

            //check radix range
            if ((sourceRadix < 1 || sourceRadix > 36) || (targetRadix < 1 || targetRadix > 36)) {
                System.out.println("error");
                return;
            }

            //check for decimal
            if (no.contains(".")) {
                if (sourceRadix == 1 || targetRadix == 1) {
                    System.out.println("error");
                }
            }

            try {

                //print if source radix == 1
                if (sourceRadix == 1) {
                    System.out.println(
                            Integer.toString(no.length(), targetRadix)
                    );
                }

                //print if target radix == 1
                else if (targetRadix == 1) {
                    int noDigits = Integer.parseInt(no.split("\\.")[0], sourceRadix);

                    System.out.println("1".repeat(noDigits));
                }

                //normal algorithm
                else {
                    String[] temp = no.split("\\.");

                    String integer = temp[0];
                    String decimal;
                    try {
                        decimal = temp[1];
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                        decimal = "0";
                    }


                    int base10Int = Integer.parseInt(integer, sourceRadix);
                    double base10decimal;

                    if (sourceRadix == 10) {
                        base10decimal = Double.parseDouble("." + decimal);

                    } else {
                        //manually convert decimal
                        if (decimal.equals("0")) {
                            base10decimal = 0;
                        } else {
                            double base = sourceRadix;
                            double sum = 0;
                            for (char charDigit : decimal.toCharArray()) {
                                sum += Character.digit(charDigit, sourceRadix) / base;
                                base *= sourceRadix;
                            }
                            base10decimal = sum;
//                    String Temp = String.valueOf(sum);

//                    base10decimal = Integer.parseInt(Temp.substring(2, Math.min(Temp.length(), 12)));
                        }
                    }

                    //convert base 10 to required base
                    if (targetRadix == 10)
                        System.out.println(base10Int + base10decimal);

                    else {
                        StringBuilder targetBaseDecimal = new StringBuilder();

                        double base10no = base10decimal;

                        for (int i = 0; i < 5; i++) {
                            if (base10no == 0)
                                break;

                            base10no *= targetRadix;

                            String[] wholeDecimal = Double.toString(base10no).split("\\.");

                            targetBaseDecimal.append(Integer.toString(Integer.parseInt(wholeDecimal[0]), targetRadix));
                            base10no = Double.parseDouble("." + wholeDecimal[1]);
                        }

                        String targetBaseInt = Integer.toString(base10Int, targetRadix);

                        System.out.println(targetBaseInt + "." + targetBaseDecimal);
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("error");
            }
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}
