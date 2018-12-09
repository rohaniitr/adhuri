package in.rohansarkar.adhuri.adhuri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.rohansarkar.adhuri.adhuri..LoginFragment;

public class VerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow();
        }
    }
}
