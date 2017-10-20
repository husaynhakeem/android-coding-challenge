package com.stashinvest.stashchallenge.injection;

import com.stashinvest.stashchallenge.App;
import com.stashinvest.stashchallenge.listing.MainActivity;
import com.stashinvest.stashchallenge.listing.MainFragment;
import com.stashinvest.stashchallenge.listing.popup.PopUpDialogFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, ListingModule.class})
public interface AppComponent {
    void inject(App app);

    void inject(MainActivity activity);

    void inject(MainFragment fragment);

    void inject(PopUpDialogFragment fragment);
}