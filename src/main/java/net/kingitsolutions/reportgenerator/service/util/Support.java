package net.kingitsolutions.reportgenerator.service.util;

import net.kingitsolutions.reportgenerator.dto.response.ReportResponseDto;
import org.springframework.context.annotation.Bean;

import java.util.Random;


public class Support {
    public static String generateRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }


}
