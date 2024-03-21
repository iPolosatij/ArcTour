package space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.screens.destinations

import space.dlsunity.simple_crm.presenter.base.navigation.Destination

sealed interface CreateWorkDestination: Destination{

    class ToMain( val tab: Int): CreateWorkDestination
}