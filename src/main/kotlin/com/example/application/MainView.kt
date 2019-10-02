package com.example.application

import com.example.application.views.form.FormView
import com.example.application.views.masterdetail.MasterDetailView
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.TabVariant
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.router.RouterLink
import com.vaadin.flow.server.PWA
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import java.util.*

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@PWA(name = "Test", shortName = "Test", startPath = "patient/")
@Theme(value = Lumo::class, variant = Lumo.LIGHT)
class MainView : AppLayout() {

    private val menu: Tabs

    private val availableTabs: Array<Tab>
        get() {
            val tabs = ArrayList<Tab>()
            tabs.add(createTab("Form", FormView::class.java))
            tabs.add(createTab("MasterDetail", MasterDetailView::class.java))
            return tabs.toTypedArray()
        }

    init {
        menu = createMenuTabs()
        addToNavbar(menu)
    }

    private fun createMenuTabs(): Tabs {
        val tabs = Tabs()
        tabs.orientation = Tabs.Orientation.HORIZONTAL
        tabs.add(*availableTabs)
        return tabs
    }

    private fun createTab(title: String,
                          viewClass: Class<out Component>): Tab {
        return createTab(populateLink(RouterLink(null, viewClass), title))
    }

    private fun createTab(content: Component): Tab {
        val tab = Tab()
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP)
        tab.add(content)
        return tab
    }

    private fun <T : HasComponents> populateLink(a: T, title: String): T {
        a.add(title)
        return a
    }

}
