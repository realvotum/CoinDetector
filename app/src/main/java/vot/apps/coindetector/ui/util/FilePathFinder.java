package vot.apps.coindetector.ui.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Lap on 2017-08-22.
 */

public class FilePathFinder {

    private Uri mUri;
    private Context mContext;

    public FilePathFinder(Context context, Uri uri){
        this.mContext = context;
        this.mUri = uri;
    }

    public String getRealPathFromUri() {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = mContext.getContentResolver().query(mUri, proj, null, null,
                    null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
