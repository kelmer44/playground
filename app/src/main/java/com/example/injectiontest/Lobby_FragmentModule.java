package com.example.injectiontest;

import androidx.fragment.app.Fragment;

import com.example.injectiontest.injectedobjects.ContentTypeRouter;
import com.example.injectiontest.injectedobjects.LobbyLeaveHelper;
import com.example.injectiontest.injectedobjects.LobbyViewModel;
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
//            LobbyLeaveHelper lobbyLeaveHelper
            Lazy<LobbyLeaveHelper> lobbyLeaveHelperProvider
    ) {
        return ViewModelUtils.getViewModel(fragment,
                LobbyViewModel.class,
                () -> new LobbyViewModel(lobbyLeaveHelperProvider)
//                () -> new LobbyViewModel(lobbyLeaveHelper)
        );
    }


    @Provides
    static ContentTypeRouter contentTypeRouter(Fragment fragment) {
        return ContentTypeRouter.requireRouter(fragment);
    }
}
