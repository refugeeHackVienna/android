package app.iamin.iamin;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import app.iamin.iamin.util.EndpointUtils;

/**
 * AsyncTask that wraps an OkHttpRequest. Designed to log in a new user.
 */
public class LoginTask extends AsyncTask<Context, Void, Response> {
    private static final String TAG = "LoginTask";

    private String password;
    private String email;
    private Exception savedException;

    /**
     * Construct a new task with an email and the users password.
     */
    public LoginTask(String email, String password) {
        this.password = password;
        this.email = email;
    }

    @Override
    protected Response doInBackground(Context ... contexts) {
        String url = EndpointUtils.getEndpoint(contexts[0], EndpointUtils.TASK_LOGIN);
        Log.d(TAG, url);

        RequestBody formBody = new FormEncodingBuilder()
                .add("email", email)
                .add("password", password)
                .build();

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .url(url)
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            EndpointUtils.storeHeader(contexts[0], response.headers());
            return response;

        } catch (IOException e) {
            e.printStackTrace();
            cancel(true);
        }

        return null;
    }

    protected void onPostExecute(Response response) {
        // TODO: Do something with response
        Log.e(TAG, "Access-Token = " + response.headers().get("Access-Token"));
        Log.e(TAG, "Token-Type = " + response.headers().get("Token-Type"));
        Log.e(TAG, "Client = " + response.headers().get("Client"));
        Log.e(TAG, "Expiry = " + response.headers().get("Expiry"));
        Log.e(TAG, "Uid = " + response.headers().get("Uid"));
    }

    @Override
    protected void onCancelled() {
        // TODO: Do something with this.savedException
    }
}
