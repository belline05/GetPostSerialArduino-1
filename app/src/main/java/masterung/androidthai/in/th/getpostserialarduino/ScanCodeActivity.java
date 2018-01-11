package masterung.androidthai.in.th.getpostserialarduino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView zXingScannerView;
    private String resultString;
    private String tag = "11JanV1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        zXingScannerView = new ZXingScannerView(ScanCodeActivity.this);
        setContentView(zXingScannerView);


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
            zXingScannerView.stopCamera();
            finish();
        }

        zXingScannerView.resumeCameraPreview(ScanCodeActivity.this);

    }


}   // Main Class
