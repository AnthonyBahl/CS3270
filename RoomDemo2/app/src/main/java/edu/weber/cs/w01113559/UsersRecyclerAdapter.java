package edu.weber.cs.w01113559;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.weber.cs.w01113559.db.User;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.userViewHolder> {

    private List<User> userList;
    //ToDO: Define variable to hold Interface

    //ToDo: Define and attach Interface for button action (Maybe done in the userfragment?). More info at 45:42 of part 1

    //ToDO: Define function that will take interface as a parameter so that we can work with it.


    public UsersRecyclerAdapter(List<User> userList) {
        this.userList = userList;
    }

    public void setUserList(List<User> list) {
            this.userList.clear();
            this.userList.addAll(list);
            notifyDataSetChanged();
    }

    /**
     * Take the data (single row) and build up the template for that row to be bound to.
     * @param parent
     * @param viewType
     * @return userViewHolder:
     */
    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_view, parent, false);

        return new userViewHolder(view);
    }

    /**
     * Take the template that we created (in onCreateViewHolder), now tie it together with the data source.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {

        User user = userList.get(position);

        if (user != null) {
            holder.item = user;
            holder.tv1.setText(user.getFirstName());
            holder.tv2.setText(user.getLastName());
            holder.itemRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToDO: Do something when they click. mCallback.
                }
            });
        }

    }

    /**
     * How many View Holders are there?
     * @return
     */
    @Override
    public int getItemCount() {
        return userList.size();
    }

    // ViewHolders HOLD the UI of an *individual* item in the list.
    /**
     * Inner Class to define what a View Holder is for our data
     */
    public class userViewHolder extends RecyclerView.ViewHolder {

        public View itemRoot;
        public TextView tv1, tv2;
        public User item;

        public userViewHolder(View view) {
            // ToDo: This could be a good discussion topic. What does Super even do?
            super(view);

            itemRoot = view;

            tv1 = itemRoot.findViewById(R.id.tvLine1);
            tv2 = itemRoot.findViewById(R.id.tvLine2);
        }
    }
}