package tds.com.moviezlub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class networkError extends AppCompatActivity {
    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_network_error);
        ((ActionBar) Objects.requireNonNull(getSupportActionBar())).hide();
        ((Button) findViewById(R.id.retry)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                networkError.this.finish();
                networkError networkError = networkError.this;
                networkError.startActivity(new Intent(networkError, MainActivity.class));
            }
        });
    }
}

