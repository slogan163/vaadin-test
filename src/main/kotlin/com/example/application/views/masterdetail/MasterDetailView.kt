package com.example.application.views.masterdetail

import com.example.application.MainView
import com.example.application.backend.BackendService
import com.example.application.backend.Employee
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.data.renderer.ComponentRenderer
import com.vaadin.flow.router.AfterNavigationEvent
import com.vaadin.flow.router.AfterNavigationObserver
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(value = "masterdetail", layout = MainView::class)
@PageTitle("MasterDetail")
@CssImport("styles/views/masterdetail/master-detail-view.css")
class MasterDetailView : Div(), AfterNavigationObserver {

    private val service: BackendService
    private val grid: Grid<Employee>

    init {
        service = BackendService.INSTANCE
        setId("master-detail-view")
        grid = Grid()
        grid.setId("list")
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS)
        grid.setHeightFull()
        grid.addColumn(ComponentRenderer<Div, Employee> { employee ->
            val h3 = H3(
                    employee.lastname + ", " + employee.firstname)
            val anchor = Anchor("mailto:" + employee.email!!,
                    employee.email)
            anchor.element.themeList.add("font-size-xs")
            val div = Div(h3, anchor)
            div.addClassName("employee-column")
            div
        })

        add(grid)
    }

    override fun afterNavigation(event: AfterNavigationEvent) {
        // Lazy init of the grid items, happens only when we are sure the view will be
        // shown to the user
        grid.setItems(service.getEmployees()!!)
    }
}
