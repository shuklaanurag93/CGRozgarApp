package com.cgemployment.cge_rozgarapp;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class GraphicFaceTracker extends Tracker<Face> {

    private static final float OPEN_THRESHOLD = 0.85f;
    private static final float CLOSE_THRESHOLD = 0.4f;
    private final registration_form Registration_form;
    private int state = 0;
    int blinkcount = 0;

    GraphicFaceTracker(registration_form Registration_form) {
        this.Registration_form = Registration_form;
    }

    private void blink(float value) {
        switch (state) {
            case 0:
                if (value > OPEN_THRESHOLD) {
                    // Both eyes are initially open
                    state = 1;
                }
                break;
            case 1:
                if (value < CLOSE_THRESHOLD) {
                    // Both eyes become closed
                    state = 2;
                }
                break;
            case 2:
                if (value > OPEN_THRESHOLD) {
                    // Both eyes are open again
                    Log.i("Camera Demo", "blink has occurred!");
                    state = 0;
                    blinkcount++;

                }

                if (blinkcount == 4) {
                    Registration_form.captureImage();
                    blinkcount = 0;

                } else {
                    Registration_form.blinkCountfn(blinkcount);


                }
                break;
            default:
                break;
        }
    }

    /**
     * Update the position/characteristics of the face within the overlay.
     */
    @Override
    public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
        float left = face.getIsLeftEyeOpenProbability();
        float right = face.getIsRightEyeOpenProbability();
        if ((left == Face.UNCOMPUTED_PROBABILITY) ||
                (right == Face.UNCOMPUTED_PROBABILITY)) {
            // One of the eyes was not detected.
            return;
        }

        float value = Math.min(left, right);
        blink(value);
    }
}