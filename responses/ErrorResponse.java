package responses;

public class ErrorResponse implements Response {
    String response = "ERROR";
    String reason;

    public ErrorResponse(String reason) {
        this.reason = reason;
    }

}
