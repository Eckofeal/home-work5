package bank;

import address.Address;
import bank.employee.Employee;
import bank.organization.Organization;
import bank.organization.TaxPayable;
import bankoperation.сlient.Client;
import bankoperation.*;
import human.Human;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class BankSystem extends Organization implements Findable {

    private Bank[] banks;

    public BankSystem(String name, Address address, LocalDateTime foundedAt) {
        super(name, address, foundedAt);
        banks = new Bank[0];
    }

    public void setBanks(Bank[] banks) {
        this.banks = banks;
    }

    public Bank[] getBanks() {
        return banks;
    }

    public void addBank(Bank bank) {
        if (bank == null) {
            return;
        }
        if (indexOfBank(bank) == -1) {
            banks = addAlgorithm(bank);
        } else {
            System.out.println("Bank already exist.");
        }
    }

    public void removeBank(Bank bank) {
        if (bank == null || banks.length == 0) {
            return;
        }
        banks = removeAlgorithm(bank);
    }

    private Bank[] addAlgorithm(Bank bank) {
        if (bank == null) {
            return banks;
        }
        Bank[] result;
        if (banks == null) {
            result = new Bank[1];
            result[0] = bank;
        } else {
            result = new Bank[banks.length + 1];
            result = unionAlgorithm(banks, result, bank);
        }
        return result;
    }

    private Bank[] removeAlgorithm(Bank bank) {
        Bank[] result = new Bank[banks.length - 1];
        int index = indexOfBank(bank);
        for (int i = 0, j = 0; i < banks.length; i++, j++) {
            if (i == index) {
                j--;
            } else {
                result[j] = banks[i];
            }
        }
        return result;
    }

    private Bank[] unionAlgorithm(Bank[] copyThis, Bank[] intoThis, Bank thenInsertThis) {
        for (int i = 0; i < intoThis.length; i++) {
            if (i != intoThis.length - 1) {
                intoThis[i] = copyThis[i];
            } else {
                intoThis[i] = thenInsertThis;
            }
        }
        return intoThis;
    }

    private int indexOfBank(Bank bank) {
        int result = -1;
        if (banks.length != 0) {
            int flag = 0;
            for (int i = 0; i < banks.length; i++) {
                flag++;
                if (banks[i].equals(bank)) {
                    result = i;
                    return result;
                }
            }
            if (flag == banks.length) {
                result = -1;
            }
        } else {
            result = -1;
        }
        return result;
    }

    /*public BankOperation[] searchOperation(Human human) {
        BankOperation[] result = null;
        if (human == null) {
            return null;
        }
        if (banks != null && banks.length > 0) {
            result = new BankOperation[0];
            for (Bank element : banks) {
                result = union(result, element.findOperation(human));
            }
        }
        return result;
    }*/

    @Override
    public BankOperation[] find(Human human) {
        BankOperation[] result = null;
        if (human == null) {
            return null;
        }
        if (banks != null && banks.length > 0) {
            result = new BankOperation[0];
            for (Bank element : banks) {
                result = union(result, element.find(human));
            }
        }
        return result;
    }

    private BankOperation[] union(BankOperation[] base, BankOperation[] copied) {
        if (copied == null || copied.length == 0) {
            return base;
        }
        BankOperation[] result;
        if (base == null) {
            result = copied;
        } else {
            result = new BankOperation[base.length + copied.length];
            result = unionAlgorithm(base, copied, result);
        }
        return result;
    }

    private BankOperation[] unionAlgorithm(BankOperation[] unionThis, BankOperation[] withThis, BankOperation[] andPutHere) {
        for (int i = 0; i < andPutHere.length; i++) {
            if (i < unionThis.length) {
                andPutHere[i] = unionThis[i];
            } else {
                andPutHere[i] = withThis[i - unionThis.length];
            }
        }
        return andPutHere;
    }

    public void searchForCreditType(String moneyType) {
        System.out.printf("%-60s%s%s", "\n", "CREDIT SEARCH RESULT:", "\n");
        int flag = 0;
        int otherBanks = 0;
        for (Bank element : banks) {
            if (element instanceof CreditBank) {
                CreditBank creditBank = (CreditBank) element;
                CreditType[] creditTypes = creditBank.findCreditType(moneyType);
                if (creditTypes != null && creditTypes.length != 0) {
                    for (CreditType creditTypeElement : creditTypes) {
                        System.out.print("Bank \"" + element.getName() + "\" : ");
                        creditTypeElement.print();
                    }
                } else {
                    flag++;
                }
            } else {
                otherBanks++;
            }
        }
        if (flag == banks.length - otherBanks) {
            System.out.println("No credits found for your request.");
        }
    }

    public void searchForCreditType(String moneyType, double moneyAmount) {
        System.out.printf("%-60s%s%s", "\n", "CREDIT SEARCH RESULT:", "\n");
        int flag = 0;
        int otherBanks = 0;
        for (Bank element : banks) {
            if (element instanceof CreditBank) {
                CreditBank creditBank = (CreditBank) element;
                CreditType[] creditTypes = creditBank.findCreditType(moneyType, moneyAmount);
                if (creditTypes != null && creditTypes.length != 0) {
                    for (CreditType creditTypeElement : creditTypes) {
                        System.out.print("Bank \"" + element.getName() + "\" : ");
                        creditTypeElement.print();
                    }
                } else {
                    flag++;
                }
            } else {
                otherBanks++;
            }
        }
        if (flag == banks.length - otherBanks) {
            System.out.println("No credits found for your request.");
        }
    }

    public static void exchangeRates() {
        System.out.printf("%-60s%s%s", "\n", "EXCHANGE RATES:", "\n");
        System.out.printf("%15s %8s%n", "BUY", "SELL");
        System.out.printf("%-12s%-8s%-6s%s%n", "1 USD", Exchangable.USD_BUY, Exchangable.USD_SELL, "BYN");
        System.out.printf("%-12s%-8s%-6s%s%n", "1 EURO", Exchangable.EURO_BUY, Exchangable.EURO_SELL, "BYN");
        System.out.printf("%-12s%-8s%-6s%s%n", "1 RUB", Exchangable.RUB_BUY, Exchangable.RUB_SELL, "BYN");
    }

    @Override
    public void payTax() {
        System.out.printf("%-60s%s%s", "\n", "BANK SYSTEM TAX:", "\n");
        System.out.println(super.getName() + " bank system: ");
        double usdTax = 0;
        double eurTax = 0;
        double rubTax = 0;
        double bynTax = 0;
        for (Bank element : banks) {
            if (element.getUsd().getAmount() > 0) {
                usdTax += element.getUsd().getAmount() * TaxPayable.taxPercent;
                element.getUsd().setAmount(element.getUsd().getAmount() - usdTax);
            }
            if (element.getEur().getAmount() > 0) {
                eurTax += element.getEur().getAmount() * TaxPayable.taxPercent;
                element.getEur().setAmount(element.getEur().getAmount() - eurTax);
            }
            if (element.getRub().getAmount() > 0) {
                rubTax += element.getRub().getAmount() * TaxPayable.taxPercent;
                element.getRub().setAmount(element.getRub().getAmount() - rubTax);
            }
            if (element.getByn().getAmount() > 0) {
                bynTax += element.getByn().getAmount() * TaxPayable.taxPercent;
                element.getByn().setAmount(element.getByn().getAmount() - bynTax);
            }
        }
        System.out.println("USD tax paid: " + usdTax);
        System.out.println("EURO tax paid: " + eurTax);
        System.out.println("RUB tax paid: " + rubTax);
        System.out.println("BYN tax paid: " + bynTax);
    }

    @Override
    public void print() {
        System.out.printf("%-60s%s%s", "\n", "BANK SYSTEM INFORMATION:", "\n");
        super.print();
        System.out.println("Number of banks in system: " + Bank.count);
        System.out.println("Number of employees: " + Employee.count);
        System.out.println("Credits issued: " + Credit.count);
        System.out.println("Mortgage issued: " + Mortgage.count);
        System.out.println("Number types of credits in banks: " + CreditType.count);
        System.out.println("Number of bank clients: " + Client.count);
    }

    @Override
    public String toString() {
        return "Class BankSystem [name = " + getName() + ", address = " + getAddress() + ", foundedAt = "
                + getFoundedAt().getDayOfMonth() + "." + getFoundedAt().getMonth() + "."
                + getFoundedAt().getYear() + ", USDBUY = " + Exchangable.USD_BUY + ", USDSELL = " + Exchangable.USD_SELL + ", EUROBUY = " +
                Exchangable.EURO_BUY + ", EUROSELL = " + Exchangable.EURO_SELL + ", RUBBUY = " + Exchangable.RUB_BUY +
                ", RUBSELL = " + Exchangable.RUB_SELL + ", banks = " + banks + "]";
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        BankSystem bankSystem = (BankSystem) object;
        return Arrays.equals(banks, bankSystem.getBanks())
                && getAddress().equals(bankSystem.getAddress())
                && getFoundedAt().equals(bankSystem.getFoundedAt())
                && (getName() != null && getName().equals(bankSystem.getName()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(Exchangable.USD_BUY, Exchangable.USD_SELL, Exchangable.EURO_SELL, Exchangable.EURO_BUY,
                Exchangable.RUB_BUY, Exchangable.RUB_SELL, banks, getName(),
                getFoundedAt(), getAddress());
    }
}
