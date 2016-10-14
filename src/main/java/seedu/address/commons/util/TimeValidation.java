package seedu.address.commons.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeValidation {

    private static Pattern pattern;
    private static Matcher matcher;
    public static final String TIME_VALIDATION_REGEX = "([01]?[0-9]|2[0-3]):([0-5][0-9])";

    public void DateValidator() {

    }

    /**
     * Validate time format with regular expression
     * 
     * @param time
     * @return true valid date format, false invalid date format
     */
    public static boolean validate(String time) {

        pattern = Pattern.compile(TIME_VALIDATION_REGEX);
        matcher = pattern.matcher(time);

        if (matcher.matches()) {

            matcher.reset();

            if (matcher.find()) {
                int hour = Integer.parseInt(matcher.group(1));
                int min = Integer.parseInt(matcher.group(2));

                if (((hour < 24) && (min < 60)))
                    return true;

            }
        }
        return false;
    }
}
