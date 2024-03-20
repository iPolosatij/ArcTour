package space.dlsunity.simple_crm.presenter.screens.main_container.destinations

import space.dlsunity.simple_crm.presenter.base.navigation.Destination

interface MainDestination: Destination {
    class ToWelcome(val logout: Boolean): MainDestination
    object ToCreateWorkCard: MainDestination
}