package com.firebase.uidemo.database.realtime;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.auth.util.ui.ImeHelper;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.uidemo.R;
import com.firebase.uidemo.database.ChatHolder;
import com.firebase.uidemo.databinding.ActivityChatBinding;
import com.firebase.uidemo.util.SignInResultNotifier;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Class demonstrating how to setup a {@link RecyclerView} with an adapter while taking sign-in
 * states into consideration. Also demonstrates adding data to a ref and then reading it back using
 * the {@link FirebaseRecyclerAdapter} to build a simple chat app.
 * <p>
 * For a general intro to the RecyclerView, see <a href="https://developer.android.com/training/material/lists-cards.html">Creating
 * Lists</a>.
 */
@SuppressLint("RestrictedApi")
public class RealtimeDbChatActivity extends AppCompatActivity
        implements FirebaseAuth.AuthStateListener {
    private static final String TAG = "RealtimeDatabaseDemo";

    /**
     * Get the last 50 chat messages.
     */
    @NonNull
    protected final Query sChatQuery =
            FirebaseDatabase.getInstance().getReference().child("chats").limitToLast(50);

    private ActivityChatBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.messagesList.setHasFixedSize(true);
        mBinding.messagesList.setLayoutManager(new LinearLayoutManager(this));

        ImeHelper.setImeOnDoneListener(mBinding.messageEdit, () -> onSendClick());

        mBinding.sendButton.setOnClickListener(view -> onSendClick());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isSignedIn()) {
            attachRecyclerViewAdapter();
        }
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth auth) {
        mBinding.sendButton.setEnabled(isSignedIn());
        mBinding.messageEdit.setEnabled(isSignedIn());

        if (isSignedIn()) {
            attachRecyclerViewAdapter();
        } else {
            Toast.makeText(this, R.string.signing_in, Toast.LENGTH_SHORT).show();
            auth.signInAnonymously().addOnCompleteListener(new SignInResultNotifier(this));
        }
    }

    private boolean isSignedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    private void attachRecyclerViewAdapter() {
        final RecyclerView.Adapter adapter = newAdapter();

        // Scroll to bottom on new messages
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mBinding.messagesList.smoothScrollToPosition(adapter.getItemCount());
            }
        });

        mBinding.messagesList.setAdapter(adapter);
    }

    public void onSendClick() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String name = "User " + uid.substring(0, 6);

        onAddMessage(new Chat(name, mBinding.messageEdit.getText().toString(), uid));

        mBinding.messageEdit.setText("");
    }

    @NonNull
    protected RecyclerView.Adapter newAdapter() {
        FirebaseRecyclerOptions<Chat> options =
                new FirebaseRecyclerOptions.Builder<Chat>()
                        .setQuery(sChatQuery, Chat.class)
                        .setLifecycleOwner(this)
                        .build();

        return new FirebaseRecyclerAdapter<Chat, ChatHolder>(options) {
            @Override
            public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ChatHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.message, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull ChatHolder holder, int position, @NonNull Chat model) {
                holder.bind(model);
            }

            @Override
            public void onDataChanged() {
                // If there are no chat messages, show a view that invites the user to add a message.
                mBinding.emptyTextView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
            }
        };
    }

    protected void onAddMessage(@NonNull Chat chat) {
        sChatQuery.getRef().push().setValue(chat, (error, reference) -> {
            if (error != null) {
                Log.e(TAG, "Failed to write message", error.toException());
            }
        });
    }
}
