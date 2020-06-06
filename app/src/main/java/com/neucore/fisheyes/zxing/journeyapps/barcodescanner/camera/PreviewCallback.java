package com.neucore.fisheyes.zxing.journeyapps.barcodescanner.camera;


import com.neucore.fisheyes.zxing.journeyapps.barcodescanner.SourceData;

/**
 * Callback for camera previews.
 */
public interface PreviewCallback {
    void onPreview(SourceData sourceData);
}
