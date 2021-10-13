package bank;

import bankoperation.BankOperation;

public interface Operationable {

    void add(BankOperation operation);

    void remove(BankOperation operation);

}
