package pl.marczak.sthhappens.widgets;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Project "SthHappens"
 * <p>
 * Created by Lukasz Marczak
 * on 23.10.16.
 */
public class FabBehaviour extends CoordinatorLayout.Behavior<FloatingActionButton> {
    public static final String TAG = FabBehaviour.class.getSimpleName();

    public FabBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        Log.d(TAG, "layoutDependsOn: ");
        return dependency instanceof RelativeLayout || dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        Log.d(TAG, "onDependentViewChanged: ");
        float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);
        return true;
    }
}