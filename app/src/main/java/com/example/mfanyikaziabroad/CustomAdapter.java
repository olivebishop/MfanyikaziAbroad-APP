package com.example.mfanyikaziabroad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {
    private ArrayList<Job> jobList;
    private ArrayList<Job> filteredJobList;
    private Context context;
    private CustomItemClickListener customItemClickListener;

    public CustomAdapter(Context context,ArrayList<Job> jobArrayList,CustomItemClickListener customItemClickListener) {
        this.context = context;
        this.jobList = jobArrayList;
        this.filteredJobList = jobArrayList;
        this.customItemClickListener = customItemClickListener;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customItemClickListener.onItemClick(filteredJobList.get(myViewHolder.getAdapterPosition()),myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder viewHolder, int position) {
        viewHolder.title.setText(filteredJobList.get(position).getTitle());
        viewHolder.description.setText(filteredJobList.get(position).getDescription());
        viewHolder.type.setText(filteredJobList.get(position).getType());
        viewHolder.salary_range.setText(filteredJobList.get(position).getSalary_range());
    }

    @Override
    public int getItemCount() {
        return filteredJobList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    filteredJobList = jobList;

                } else {

                    ArrayList<Job> tempFilteredList = new ArrayList<>();

                    for (Job job : jobList) {

                        // search for user title
                        if (job.getTitle().toLowerCase().contains(searchString)) {

                            tempFilteredList.add(job);
                        }
                    }

                    filteredJobList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredJobList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredJobList = (ArrayList<Job>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView description;
        private TextView type;
        private TextView salary_range;
        public MyViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.job_title);
            description = view.findViewById(R.id.description);
            type = view.findViewById(R.id.type);
            salary_range = view.findViewById(R.id.salary_range);
        }
    }
}
