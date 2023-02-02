package com.example.injectiontest.component.annotation

import javax.inject.Scope

/**
 * For now this is a scope alias, but the idea is to make this an actual Dagger scope with a custom Hilt component.
 */
@Scope
annotation class PlayerRetainedScoped
