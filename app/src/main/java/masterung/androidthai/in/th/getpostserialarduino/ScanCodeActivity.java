package masterung.androidthai.in.th.getpostserialarduino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.physicaloid.lib.Physicaloid;
import com.physicaloid.lib.usb.driver.uart.ReadLisener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView zXingScannerView;
    private String resultString;
    private String tag = "11JanV1";
    private Physicaloid physicaloid;
    private int bandAnInt = 9600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        zXingScannerView = new ZXingScannerView(ScanCodeActivity.this);
        setContentView(zXingScannerView);

        physicaloid = new Physicaloid(ScanCodeActivity.this);
        physicaloid.setBaudrate(bandAnInt);


    }   // Main Method

    @Override
    protected void onResume() {
        super.onResume();

        zXingScannerView.setResultHandler(ScanCodeActivity.this);
        zXingScannerView.startCamera();

    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result result) {

        resultString = result.getText().toString();
        Log.d(tag, "result ==> " + resultString);

        if (resultString.length() != 0) {

            sentValueToArduino();

            zXingScannerView.stopCamera();
            finish();
        }

        zXingScannerView.resumeCameraPreview(ScanCodeActivity.this);

    }

    private void sentValueToArduino() {

        try {

            Log.d(tag, "Value Sent to Arduino ==> " + resultString);
            showMessage("Value Sent to Arduino ==> " + resultString);

//            resultString = "3";

            boolean b = physicaloid.open();
            showMessage("physicaloid.open ==> " + b);

            if (b) {

                physicaloid.addReadListener(new ReadLisener() {
                    @Override
                    public void onRead(int i) {

                        byte[] bytes = new byte[i];
                        physicaloid.read(bytes, i);


                    }
                });


            } else {
                showMessage("physicaloid Close");
            }


            byte[] bytes = resultString.getBytes();
            physicaloid.write(bytes, bytes.length);

        } catch (Exception e) {
            showMessage("Error ==> " + e);
        }




    }   // sentValue

    private void showMessage(String messageString) {

        Toast.makeText(ScanCodeActivity.this, messageString,
                Toast.LENGTH_LONG).show();

    }


}   // Main Class
