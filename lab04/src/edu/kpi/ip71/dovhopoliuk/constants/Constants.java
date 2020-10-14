package edu.kpi.ip71.dovhopoliuk.constants;

public class Constants {

    public static class Common {

        public static final int INTEGER_ZERO = 0;
        public static final int INTEGER_ONE = 1;

        public static final String INTERRUPTED_MESSAGE_PATTERN = "Thread %s received interrupt signal. Stopping...\n";
    }

    public static class ProducerConsumer {

        public static class Producer {

            public final static String ADDITIONAL_INFO_PATTERN = "Generated record #%s of %s by %s";
            public final static String RECORD_SUBMITTED_MESSAGE_PATTERN = "%s submitted record. Details: %s\n";
        }

        public static class Consumer {

            public final static String RECORD_RECEIVED_MESSAGE_PATTERN = "%s received record. Details: %s\n";
        }

        public static final String START_MESSAGE = "\n-== Starting solution of Producer/Consumer problem ==-";

        public static final String PRODUCERS_THREAD_GROUP_NAME = "Producers Thread Group";
        public static final String CONSUMERS_THREAD_GROUP_NAME = "Consumers Thread Group";

        public static final String PRODUCERS_THREAD_NAME_PREFIX = "Producer #";
        public static final String CONSUMERS_THREAD_NAME_PREFIX = "Consumer #";

        public static final String SUBMITTED_TASKS_QUANTITY_MESSAGE = "Submitted tasks quantity: ";
        public static final String PROCESSED_TASKS_QUANTITY_MESSAGE = "Processed tasks quantity: ";
    }

    public static class ReaderWriter {

        public static class Reader {

            public static final String MESSAGE_RECEIVED_PATTERN = "%s received message. Details: %s\n";
        }

        public static class Writer {

            public static final String MESSAGE_TEMPLATE = "Message #%s of %s generated by %s";
            public final static String MESSAGE_SUBMITTED_PATTERN = "%s submitted message. Details: %s\n";
            public final static String FINISHED_MESSAGE_PATTERN = "Thread %s finished work successfully. Stopping...\n";
        }

        public static final String START_MESSAGE = "\n-== Starting solution of Reader/Writer problem ==-";

        public static final String WRITERS_THREAD_GROUP_NAME = "Writers Thread Group";
        public static final String READERS_THREAD_GROUP_NAME = "Readers Thread Group";

        public static final String WRITERS_THREAD_NAME_PREFIX = "Writer #";
        public static final String READERS_THREAD_NAME_PREFIX = "Reader #";
    }

    public static class DiningPhilosophers {

        public static class Philosopher {

            public final static String EAT_MESSAGE_PATTERN = "%s started eating with %s and %s forks\n";
            public final static String THINK_MESSAGE_PATTERN = "%s started thinking\n";
        }

        public static final String START_MESSAGE = "\n-== Starting solution of Dining Philosophers problem ==-";

        public static final String PHILOSOPHERS_THREAD_GROUP_NAME = "Philosophers Thread Group";
        public static final String PHILOSOPHERS_THREAD_NAME_PREFIX = "Philosopher #";

        public static final String FORK_NAME_PREFIX = "Fork #";
    }

    public static class SleepingBarber {

        public static class Barber {

            public static final String FELL_ASLEEP_MESSAGE_PATTERN = "%s fell asleep\n";
            public static final String TOOK_CLIENT_MESSAGE_PATTERN = "%s took client\n";
            public static final String CUTTING_MESSAGE_PATTERN = "%s is cutting client...\n";
            public static final String CUT_MESSAGE_PATTERN = "%s cut client...\n";
        }

        public static class Client {

            public static final String ENTERED_MESSAGE_PATTERN = "%s entered barber shop\n";
            public static final String TOOK_PLACE_MESSAGE_PATTERN = "%s took place\n";
            public static final String WAKED_UP_BARBER_MESSAGE_PATTERN = "%s waked up barber\n";
            public static final String SERVICED_MESSAGE_PATTERN = "%s serviced by barber\n";
            public static final String LEFT_MESSAGE_PATTERN = "%s left barber shop\n";
        }

        public static final String START_MESSAGE = "\n-== Starting solution of Sleeping Barber problem ==-";

        public static final String BARBERS_THREAD_GROUP_NAME = "Barbers Thread Group";
        public static final String CLIENTS_THREAD_GROUP_NAME = "Clients Thread Group";

        public static final String BARBERS_THREAD_NAME_PREFIX = "Barber #";
        public static final String CLIENTS_THREAD_NAME_PREFIX = "Client #";

        public static final String MAX_QUEUE_SIZE_MESSAGE = "Max queue length during simulation: %s\n";
    }
}
