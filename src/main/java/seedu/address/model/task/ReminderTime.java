package seedu.address.model.task;

import java.text.ParseException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateValidation;

public class ReminderTime {

        public static final String MESSAGE_REMINDER_CONSTRAINTS = "Task reminder can only be in date format";
        public static final String MESSAGE_REMINDER_INVALID = "reminder time has passed";
        public final String value;

        /**
         * Validates given reminder.
         *
         * @throws IllegalValueException
         *             if given reminder string is invalid.
         */
        public ReminderTime(String date) throws IllegalValueException {
            assert date != null;
            String time;
            String[] parts;
            
            
            this.value = date;
        }

        /**
         * Returns true if a given string is a valid task reminder.
         */
        public static boolean isValidReminder(String test) {
            if ((DateValidation.validate(test))|| (test ==""))
                return true;
            else
                return false;
        }

        @Override
        public String toString() {
            return value;
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof Reminder // instanceof handles nulls
                            && this.value.equals(((Reminder) other).value)); // state
                                                                             // check
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

    }
