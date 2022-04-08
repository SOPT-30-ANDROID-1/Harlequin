package happy.mjstudio.harlequin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import happy.mjstudio.harlequin.auth.validator.AuthFormValidator
import happy.mjstudio.harlequin.auth.validator.AuthFormValidatorImpl
import happy.mjstudio.harlequin.auth.validator.IdValidator
import happy.mjstudio.harlequin.auth.validator.NameValidator
import happy.mjstudio.harlequin.auth.validator.PwValidator
import happy.mjstudio.harlequin.util.StringValidator
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthModule {
    @Binds
    @ViewModelScoped
    @Named("id")
    abstract fun provideIdValidator(v: IdValidator): StringValidator

    @Binds
    @ViewModelScoped
    @Named("pw")
    abstract fun providePwValidator(v: PwValidator): StringValidator

    @Binds
    @ViewModelScoped
    @Named("name")
    abstract fun provideNameValidator(v: NameValidator): StringValidator

    @Binds
    @ViewModelScoped
    abstract fun authFormValidator(v: AuthFormValidatorImpl): AuthFormValidator
}