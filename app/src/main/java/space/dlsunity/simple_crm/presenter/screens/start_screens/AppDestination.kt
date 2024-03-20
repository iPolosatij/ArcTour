package space.dlsunity.simple_crm.presenter.screens.start_screens

import space.dlsunity.simple_crm.presenter.base.navigation.Destination

interface AppDestination: Destination {
    object ToWelcome : AppDestination
    object ToMain : AppDestination
}