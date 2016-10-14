package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.TimeValidation;

public class ReminderTime {

        public static final String MESSAGE_REMINDERTIME_CONSTRAINTS = "Task reminder can only be in date format";
        public static final String MESSAGE_REMINDERTIME_INVALID = "reminder time has passed";
        public final String value;

        /**
         * Validates given reminder.
         *
         * @throws IllegalValueException
         *             if given reminder string is invalid.
         */
        public ReminderTime(String time) throws IllegalValueException {
            assert time != null;
            if (!isValidReminderTime(time)) {
                throw new IllegalValueException(MESSAGE_REMINDERTIME_CONSTRAINTS);
            }
            if (time =="")
                time = TimeValidation.TimeFormatToday();
            this.value = time;
        }

        /**
         * Returns true if a given string is a valid task reminder.
         */
        public static boolean isValidReminderTime(String test) {
            if ((TimeValidation.validate(test))|| (test ==""))
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
