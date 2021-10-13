package bank;

import bankoperation.BankOperation;
import human.Human;

public interface Findable {

    BankOperation[] find(Human human);

}
