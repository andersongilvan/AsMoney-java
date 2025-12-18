package AsMoney.modules.transcation.exceptions;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException() {
        super("Transaction not found");
    }
}
