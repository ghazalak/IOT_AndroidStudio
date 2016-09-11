package Connection;

import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RequestTask extends AsyncTask<String, String, String> {
    Exception error;
    HttpDeviceConnection con;
    long DeviceId;
    int KeyIdx;
    public RequestTask(HttpDeviceConnection con, long DeviceId, int KeyIdx) {
        this.con = con;
        this.DeviceId = DeviceId;
        this.KeyIdx = KeyIdx;
    }

    public RequestTask() {

    }

    @Override
    protected String doInBackground(String[] uri) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
            } else{
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            cancel(true);
            error = e;
            } catch (Exception e) {
            error = e;
            cancel(true);
        }
        return responseString;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(con!=null)
        con.StatusChangedCallback(DeviceId, KeyIdx, result);
    }
    @Override
    protected void onCancelled(){
        super.onCancelled();
    }
}