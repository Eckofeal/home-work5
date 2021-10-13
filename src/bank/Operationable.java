package bank;

import bankoperations.BankOperation;

public interface Operationable {

    void add(BankOperation operation);

    void remove(BankOperation operation);

}
