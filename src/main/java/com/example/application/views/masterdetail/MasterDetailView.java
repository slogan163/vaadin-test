package com.example.application.views.masterdetail;

import com.example.application.MainView;
import com.example.application.backend.BackendService;
import com.example.application.backend.Employee;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "masterdetail", layout = MainView.class)
@PageTitle("MasterDetail")
@CssImport("styles/views/masterdetail/master-detail-view.css")
public class MasterDetailView extends Div implements AfterNavigationObserver {

    private BackendService service;
    private final Grid<Employee> grid;

    public MasterDetailView() {
        service = new BackendService();
        setId("master-detail-view");
        grid = new Grid<>();
        grid.setId("list");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);
        grid.setHeightFull();
        grid.addColumn(new ComponentRenderer<>(employee -> {
            H3 h3 = new H3(
                    employee.getLastname() + ", " + employee.getFirstname());
            Anchor anchor = new Anchor("mailto:" + employee.getEmail(),
                    employee.getEmail());
            anchor.getElement().getThemeList().add("font-size-xs");
            Div div = new Div(h3, anchor);
            div.addClassName("employee-column");
            return div;
        }));

        add(grid);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        // Lazy init of the grid items, happens only when we are sure the view will be
        // shown to the user
        grid.setItems(service.getEmployees());
    }
}
