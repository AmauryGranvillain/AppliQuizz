package fr.diginamic.formation.monquizz.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.diginamic.formation.monquizz.R;
import fr.diginamic.formation.monquizz.model.Question;
import fr.diginamic.formation.monquizz.ui.fragments.QuestionListFragment;
import fr.diginamic.formation.monquizz.ui.fragments.QuestionListFragment.OnListQuestionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Question} and makes a call to the
 * specified {@link OnListQuestionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.ViewHolder> {

    private final List<Question> mValues;
    private final OnListQuestionListener mListener;

    public QuestionRecyclerViewAdapter(List<Question> items, QuestionListFragment.OnListQuestionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);
        holder.intitule.setText(mValues.get(position).intitule);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.onLongClickInteraction(holder.mItem);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final TextView mIdView;
        public final TextView intitule;
        public Question mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mIdView = (TextView) view.findViewById(R.id.item_number);
            intitule = (TextView) view.findViewById(R.id.intitulequestion);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + intitule.getText() + "'";
        }
    }
}
