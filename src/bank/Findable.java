package bank;

import bankoperations.BankOperation;
import human.Human;

public interface Findable {

    BankOperation[] find(Human human);

}
