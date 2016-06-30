package ru.vat78.homeMoney.model;

public final class Defenitions {

    public static final String DATE_FORMAT = "dd/mm/yyyy";
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

    public final static class TABLES{

        public static final String ACCOUNTS = "accounts";
        public static final String BILLS = "bills";
        public static final String CATEGORIES = "categories";
        public static final String CONTRACTORS = "contractors";
        public static final String CREDIT_ACCOUNTS = "credit_accounts";
        public static final String CURRENCY = "currency";
        public static final String PAYMENTS = "payments";
        public static final String PERSONS = "persons";
        public static final String TAGS = "tags";
        public static final String TRANSACTIONS = "transactions";
        public static final String TRANSFERS = "transfers";
        public static final String USERS = "users";

    }

    public final static class FIELDS{

        public static final String ID = "id";
        public static final String CREATE_ON = "createdOn";
        public static final String CREATE_BY = "createdBy";
        public static final String MODIFY_ON = "modifiedOn";
        public static final String MODIFY_BY = "modifiedBy";

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
        public static final String CORRESPONDING_ACCOUNT = "corresponding";
        public static final String CONVERSION = "conversion";

        public static final String CATEGORY_ID = "category";
        public static final String TAG_ID = "tag";
        public static final String PERSON_ID = "person";
        public static final String PRICE = "price";
        public static final String QUANTITY = "quantity";
        public static final String COMMENTS = "comments";

    }

    public final static class ACCOUNTS_TYPE {

        public static final String CREDIT = "credit";
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