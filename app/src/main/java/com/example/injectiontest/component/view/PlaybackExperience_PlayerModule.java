package com.example.injectiontest.component.view;

import androidx.lifecycle.ViewModelStoreOwner;

import com.example.injectiontest.component.annotation.PlayerManaged;
import com.example.injectiontest.component.annotation.PlayerViewComponent;
import com.example.injectiontest.component.experience.PlaybackExperience;
import com.example.injectiontest.component.experience.PlaybackExperienceKey;

import java.util.Map;

import javax.inject.Provider;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;

@Module
@InstallIn(PlayerViewComponent.class)
abstract class PlaybackExperience_PlayerModule {
   @Provides
   static PlayerView bindsPlayerView(
           PlaybackExperience playbackExperience,
           Map<PlaybackExperienceKey, Provider<PlayerView>> playbackExperienceProviderMap
   ) {
      Provider<PlayerView> provider = playbackExperienceProviderMap.get(playbackExperience.getExperienceKey());

      if (provider == null) {
         String key = playbackExperience.getExperienceKey().getKeyString();
         throw new IllegalArgumentException("PlayerView not found for PlayerExperience=" + key);
      }
      return provider.get();
   }

   @Provides
   static PlaybackExperienceView playerExperienceView(@PlayerManaged ViewModelStoreOwner viewModelStoreOwner) {
      if (!(viewModelStoreOwner instanceof PlaybackExperienceView)) {
         throw new IllegalArgumentException("PlaybackExperience is not created using a PlaybackExperienceView");
      }

      return (PlaybackExperienceView) viewModelStoreOwner;
   }
}
