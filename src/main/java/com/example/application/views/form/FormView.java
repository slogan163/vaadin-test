package com.example.application.views.form;

import com.example.application.MainView;
import com.example.application.backend.BackendService;
import com.example.application.backend.Employee;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;

import java.util.UUID;

@Route(value = "patient", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Form")
@CssImport("styles/views/form/form-view.css")
public class FormView extends Div implements HasUrlParameter<String> {

    private TextField firstname = new TextField();
    private TextField lastname = new TextField();
    private TextField email = new TextField();
    private TextArea notes = new TextArea();
    private Binder<Employee> binder;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    public FormView() {
        setId("form-view");
        VerticalLayout wrapper = createWrapper();

        createTitle(wrapper);
        createFormLayout(wrapper);
        createButtonLayout(wrapper);

        // Configure Form

        binder = new Binder<>(Employee.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> binder.readBean(null));
        save.addClickListener(e -> BackendService.INSTANCE.save(binder.getBean()));

        add(wrapper);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter != null) {
            bindPatientId(parameter);
            storePatientId(UI.getCurrent().getPage(), parameter);
        } else {
            loadPatientId(UI.getCurrent().getPage());
            Notification.show("No parameter");
        }
    }

    private void storePatientId(Page page, String patientId) {
        page.executeJs(String.format("localStorage.setItem('patientId', '%s')", patientId));
    }

    private void loadPatientId(Page page) {
        page.executeJs("return localStorage.getItem('patientId')")
                .then(String.class, this::bindPatientId);
    }

    private void bindPatientId(String patientId) {
        UUID id = UUID.fromString(patientId);
        binder.setBean(BackendService.INSTANCE.load(id));
    }

    private void createTitle(VerticalLayout wrapper) {
        H1 h1 = new H1("Form");
        wrapper.add(h1);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(false);
        return wrapper;
    }

    private void createFormLayout(VerticalLayout wrapper) {
        FormLayout formLayout = new FormLayout();
        addFormItem(wrapper, formLayout, firstname, "First name");
        addFormItem(wrapper, formLayout, lastname, "Last name");
        FormLayout.FormItem emailFormItem = addFormItem(wrapper, formLayout,
                email, "Email");
        formLayout.setColspan(emailFormItem, 2);
        FormLayout.FormItem notesFormItem = addFormItem(wrapper, formLayout,
                notes, "Notes");
        formLayout.setColspan(notesFormItem, 2);
    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(cancel);
        buttonLayout.add(save);
        wrapper.add(buttonLayout);
    }

    private FormLayout.FormItem addFormItem(VerticalLayout wrapper,
                                            FormLayout formLayout, Component field, String fieldName) {
        FormLayout.FormItem formItem = formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
        return formItem;
    }

}
