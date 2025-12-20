package AsMoney.modules.transcation.exceptions;



public class UnauthorizedTransactionAccessException extends RuntimeException {
    public UnauthorizedTransactionAccessException() {
        super("Access denied");
    }
}
