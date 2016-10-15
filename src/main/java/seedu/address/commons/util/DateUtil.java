package seedu.address.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtil {
    private static List<SimpleDateFormat> DATE_FORMATS;
    
    public DateUtil() {
        DATE_FORMATS = new ArrayList<>();
        
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy h:mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d/MM/yyyy h:mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy h.mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d/MM/yyyy h.mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy HHmm"));
        DATE_FORMATS.add(new SimpleDateFormat("d/MM/yyyy HHmm"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy HH:mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d/MM/yyyy HH:mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy HH.mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d/MM/yyyy HH.mm"));
    }
    
    public static boolean validate(String date) {
        Date validDate;
        
        for (SimpleDateFormat sdf : DATE_FORMATS) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    return true;
                }
            } catch (ParseException e) {
                continue;
            }
        }
        
        return false;
    }
    
    public static Date parseDate(String date) {
        Date validDate;
        
        for (SimpleDateFormat sdf : DATE_FORMATS) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    return validDate;
                }
            } catch (ParseException e) {
                continue;
            }
        }
        
        return null;
    }
}
