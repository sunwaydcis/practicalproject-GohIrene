package ch.makery.address.view
import ch.makery.address.model.Person
import ch.makery.address.MainApp
import javafx.fxml.FXML
import javafx.scene.control.{Label, TableColumn, TableView}
import scalafx.Includes.*
import javafx.scene.control.TextField
import ch.makery.address.util.DateUtil.*
import javafx.event.ActionEvent
import scalafx.beans.binding.Bindings
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

@FXML
class PersonOverviewController():
  @FXML
  private var personTable: TableView[Person] = null
  @FXML
  private var firstNameColumn: TableColumn[Person, String] = null
  @FXML
  private var lastNameColumn: TableColumn[Person, String] = null
  @FXML
  private var firstNameLabel: Label = null
  @FXML
  private var lastNameLabel: Label = null
  @FXML
  private var streetLabel: Label = null
  @FXML
  private var postalCodeLabel: Label = null
  @FXML
  private var cityLabel: Label = null
  @FXML
  private var birthdayLabel: Label = null
  @FXML
  private var mytext: TextField = null //use the grey textField, not the blue one
  // initialize Table View display contents model
  def initialize() =
    personTable.items = MainApp.personData
    // initialize columns's cell values
    firstNameColumn.cellValueFactory = {x=> x.value.firstName}
    lastNameColumn.cellValueFactory  = {_.value.lastName}

    firstNameLabel.text <== mytext.text  // how to sync the textfield and the firstnamelabel tgt
    showPersonDetails(None)
    // check which item is selected, and display what is selected
    personTable.selectionModel().selectedItem.onChange(
      (_, _, newValue) => showPersonDetails(Option(newValue))
    )

  // Person details in plug (helper details)
  private def showPersonDetails(person: Option[Person]): Unit =
    person match
      case Some(person) => //has value, inside bracket is any variable, use person variable inside the case, and do binding
        // Fill the labels with info from the person object.
        firstNameLabel.text <== person.firstName
        lastNameLabel.text <== person.lastName
        streetLabel.text <== person.street
        cityLabel.text <== person.city
        //it is number, so it has bind object property into string property (convert to string and update table)
        postalCodeLabel.text <== person.postalCode.delegate.asString()
        birthdayLabel.text <== Bindings.createStringBinding(() => {
          person.date.value.asString
        }, person.date)

      case None =>
        // no value, set all the text property to empty string (publisher is the left hand side); cannot change the subscriber value

        // have to do unbind if you want to change the subscriber value
        firstNameLabel.text.unbind()
        lastNameLabel.text.unbind()
        streetLabel.text.unbind()
        postalCodeLabel.text.unbind()
        cityLabel.text.unbind()
        birthdayLabel.text.unbind()

        // Person is null, remove all the text.
        firstNameLabel.text = ""
        lastNameLabel.text = ""
        streetLabel.text = ""
        postalCodeLabel.text = ""
        cityLabel.text = ""
        birthdayLabel.text = ""

  /**
   * Called when the user clicks on the delete button.
   */
  // handle delete person
  // find user click what button in the action event
  @FXML //anything with fxml, is using javafx library
  def handleDeletePerson(action: ActionEvent) =
    val selectedIndex = personTable.selectionModel().selectedIndex.value //selectedIdex = which row is selected
    if (selectedIndex >= 0) then
      personTable.items().remove(selectedIndex)
      // MainApp.personData.remove(selectedIndex); // delete from specific

    else //should pop-up error message if dont click anything and click delete
      // Nothing selected.
      // alert type: information; error
      val alert = new Alert(AlertType.Warning): //warning sign shows cuz alertType: Warning words
        initOwner(MainApp.stage) //init window = point back to main window when close warining window
        title = "No Selection"
        headerText = "No Person Selected"
        contentText = "Please select a person in the table."
      alert.showAndWait()




