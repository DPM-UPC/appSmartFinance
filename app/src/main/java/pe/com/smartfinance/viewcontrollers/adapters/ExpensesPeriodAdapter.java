package pe.com.smartfinance.viewcontrollers.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.OperationModels.Operation;
import pe.com.smartfinance.utils.DateFormatter;

public class ExpensesPeriodAdapter
        extends RecyclerView.Adapter<ExpensesPeriodAdapter.ViewHolder> {

    private List<Operation> operations;

    public ExpensesPeriodAdapter(List<Operation> operations) {
        this.operations = operations;
    }

    @NonNull
    @Override
    public ExpensesPeriodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_expense, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesPeriodAdapter.ViewHolder holder, int position) {
        Operation operation = operations.get(position);
        holder.updateViewFrom(operation);
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public ExpensesPeriodAdapter setOperations(List<Operation> operations) {
        this.operations = operations;
        return this;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dateExpenseTextView;
        private TextView categoryExpenseTextView;
        private TextView tagExpenseTextView;
        private TextView amountExpenseTextView;
        private ConstraintLayout expenseLayout;
        private Operation operation;
        public ViewHolder(View itemView) {
            super(itemView);
            dateExpenseTextView = (TextView) itemView.findViewById(R.id.dateExpenseTextView);
            categoryExpenseTextView = (TextView) itemView.findViewById(R.id.categoryExpenseTextView);
            tagExpenseTextView = (TextView) itemView.findViewById(R.id.tagExpenseTextView);
            amountExpenseTextView = (TextView) itemView.findViewById(R.id.amountExpenseTextView);
            expenseLayout = (ConstraintLayout) itemView.findViewById(R.id.expenseLayout);
        }

        public void updateViewFrom(final Operation operation) {
            dateExpenseTextView.setText(DateFormatter.getFormatDate(operation.getCreationDate(), "dd-M-yyyy"));
            categoryExpenseTextView.setText(operation.getTag().getCategory().getDescription());
            tagExpenseTextView.setText(operation.getTag().getDescription());
            amountExpenseTextView.setText(operation.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
            this.operation = operation;
        }
    }
}
