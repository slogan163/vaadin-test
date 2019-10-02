package com.example.application.views.form

import com.example.application.MainView
import com.example.application.backend.BackendService
import com.example.application.backend.Employee
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.page.Page
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.router.*
import java.util.*

@Route(value = "patient", layout = MainView::class)
@RouteAlias(value = "", layout = MainView::class)
@PageTitle("Form")
@CssImport("styles/views/form/form-view.css")
class FormView : Div(), HasUrlParameter<String> {

    private val firstname = TextField()
    private val lastname = TextField()
    private val email = TextField()
    private val notes = TextArea()
    private val binder: Binder<Employee>

    private val cancel = Button("Cancel")
    private val save = Button("Save")

    init {
        setId("form-view")
        val wrapper = createWrapper()

        createTitle(wrapper)
        createFormLayout(wrapper)
        createButtonLayout(wrapper)

        // Configure Form

        binder = Binder(Employee::class.java)

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this)

        cancel.addClickListener { binder.readBean(null) }
        save.addClickListener { BackendService.save(binder.bean) }

        add(wrapper)
    }

    override fun setParameter(event: BeforeEvent, @OptionalParameter parameter: String?) {
        if (parameter != null) {
            bindPatientId(parameter)
            storePatientId(UI.getCurrent().page, parameter)
        } else {
            loadPatientId(UI.getCurrent().page)
            Notification.show("No parameter")
        }
    }

    private fun storePatientId(page: Page, patientId: String) {
        page.executeJs(String.format("localStorage.setItem('patientId', '%s')", patientId))
    }

    private fun loadPatientId(page: Page) {
        page.executeJs("return localStorage.getItem('patientId')")
                .then(String::class.java) { this.bindPatientId(it) }
    }

    private fun bindPatientId(patientId: String) {
        val id = UUID.fromString(patientId)
        binder.bean = BackendService.load(id)
    }

    private fun createTitle(wrapper: VerticalLayout) {
        val h1 = H1("Form")
        wrapper.add(h1)
    }

    private fun createWrapper(): VerticalLayout {
        val wrapper = VerticalLayout()
        wrapper.setId("wrapper")
        wrapper.isSpacing = false
        return wrapper
    }

    private fun createFormLayout(wrapper: VerticalLayout) {
        val formLayout = FormLayout()
        addFormItem(wrapper, formLayout, firstname, "First name")
        addFormItem(wrapper, formLayout, lastname, "Last name")
        val emailFormItem = addFormItem(wrapper, formLayout,
                email, "Email")
        formLayout.setColspan(emailFormItem, 2)
        val notesFormItem = addFormItem(wrapper, formLayout,
                notes, "Notes")
        formLayout.setColspan(notesFormItem, 2)
    }

    private fun createButtonLayout(wrapper: VerticalLayout) {
        val buttonLayout = HorizontalLayout()
        buttonLayout.addClassName("button-layout")
        buttonLayout.setWidthFull()
        buttonLayout.justifyContentMode = FlexComponent.JustifyContentMode.END
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY)
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        buttonLayout.add(cancel)
        buttonLayout.add(save)
        wrapper.add(buttonLayout)
    }

    private fun addFormItem(wrapper: VerticalLayout,
                            formLayout: FormLayout, field: Component, fieldName: String): FormLayout.FormItem {
        val formItem = formLayout.addFormItem(field, fieldName)
        wrapper.add(formLayout)
        field.element.classList.add("full-width")
        return formItem
    }

}
