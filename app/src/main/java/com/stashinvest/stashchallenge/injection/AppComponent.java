package com.stashinvest.stashchallenge.injection;

import com.stashinvest.stashchallenge.App;
import com.stashinvest.stashchallenge.listing.main.MainActivity;
import com.stashinvest.stashchallenge.listing.main.MainFragment;
import com.stashinvest.stashchallenge.listing.popup.PopUpDialogActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(App app);

    void inject(MainActivity activity);

    void inject(MainFragment fragment);

    void inject(PopUpDialogActivity fragment);
}