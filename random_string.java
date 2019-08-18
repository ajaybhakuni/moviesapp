package tds.com.moviezlub;

import java.util.Random;

class random_string {
    random_string() {
    }

    static String userId() {
        char[] toCharArray = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(toCharArray[random.nextInt(toCharArray.length)]);
        }
        return stringBuilder.toString();
    }
}
