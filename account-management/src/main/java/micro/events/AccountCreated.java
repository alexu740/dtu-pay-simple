package micro.events;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import micro.aggregate.AccountId;
import micro.service.CorrelationId;
import boilerplate.Event;


@EqualsAndHashCode(callSuper = false)
public class AccountCreated extends Event {
	private static final long serialVersionUID = -1599019626118724482L;
	public AccountId accountId;
    public String firstName;
    public String lastName;
    public String cpr;
    public String bankAccount;
    public boolean isCustomerAccountType; 

    public AccountId getAccountId() {
        return accountId;
    }

    public AccountCreated(AccountId accountId, CorrelationId correlationId) {
        super("AccountRegistered", new Object[] { accountId.getUuid(), correlationId });
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void setAccountId(AccountId accountId) {
        this.accountId = accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public boolean isCustomerAccountType() {
        return isCustomerAccountType;
    }

    public void setCustomerAccountType(boolean isCustomerAccountType) {
        this.isCustomerAccountType = isCustomerAccountType;
    }
}
