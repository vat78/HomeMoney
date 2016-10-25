package ru.vat78.homeMoney.model;

public final class Defenitions {

    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final int USER_NAME_MIN = 3;
    public static final int USER_NAME_MAX = 50;
    public static final String USER_NAME_TEMPLATE = "^[a-zA-Z0-9]+$";
    public static final int PASSWORD_MIN = 3;
    public static final int PASSWORD_MAX = 250;
    public static final int CURRNECY_SYMBOL_MIN = 1;
    public static final int CURRNECY_SYMBOL_MAX = 5;
    public static final int DISCRIMINATOR_LENGTH = 10;
    public static final int DICTIONARY_NAME_MIN = 2;
    public static final int DICTIONARY_NAME_MAX = 250;
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    public final static class GROUPS{
        public static final String OBJECTS = "objects";
        public static final String DICTIONARIES = "dictionaries";
        public static final String ACCOUNTS = "accounts";
        public static final String TRANSACTIONS = "transactions";
    }

    public final static class TABLES{


        public static final String ACCOUNTS = "Account";
        public static final String BILLS = "Bill";
        public static final String CATEGORIES = "Category";
        public static final String CONTRACTORS = "Contractor";
        public static final String CREDIT_ACCOUNTS = "CreditAccount";
        public static final String CASH_ACCOUNTS = "CashAccount";
        public static final String CURRENCY = "Currency";
        public static final String PAYMENTS = "Payment";
        public static final String PERSONS = "Person";
        public static final String TAGS = "Tag";
        public static final String TRANSACTIONS = "Transaction";
        public static final String TRANSFERS = "Transfer";
        public static final String USERS = "users";

        public static final String COLUMNS = "columns";
        public static final String TABLE_SETTINGS = "tables";
    }

    public final static class FIELDS{

        public static final String ID = "id";
        public static final String CREATE_ON = "createdOn";
        public static final String CREATE_BY = "createdBy";
        public static final String MODIFY_ON = "modifiedOn";
        public static final String MODIFY_BY = "modifiedBy";
        public static final String GROUP = "ogroup";
        public static final String TYPE = "otype";

        public static final String NAME = "name";
        public static final String SEARCH_NAME = "searchingName";
        public static final String PARENT_ID = "parent";
        public static final String FULL_NAME = "fullName";
        public static final String SYMBOL = "symbol";

        public static final String ACTIVE = "active";
        public static final String CURRENCY = "currency";
        public static final String CREDIT_RATE = "creditRate";
        public static final String OPENING_DATE = "openingDate";
        public static final String ORGANIZATION = "creditOrganization";
        public static final String ACCOUNT_TYPE = "account_type";

        public static final String PASSWORD = "password";
        public static final String IS_ADMIN = "admin";

        public static final String CONTRACTOR_ID = "contractor";
        public static final String BILL_ID = "bill";
        public static final String DATE = "date";
        public static final String ACCOUNT_ID = "account";
        public static final String SUM = "sum";
        public static final String OPERATION_TYPE = "operation_type";
        public static final String OPERATION = "operation";
        public static final String CORRESPONDING_ACCOUNT = "corrAccount";
        public static final String CONVERSION = "conversion";

        public static final String CATEGORY_ID = "category";
        public static final String TAG_ID = "tag";
        public static final String PERSON_ID = "person";
        public static final String PRICE = "price";
        public static final String QUANTITY = "quantity";
        public static final String COMMENTS = "comments";

        public static final String TABLE = "table";
        public static final String USER = "user";
        public static final String VISIBLE = "visible";
        public static final String CAPTION = "caption";
        public static final String NUM = "num";

        public static final String SORT_COLUMN = "sortColumn";
        public static final String SORT_ORDER = "sortOrder";
        public static final String PAGE_SIZE = "pageSize";
        public static final String ADD_BTN = "add_btn";
    }

    public final static class ACCOUNTS_TYPE {

        public static final String CREDIT = "credit";
        public static final String CASH = "cash";
    }

    public final static class TRANSACTION_TYPE {

        public static final String BILL = "bill";
        public static final String TRANSFER = "transfer";

    }

    public final static class OPERATION_TYPE {

        public static final int INCOME = 1;
        public static final int EXPENSE = -1;

    }
}
