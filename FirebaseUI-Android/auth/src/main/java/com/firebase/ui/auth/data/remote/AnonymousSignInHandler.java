package com.firebase.ui.auth.data.remote;

import android.app.Application;
import android.content.Intent;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FlowParameters;
import com.firebase.ui.auth.data.model.Resource;
import com.firebase.ui.auth.data.model.User;
import com.firebase.ui.auth.ui.HelperActivityBase;
import com.firebase.ui.auth.viewmodel.ProviderSignInBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;


@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class AnonymousSignInHandler extends SingleProviderSignInHandler<FlowParameters> {

    @VisibleForTesting
    public FirebaseAuth mAuth;

    public AnonymousSignInHandler(Application application) {
        super(application, AuthUI.ANONYMOUS_PROVIDER);
    }

    @Override
    protected void onCreate() {
        mAuth = getAuth();
    }

    @Override
    public void startSignIn(@NonNull FirebaseAuth auth,
                            @NonNull HelperActivityBase activity,
                            @NonNull String providerId) {
        setResult(Resource.forLoading());

        // Calling signInAnonymously() will always return the same anonymous user if already
        // available. This is enforced by the client SDK.
        mAuth.signInAnonymously()
                .addOnSuccessListener(result -> setResult(Resource.forSuccess(initResponse(
                        result.getAdditionalUserInfo().isNewUser()))))
                .addOnFailureListener(e -> setResult(Resource.forFailure(e)));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {}

    private IdpResponse initResponse(boolean isNewUser) {
        return new IdpResponse.Builder(
                new User.Builder(AuthUI.ANONYMOUS_PROVIDER, null)
                        .build())
                .setNewUser(isNewUser)
                .build();
    }

    // TODO: We need to centralize the auth logic. ProviderSignInBase classes were originally
    // meant to only retrieve remote provider data.
    private FirebaseAuth getAuth() {
        return AuthUI.getInstance(getArguments().appName).getAuth();
    }
}
