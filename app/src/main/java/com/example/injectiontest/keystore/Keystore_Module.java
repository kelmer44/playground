package com.example.injectiontest.keystore;

import androidx.fragment.app.Fragment;

import com.example.injectiontest.archselector.ArchRepository;
import com.example.injectiontest.archselector.arch.ArchViewModel;
import com.example.injectiontest.keystore.encrypt.Decryptor;
import com.example.injectiontest.keystore.encrypt.Encryptor;
import com.example.injectiontest.util.ViewModelUtils;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn(FragmentComponent.class)
public abstract class Keystore_Module {

    @Provides
    static KeystoreViewModel providesViewModel(Fragment fragment, Encryptor encryptor, Decryptor decryptor) {
        return ViewModelUtils.getViewModel(
                fragment,
                KeystoreViewModel.class,
                () -> new KeystoreViewModel(encryptor, decryptor)
        );
    }

    @Named("alias")
    @Provides
    static String provideAlias() {
        return "MyAlias";
    }

}
