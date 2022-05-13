package sources;

public enum ConectivityStatus {
    NA("N/A"),
    READY("Ready"),
    CONNECTED("Connected"),
    NOT_CONNECTED("Not Connected"),
    SENDING("Sending"),
    RECEIVING("Receiving"),
    DONE_RECV("Done Receiving"),
    DONE_SENT("Done Sending");


    private final String name;
    ConectivityStatus(String name) {
        this.name = name;
    }

}
