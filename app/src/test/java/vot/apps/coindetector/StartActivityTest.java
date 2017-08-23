package vot.apps.coindetector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import vot.apps.coindetector.ui.activities.StartActivity;
import vot.apps.coindetector.ui.presenters.StartPresenter;
import vot.apps.coindetector.ui.screen_contracts.StartScreen;


@RunWith(MockitoJUnitRunner.class)
public class StartActivityTest {

    @Mock
    StartActivity mStartActivity;

    @Mock
    StartScreen mStartScreen;

    @Test
    public void shouldInitiateDashboard(){
        StartPresenter mStartPresenter = new StartPresenter();
        mStartPresenter.interact(mStartScreen);

        Mockito.verify(mStartActivity).loadDashboard();

    }


}