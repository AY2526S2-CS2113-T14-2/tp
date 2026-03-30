package seedu.duke.data;
//@@author nicholaslauhy
/**
 * Immutable DTO for displaying archived monthly expense rows.
 */
public class ArchivedExpense {
    private final String name;
    private final String amount;
    private final String category;
    private final boolean isRecurring;

    /**
     * Creates an archived expense record.
     *
     * @param name Name of the expense.
     * @param amount Amount of the expense.
     * @param category Category of the expense.
     */
    public ArchivedExpense(String name, String amount, String category, boolean isRecurring) {
        assert name != null : "Name cannot be null";
        assert amount != null : "Amount cannot be null";
        assert category != null : "Category cannot be null";

        this.name = name;
        this.amount = amount;
        this.category = category;
        this.isRecurring = isRecurring;
    }

    /**
     * Returns the expense name.
     *
     * @return Expense name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the expense amount.
     *
     * @return Expense amount.
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Returns the expense category.
     *
     * @return Expense category.
     */
    public String getCategory() {
        return category;
    }
    /**
     * Returns whether this archived expense came from a recurring expense.
     *
     * @return {@code true} if recurring, {@code false} otherwise
     */
    public boolean isRecurring() {
        return isRecurring;
    }
}

