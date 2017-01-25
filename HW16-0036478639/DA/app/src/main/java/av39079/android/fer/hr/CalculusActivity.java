package av39079.android.fer.hr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CalculusActivity extends AppCompatActivity {

    public static final String EXTRAS_SUM = "sum";
    public static final String EXTRAS_OPERATION = "operation";

    private TextView tvLabel;

    private Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);

        tvLabel = (TextView) findViewById(R.id.tvLabel);
        btnReturn = (Button) findViewById(R.id.btnReturn);

        Bundle mapa = getIntent().getExtras();
        double valA = mapa.getDouble(FormActivity.EXTRAS_VALA, 0.0);
        double valB = mapa.getDouble(FormActivity.EXTRAS_VALB, 0.0);
        String operation = mapa.getString(FormActivity.EXTRAS_OPERATION);
        double result=Double.NaN;
        String exception="(no exception)";
        try {
            switch (operation) {
                case "+":
                    result = valA+valB;
                    break;
                case "-":
                    result = valA-valB;
                    break;
                case "*":
                    result= valA*valB;
                    break;
                case "/":
                    result= valA/valB;
                    break;
            }
        } catch (Exception e) {
            exception = e.getMessage();
        }

        tvLabel.setText("Operation result: " + String.valueOf(result));
        final double res = result;
        final String exc = exception;
        btnReturn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent();
                        i.putExtra(EXTRAS_SUM, res);
                        i.putExtra(EXTRAS_OPERATION , exc);
                        setResult(RESULT_OK, i);
                        finish();
                    }
                });


    }


}
