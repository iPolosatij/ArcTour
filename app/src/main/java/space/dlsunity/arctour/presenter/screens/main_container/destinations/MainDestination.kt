package space.dlsunity.arctour.presenter.screens.main_container.destinations

import space.dlsunity.arctour.presenter.base.navigation.Destination

interface MainDestination: Destination {
    class ToWelcome(val logout: Boolean): MainDestination
}