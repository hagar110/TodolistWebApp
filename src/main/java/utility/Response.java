package utility;

public class Response {
   private String message;
  private  int statusCode;
    private Object itemsToBeReturned;

    public Response(){}

    public Response(String message, int statusCode, Object itemsToBeReturned) {
        this.message = message;
        this.statusCode = statusCode;
        this.itemsToBeReturned = itemsToBeReturned;
    }

    public Response(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getItemsToBeReturned() {
        return itemsToBeReturned;
    }

    public void setItemsToBeReturned(Object itemsToBeReturned) {
        this.itemsToBeReturned = itemsToBeReturned;
    }
}
