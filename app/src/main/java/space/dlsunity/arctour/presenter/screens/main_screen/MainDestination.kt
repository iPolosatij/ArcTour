package space.dlsunity.arctour.presenter.screens.main_screen

import space.dlsunity.arctour.presenter.base.navigation.Destination

interface MainDestination: Destination {
    class toWelcome(val logout: Boolean): MainDestination
}