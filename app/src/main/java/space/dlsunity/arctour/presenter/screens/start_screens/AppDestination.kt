package space.dlsunity.arctour.presenter.screens.start_screens

import space.dlsunity.arctour.presenter.base.navigation.Destination

interface AppDestination: Destination {
    object ToWelcome : AppDestination
}