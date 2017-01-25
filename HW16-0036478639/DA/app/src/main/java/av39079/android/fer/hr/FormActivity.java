package av39079.android.fer.hr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class FormActivity extends AppCompatActivity {

    public static final String EXTRAS_VALA = "VALA";
    public static final String EXTRAS_VALB = "VALB";
    public static final String EXTRAS_OPERATION = "OPERATION";
    private TextView tvSum;
    private TextView tvException;
    private EditText etVariableA;
    private EditText etVariableB;
    private Button btnCall;
    private Button btnOpen;
    private Button btnSend;
    private RadioGroup radioOperationGroup;
    private RadioButton radioOperationButton;
    private String exception;
    private Double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form);

        tvSum = (TextView) findViewById(
                R.id.tvSum);
        tvException = (TextView) findViewById(
                R.id.tvException);
        etVariableA = (EditText) findViewById(
                R.id.etFirstVariable);
        etVariableB = (EditText) findViewById(
                R.id.etSecondVariable);
        Button btnCalculate = (Button) findViewById(
                R.id.btnCalculate);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnOpen = (Button) findViewById(R.id.btnView);

        radioOperationGroup = (RadioGroup) findViewById(R.id.radioOperation);

        btnCalculate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String varA = etVariableA.getText()
                                .toString();
                        String varB = etVariableB.getText()
                                .toString();

                        double valueA = 0;
                        try {
                            valueA = Double.parseDouble(varA);
                        } catch (Exception e) {

                        }
                        double valueB = 0;
                        try {
                            valueB = Double.parseDouble(varB);
                        } catch (Exception e) {

                        }
                        Intent i = new Intent(
                                FormActivity.this,
                                CalculusActivity.class);

                        i.putExtra(EXTRAS_VALA, valueA);
                        i.putExtra(EXTRAS_VALB, valueB);

                        i.putExtra(EXTRAS_OPERATION,getSelectedOperation());

                        startActivityForResult(i, 666);

                    }
                });

        btnCall.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(
                                Intent.ACTION_CALL,
                                Uri.parse(
                                        "tel:09912345567"));
                        startActivity(i);
                    }
                });

        btnOpen.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String varA = etVariableA.getText()
                                .toString();
                        String varB = etVariableB.getText()
                                .toString();

                        int valueA = 0;
                        try {
                            valueA = Integer.parseInt(varA);
                        } catch (Exception e) {

                        }
                        int valueB = 0;
                        try {
                            valueB = Integer.parseInt(varB);
                        } catch (Exception e) {

                        }

                        Intent i = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(
                                        "https://www.google.hr/?q="
                                                + valueA
                                                + "%2B"
                                                + valueB));
                        startActivity(i);
                    }
                });


        btnSend.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(result!=null && exception!=null) {
                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"ana.baotic@infinum.hr"});
                            email.putExtra(Intent.EXTRA_SUBJECT, "0036478639: dz report");
                            String exceptionText="";
                            if(!exception.equals("(no exception)") &&  !exception.isEmpty()) {
                                exceptionText="\nIzvodenje je bilo neuspjesno, uzrok: " + exception;
                            }
                            email.putExtra(Intent.EXTRA_TEXT, "Rezultat operacije "+
                                    getSelectedOperation()+" je "+result+exceptionText);
                            email.setType("message/rfc822");
                            startActivity(Intent.createChooser(email, "Choose an Email client :"));
                        }

                    }
                });

    }

    private String getSelectedOperation() {
        int selectedId = radioOperationGroup.getCheckedRadioButtonId();
        radioOperationButton = (RadioButton) findViewById(selectedId);
        return String.valueOf(radioOperationButton.getText());
    }

    @Override
    protected void onActivityResult(int requestCode,
            int resultCode, Intent data) {
        if (requestCode == 666 && resultCode == RESULT_OK
                && data != null) {

            Double res = data.getExtras()
                    .getDouble(CalculusActivity.EXTRAS_SUM,
                            Double.NaN);
            String exc = data.getExtras().getString(CalculusActivity.EXTRAS_OPERATION,"(no exception)");
            exception = exc;
            result = res;
            tvSum.setText(String.valueOf(res));
            tvException.setText("Exception: "+exc);
        }
    }
}
