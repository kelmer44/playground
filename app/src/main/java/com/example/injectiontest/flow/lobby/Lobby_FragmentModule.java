package com.example.injectiontest.flow.lobby;

import androidx.fragment.app.Fragment;

import com.example.injectiontest.util.ViewModelUtils;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn(FragmentComponent.class)
abstract class Lobby_FragmentModule {

    @Provides
    static LobbyViewModel provideLobbyViewModel(
            Fragment fragment,
            Lazy<LobbyLeaveHelper> lobbyLeaveHelperProvider
    ) {
        return ViewModelUtils.getViewModel(fragment,
                LobbyViewModel.class,
                () -> new LobbyViewModel(lobbyLeaveHelperProvider)
        );
    }


    @Provides
    static ContentTypeRouter contentTypeRouter(Fragment fragment) {
        return ContentTypeRouter.requireRouter(fragment);
    }
}
