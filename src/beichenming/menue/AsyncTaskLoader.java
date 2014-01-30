package beichenming.menue;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;

import beichenming.manger.Sourcemanger;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class AsyncTaskLoader extends AsyncTask<IAsyncCallback, Integer, Boolean> {
	 
    // ===========================================================
    // Fields
    // ===========================================================
    
  	
    IAsyncCallback[] _params;
    public static int count;
    // ===========================================================
    // Inherited Methods
    // ===========================================================
 
    @Override
  	protected void onPreExecute() {
  		// TODO Auto-generated method stub
    	
    	
    	Log.d("新线程","onPreExecute");
  		super.onPreExecute();
  	}
    
    
    @Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
    	
		super.onProgressUpdate(values);
	}


	@Override
    protected Boolean doInBackground(IAsyncCallback... params) {
        this._params = params;
         count = params.length;
        for(int i = 0; i < count; i++){
            params[i].workToDo();
            publishProgress((int) ((i / (float) count) * 100));
        }
    	Log.d("新线程","doInBackground");
        return true;
    }
 


	@Override
    protected void onPostExecute(Boolean result) {
         count = this._params.length;
        for(int i = 0; i < count; i++){
            this._params[i].onComplete();
        }
        
        Log.d("新线程","onPostExecute");
    }
}