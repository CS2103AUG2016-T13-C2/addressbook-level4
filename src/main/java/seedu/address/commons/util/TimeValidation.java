package seedu.address.commons.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeValidation {

    private static Pattern pattern;
    private static Matcher matcher;
    public static final String DATE_VALIDATION_REGEX = ("([01]?[0-9]|2[0-3]):([0-5][0-9])");
}
